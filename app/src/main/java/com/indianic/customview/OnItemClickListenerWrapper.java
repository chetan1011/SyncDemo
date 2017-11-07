package com.indianic.customview;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.indianic.util.Constants;

public class OnItemClickListenerWrapper implements AdapterView.OnItemClickListener {
    private final AdapterView.OnItemClickListener itemClickListener;
    private long lastClickTime = 0;

    public OnItemClickListenerWrapper(AdapterView.OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long currentTime = AnimationUtils.currentAnimationTimeMillis();
        if (currentTime - lastClickTime > Constants.MAX_CLICK_INTERVAL) {
            itemClickListener.onItemClick(parent, view, position, id);
            lastClickTime = currentTime;
        }
    }
}
