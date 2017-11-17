package com.indianic.adapter;


import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
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
    private ArrayList<BannerModel> bannerList;
    private OnItemClickListener onItemClickListener;

    private long lastClickedTime;

    public HomeBannerPagerAdapter(final Context context, final ArrayList<BannerModel> bannerList, final OnItemClickListener onItemClickListener) {
        this.context = context;
        this.bannerList = bannerList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        final View itemView = LayoutInflater.from(context).inflate(R.layout.row_home_banner, container, false);
        final ImageView imageView = itemView.findViewById(R.id.row_home_banner_ivOffer);

        ImageLoader.loadImage(context, imageView, R.drawable.dummy_banner_image, R.drawable.placeholder_banner);

        container.addView(itemView);

        imageView.setTag(position);
        imageView.setOnClickListener(this);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
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
            case R.id.row_home_banner_ivOffer:
                if (onItemClickListener != null) {
                    onItemClickListener.onClickBanner((Integer) v.getTag());
                }
                break;
        }

    }

    public interface OnItemClickListener {
        void onClickBanner(int position);
    }
}
