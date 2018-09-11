package com.nenton.testmagnitapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.nenton.testmagnitapp.R;
import com.nenton.testmagnitapp.ui.fragments.AboutFragment;
import com.nenton.testmagnitapp.ui.fragments.TimeTableFragment;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

public class RootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private CoordinatorLayout layout;
    private Fragment timeTableFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        Toolbar toolbar = findViewById(R.id.toolbar);
        layout = findViewById(R.id.coordinator);
        setSupportActionBar(toolbar);
        manager = getSupportFragmentManager();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        timeTableFragment = manager.findFragmentByTag("root");

        if (timeTableFragment == null) {
            timeTableFragment = new TimeTableFragment();
            manager.beginTransaction()
                    .replace(R.id.root_container, timeTableFragment, "root")
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        TransitionManager.beginDelayedTransition(layout, new Slide(Gravity.RIGHT));
        switch (id) {
            case R.id.nav_timetable:
                if (timeTableFragment == null){
                    timeTableFragment = new TimeTableFragment();
                }
                manager.beginTransaction().replace(R.id.root_container, timeTableFragment).commit();
                break;
            case R.id.nav_about:
                manager.beginTransaction().replace(R.id.root_container, new AboutFragment()).commit();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
