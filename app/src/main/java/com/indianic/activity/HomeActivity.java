package com.indianic.activity;


import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.indianic.fragment.HomeFragment;
import com.indianic.R;

public class HomeActivity extends BaseActivity implements DrawerLayout.DrawerListener {

    /**
     * Left navigation drawer which can be managed by menu icon
     */
    private DrawerLayout drawerLayout;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initializeComponents() {
        replaceFragment(R.id.activity_home_fl_container, getFragmentManager(), new HomeFragment(), false, false);

        drawerLayout = findViewById(R.id.activity_home_drawer_layout);
        drawerLayout.addDrawerListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);// Un Lock the drawer to allow User to swipe to close
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);// Lock the drawer to disallow User to swipe to open
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    /**
     * Opens the Navigation Drawer
     */
    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.START);
    }

    /**
     * Close the Navigation Drawer
     */
    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
