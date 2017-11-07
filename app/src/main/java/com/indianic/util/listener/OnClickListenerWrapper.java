package com.indianic.util.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;

import com.indianic.util.Constants;

public class OnClickListenerWrapper implements OnClickListener {
    private final OnClickListener onClickListener;
    private long lastClickTime = 0;

    public OnClickListenerWrapper(OnClickListener listener) {
        onClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        long currentTime = AnimationUtils.currentAnimationTimeMillis();
        if (currentTime - lastClickTime > Constants.MAX_CLICK_INTERVAL) {
            onClickListener.onClick(v);
            lastClickTime = currentTime;
        }
    }
}
