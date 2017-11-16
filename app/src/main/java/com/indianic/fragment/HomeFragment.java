package com.indianic.fragment;


import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.indianic.R;
import com.indianic.adapter.HomeBannerPagerAdapter;
import com.indianic.model.BannerModel;
import com.indianic.model.HomeDataModel;
import com.indianic.util.Utils;

import java.util.ArrayList;

/**
 * Sample Fragment for reference purpose only.
 */
public class HomeFragment extends BaseFragment implements HomeBannerPagerAdapter.OnItemClick {

    public static final int DELAY = 5000;// Milliseconds delay to swipe banner view pager automatically.
    private ViewPager vpBanner;//Banner images view pager

    private HomeDataModel homeDataModel;//Model containing all the Home screen Data.
    private TabLayout tblBannerIndicator;//Banner images pager page indicator

    private HomeBannerPagerAdapter bannerAdapter;//Banner images pageradapter

    private Handler bannerHandler;// Handler, managing the banner pager auto swipe.
    private int currentBanner;//Represents the current visible banner position.


    @Override
    protected int defineLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initializeComponent(View view) {

        vpBanner = view.findViewById(R.id.fragment_home_vpBanner);
        tblBannerIndicator = view.findViewById(R.id.fragment_home_tblPageIndicator);
        tblBannerIndicator.setupWithViewPager(vpBanner);

        setDummyData();
        setBanner();
    }

    @Override
    public void reloadData() {

    }

    /**
     * Sets the Banner pager data.
     */
    private void setBanner() {
        if (homeDataModel.getBannerList() != null && homeDataModel.getBannerList().size() > 0) {
            bannerAdapter = new HomeBannerPagerAdapter(getActivity(), homeDataModel.getBannerList(), this);
            vpBanner.setAdapter(bannerAdapter);
            tblBannerIndicator.setVisibility(View.VISIBLE);

            setBannerHeight();

            vpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentBanner = position;
                    stopAutoSwipeBanner();
                    startAutoSwipeBanner();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            /*
             * Auto Banner pager swipe will only be available for more than one banner images.
             */
            if (homeDataModel.getBannerList().size() > 1) {
                bannerHandler = new Handler();
                startAutoSwipeBanner();
            }
        }
    }

    private void setBannerHeight() {
        final ViewGroup.LayoutParams params = vpBanner.getLayoutParams();

        final int deviceWidth = Utils.getDeviceMetrics(getActivity()).widthPixels;
        final float BANNER_ASPECT_RATIO = 16f / 9; //16:9 aspect ratio
        params.height = (int) (deviceWidth / BANNER_ASPECT_RATIO); //left, top, right, bottom

        vpBanner.setLayoutParams(params);
    }

    /**
     * Performs the banner pager auto swipe.
     */
    final Runnable bannerScroll = new Runnable() {
        public void run() {
            if (bannerAdapter.getCount() - 1 == currentBanner) {
                currentBanner = 0;
            } else {
                currentBanner++;
            }
            vpBanner.setCurrentItem(currentBanner, true);
        }
    };

    @Override
    public void onClickBanner(int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopAutoSwipeBanner();
    }

    /**
     * Stops the banner auto swipe
     */
    private void stopAutoSwipeBanner() {
        if (bannerHandler != null) {
            bannerHandler.removeCallbacks(bannerScroll);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            stopAutoSwipeBanner();
        } else {
            startAutoSwipeBanner();
        }
    }

    /**
     * Starts the banner auto swipe
     */
    private void startAutoSwipeBanner() {
        if (bannerHandler != null) {
            bannerHandler.postDelayed(bannerScroll, DELAY);
        }
    }

    private void setDummyData() {
        homeDataModel = new HomeDataModel();
        final ArrayList<BannerModel> bannerArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bannerArrayList.add(new BannerModel());
        }
        homeDataModel.setBannerList(bannerArrayList);
    }
}
