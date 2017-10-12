package com.indianic.adapter;


import android.content.Context;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.indianic.R;
import com.indianic.model.BannerModel;
import com.indianic.util.Constants;
import com.indianic.util.Utils;
import com.indianic.util.imageloader.ImageLoader;

import java.util.ArrayList;

/**
 * This Adapter holds the views for list for Home screen banner images.
 */
public class HomeBannerPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<BannerModel> bannerList;
    private OnItemClick onItemClick;

    private long lastClickedTime;

    public HomeBannerPagerAdapter(final Context context, final ArrayList<BannerModel> bannerList, final OnItemClick onItemClick) {
        this.context = context;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bannerList = bannerList;
        this.onItemClick = onItemClick;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final View itemView = layoutInflater.inflate(R.layout.row_home_banner, container, false);
        final ImageView imageView = itemView.findViewById(R.id.row_home_banner_iv);

        ImageLoader.loadImage(context, imageView, R.drawable.dummy_banner_image, R.drawable.placeholder_l_banner);

        container.addView(itemView);

        imageView.setTag(position);
        imageView.setOnClickListener(this);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public void onClick(View v) {
        Utils.hideSoftKeyBoard(context, v);
        /*
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - lastClickedTime < Constants.MAX_CLICK_INTERVAL) {

            return;
        }
        lastClickedTime = SystemClock.elapsedRealtime();


        final int id = v.getId();

        switch (id) {
            case R.id.row_home_banner_iv:
                if (onItemClick != null) {
                    onItemClick.onClickBanner((Integer) v.getTag());
                }
                break;
        }

    }

    public interface OnItemClick {
        void onClickBanner(int position);
    }
}