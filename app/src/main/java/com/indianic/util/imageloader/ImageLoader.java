package com.indianic.util.imageloader;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.indianic.R;
import com.indianic.customview.CircleImageView;

/**
 * Image loader class which will use Glide to load images.
 */
public class ImageLoader {


    /**
     * Loads the image from url into ImageView.
     */
    public static void loadImage(final Context context, final ImageView imageView, final String imageUrl, int placeHolderRes) {
        if (placeHolderRes != R.drawable.placeholder_banner) {
            placeHolderRes = R.drawable.placeholder_banner;
        }

        Glide.with(context).asBitmap().load(imageUrl).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes)).into(imageView);
    }

    /**
     * Loads the image resource into ImageView.
     */
    public static void loadImage(final Context context, final ImageView imageView, final int resource, int placeHolderRes) {
        Glide.with(context).asBitmap().load(resource).apply(new RequestOptions().placeholder(placeHolderRes).error(placeHolderRes)).into(imageView);
    }

    /**
     * Set rounded image view
     * <p>
     * If want to do something after setting the image so don't use this generalized method. Use it at same place at your own.
     * </p>
     *
     * @param uri       String image Uri
     * @param imageView imageView into set image
     */

    public void getGlideRounded(final Context context, final String uri, final CircleImageView imageView) {
        Glide.with(context).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                //                mImageView.setImageDrawable(VectorDrawableCompat.create(getResources(), R.drawable.img_placeholder, null));
                super.onLoadFailed(errorDrawable);
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder_banner));
            }

            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder_banner));
            }
        });
    }
}
