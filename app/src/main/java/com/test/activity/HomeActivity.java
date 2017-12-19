package com.test.activity;


import com.test.R;
import com.test.fragment.UserListFragment;

/**
 * Sample Home activity with Navigation menu.
 */
public class HomeActivity extends BaseActivity {


    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initializeComponents() {
        replaceFragment(R.id.activity_home_flContainer, getFragmentManager(), new UserListFragment(), false, false);


    }

}
