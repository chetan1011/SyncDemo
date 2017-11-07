package com.indianic.customview;

import android.view.animation.AnimationUtils;

import com.indianic.util.Constants;

public class OnRecyclerItemClickListenerWrapper implements RecyclerViewItemClickListener {
    private final RecyclerViewItemClickListener itemClickListener;
    private long lastClickTime = 0;

    public OnRecyclerItemClickListenerWrapper(RecyclerViewItemClickListener listener) {
        itemClickListener = listener;
    }

    public void onItemClick(int position) {
        long currentTime = AnimationUtils.currentAnimationTimeMillis();
        if (currentTime - lastClickTime > Constants.MAX_CLICK_INTERVAL) {
            itemClickListener.onItemClick(position);
            lastClickTime = currentTime;
        }
    }
}
