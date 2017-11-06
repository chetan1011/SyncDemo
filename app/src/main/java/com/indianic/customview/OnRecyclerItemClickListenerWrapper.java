package com.indianic.customview;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;

public class OnRecyclerItemClickListenerWrapper implements RecyclerViewItemClickListener{
    private static final long DEFAULT_MIN_INTERVAL = 700;
    private final RecyclerViewItemClickListener mListener;
    private long mLastClickTime = 0;

    public OnRecyclerItemClickListenerWrapper(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    public void onItemClick(int position) {
        long currentTime = AnimationUtils.currentAnimationTimeMillis();
        if (currentTime - mLastClickTime > DEFAULT_MIN_INTERVAL) {
            mListener.onItemClick(position);
            mLastClickTime = currentTime;
        }
    }
}
