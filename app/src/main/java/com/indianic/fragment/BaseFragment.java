package com.indianic.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.indianic.util.impl.DataReloader;
import com.indianic.R;
import com.indianic.activity.BaseActivity;
import com.indianic.activity.HomeActivity;
import com.indianic.util.Constants;
import com.indianic.util.Utils;


/**
 * Base class for all the fragments used, manages common feature needed in the most of the fragments
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener, DataReloader {

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * Initialize the components for Fragment's view
     *
     * @param view A View inflated into Fragment
     */
    protected abstract void initializeComponent(View view);//to initialize the fragments components

    /**
     * Returns the resource id of the layout which will be used for setContentView() for the Activity
     *
     * @return resource id of the xml layout
     */
    protected abstract int defineLayoutResource();

    /**
     * Contains last clicked time
     */
    private long lastClickedTime = 0;

    /**
     * Shows progress indication in screens
     */
    protected ProgressBar pbProgress;

    /**
     * EmptyView Layout
     */
    private LinearLayout llEmptyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(defineLayoutResource(), container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pbProgress = view.findViewById(R.id.layout_pb_progress);

        llEmptyView = view.findViewById(R.id.layout_empty_view_ll_main);

        if (llEmptyView != null) {
            llEmptyView.setVisibility(View.GONE);
            llEmptyView.setOnClickListener(this);
        }

        /*
      Drawer menu image button
     */
        final ImageButton ibMenu = view.findViewById(R.id.header_ib_menu);
        /*final ImageView ibBack = (ImageView) view.findViewById(R.id.header_iv_back);
        final ImageView ivSearch = (ImageView) view.findViewById(R.id.header_iv_search);*/


        if (ibMenu != null) {
            ibMenu.setOnClickListener(this);
        }
        /*if (ibBack != null) {
            ibBack.setOnClickListener(this);
        }
        if (ivSearch != null) {
            ivSearch.setOnClickListener(this);
        }*/
        initializeComponent(view);
    }

    /**
     * Sets visibility of empty view
     */
    protected void setEmptyView(final boolean isVisible) {
        if (llEmptyView != null) {
            if (isVisible) {
                llEmptyView.setVisibility(View.VISIBLE);
            } else {
                llEmptyView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Set empty data message
     */
    protected void setEmptyViewMessage(final String message) {
        if (llEmptyView != null && !TextUtils.isEmpty(message)) {
            final TextView tvEmpty = llEmptyView.findViewById(R.id.layout_empty_view_tv_message);
            if (tvEmpty != null) {
                tvEmpty.setText(message);
            }
        }
    }

    /**
     * Adds the Fragment into layout container.
     *
     * @param container               Resource id of the layout in which Fragment will be added
     * @param currentFragment         Current loaded Fragment to be hide
     * @param nextFragment            New Fragment to be loaded into container
     * @param requiredAnimation       true if screen transition animation is required
     * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws ClassCastException    Throws exception if getActivity() is not an instance of BaseActivity
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    protected boolean addFragment(final int container, final Fragment currentFragment, final Fragment nextFragment, final boolean requiredAnimation, final boolean commitAllowingStateLoss) throws ClassCastException, IllegalStateException {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                return ((BaseActivity) getActivity()).addFragment(container, currentFragment, nextFragment, requiredAnimation, commitAllowingStateLoss);
            } else {
                throw new ClassCastException(BaseActivity.class.getName() + " can not be cast into " + getActivity().getClass().getName());
            }
        }
        return false;
    }

    /**
     * Replaces the Fragment into layout container.
     *
     * @param container               Resource id of the layout in which Fragment will be added
     * @param fragmentManager         Activity fragment manager
     * @param nextFragment            New Fragment to be loaded into container
     * @param requiredAnimation       true if screen transition animation is required
     * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws ClassCastException    Throws exception if getActivity() is not an instance of BaseActivity
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    protected boolean replaceFragment(final int container, final FragmentManager fragmentManager, final Fragment nextFragment, final boolean requiredAnimation, final boolean commitAllowingStateLoss) throws ClassCastException, IllegalStateException {
        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                return ((BaseActivity) getActivity()).replaceFragment(container, fragmentManager, nextFragment, requiredAnimation, commitAllowingStateLoss);
            } else {
                throw new ClassCastException(BaseActivity.class.getName() + " can not be cast into " + getActivity().getClass().getName());
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {

        Utils.getInstance().hideSoftKeyBoard(getActivity(), v);
        /*
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - lastClickedTime < Constants.MAX_CLICK_INTERVAL) {

            return;
        }
        lastClickedTime = SystemClock.elapsedRealtime();

        switch (v.getId()) {
            case R.id.header_ib_menu:

                openDrawer();

                break;

            /*case R.id.header_iv_back:
                getFragmentManager().popBackStack();
                break;*/


            case R.id.layout_empty_view_ll_main:
                reloadData();
                break;
        }
    }

    //Opens the navigation drawer menu
    private void openDrawer() {
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).openDrawer();
        }
    }

    // Close the navigation drawer menu
    protected void closeDrawer() {
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).closeDrawer();
        }
    }
}