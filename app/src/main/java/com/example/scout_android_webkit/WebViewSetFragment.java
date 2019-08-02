package com.example.scout_android_webkit;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Stack;

public class WebViewSetFragment extends Fragment {
    private final String startUrl;

    protected FrameLayout frameLayout;
    protected Stack<WebView> webViewStack;
    protected WebView extraWebView;

    WebViewSetFragment(String startUrl) {
        this.startUrl = startUrl;
        webViewStack = new Stack<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (webViewStack.size() == 0)
            frameLayout = new FrameLayout(getActivity());
        return frameLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (webViewStack.size() == 0) {
            webViewStack.add(new WebView(getActivity()));
            webViewStack.peek().getSettings().setJavaScriptEnabled(true);
            webViewStack.peek().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            frameLayout.addView(webViewStack.peek());

            extraWebView = new WebView(getActivity());
        }

        Log.d("WebViewSetFragment", "onViewCreated: Called");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        if (webViewStack.size() != 0)
            webViewStack.peek().onResume();
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        if (webViewStack.peek().getUrl() == null)
            webViewStack.peek().loadUrl(startUrl);
        super.onStart();
    }

    @Override
    public void onDetach() {
        if (webViewStack.size() != 0)
            webViewStack.peek().onPause();
        super.onDetach();
    }

    public Boolean goBack() {
        if (webViewStack.peek().canGoBack()) {
            webViewStack.peek().goBack();
            return true;
        }
        return false;
    }
}
