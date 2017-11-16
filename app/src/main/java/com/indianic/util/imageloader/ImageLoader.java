package com.indianic.util.imageloader;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.indianic.R;

import java.io.File;

/**
 * Image loader class which will use Glide to load images.
 */
public class ImageLoader {


    public static void loadImage(final Context context, final ImageView imageView, final String imageUrl, int placeHolderRes) {
        if (placeHolderRes != R.drawable.placeholder_banner) {
            placeHolderRes = R.drawable.placeholder_banner;
        }

        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(placeHolderRes)
                .dontAnimate()
                .dontTransform()
                .into(imageView);
    }

    public static void loadImage(final Context context, final ImageView imageView, final int resource, int placeHolderRes) {
        Glide.with(context)
                .load(resource)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(placeHolderRes)
                .dontAnimate()
                .dontTransform()
                .into(imageView);
    }

    public static void loadRoundedImage(final Context context, final ImageView imageView, final String imageUrl, int placeHolderRes) {

        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .placeholder(placeHolderRes).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                final RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadImage(final Context context, final ImageView imageView, final File file, int placeHolderRes) {
        Glide.with(context)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(placeHolderRes)
                .dontAnimate()
                .dontTransform()
                .into(imageView);
    }
}
