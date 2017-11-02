package com.indianic.customview;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

public class OnItemClickListenerWrapper implements AdapterView.OnItemClickListener {
    private static final long DEFAULT_MIN_INTERVAL = 500;
    private final AdapterView.OnItemClickListener mListener;
    private long mLastClickTime = 0;

    public OnItemClickListenerWrapper(AdapterView.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long currentTime = AnimationUtils.currentAnimationTimeMillis();
        if (currentTime - mLastClickTime > DEFAULT_MIN_INTERVAL) {
            mListener.onItemClick(parent, view, position, id);
            mLastClickTime = currentTime;
        }
    }
}
