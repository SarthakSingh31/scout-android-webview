package com.example.scout_android_webkit;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private WebViewSetFragment[] webViewSetFragments;
    private String base_url = "https://scout-test.s.uw.edu/h/seattle/";
    private int currentFragmentIndex = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_discovery:
                    switchToFragment(0);
                    return true;
                case R.id.navigation_food:
                    switchToFragment(1);
                    return true;
                case R.id.navigation_study:
                    switchToFragment(2);
                    return true;
                case R.id.navigation_tech:
                    switchToFragment(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        webViewSetFragments = new WebViewSetFragment[4];
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        webViewSetFragments[0] = new WebViewSetFragment(base_url);
        webViewSetFragments[1] = new WebViewSetFragment(base_url + "food/");
        webViewSetFragments[2] = new WebViewSetFragment(base_url + "study/");
        webViewSetFragments[3] = new WebViewSetFragment(base_url + "tech/");

        fragmentManager.beginTransaction().add(R.id.mainFrameLayout, webViewSetFragments[0]).commit();
        fragmentManager.beginTransaction().add(R.id.mainFrameLayout, webViewSetFragments[1]).commit();
        fragmentManager.beginTransaction().add(R.id.mainFrameLayout, webViewSetFragments[2]).commit();
        fragmentManager.beginTransaction().add(R.id.mainFrameLayout, webViewSetFragments[3]).commit();

        fragmentManager.beginTransaction().detach(webViewSetFragments[1]).commit();
        fragmentManager.beginTransaction().detach(webViewSetFragments[2]).commit();
        fragmentManager.beginTransaction().detach(webViewSetFragments[3]).commit();
        super.onStart();
    }

    private void switchToFragment(int fragmentIndex) {
        if (fragmentIndex != currentFragmentIndex) {
            fragmentManager.beginTransaction().detach(webViewSetFragments[currentFragmentIndex]).commit();
            currentFragmentIndex = fragmentIndex;
            fragmentManager.beginTransaction().attach(webViewSetFragments[currentFragmentIndex]).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(!webViewSetFragments[currentFragmentIndex].goBack())
            super.onBackPressed();
    }
}
