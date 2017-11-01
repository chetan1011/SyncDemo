package com.indianic.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.indianic.R;


/**
 * Purpose: This class set the font to textView according to attribute
 *
 * @author
 * @version 1.0
 */
public class CustomTextView extends AppCompatTextView {
    public CustomTextView(final Context mContext) {
        super(mContext);
    }

    public CustomTextView(final Context mContext, final AttributeSet attrs) {
        super(mContext, attrs);
        setCustomFont(mContext, attrs);
    }

    public CustomTextView(final Context mContext, final AttributeSet attrs, final int defStyle) {
        super(mContext, attrs, defStyle);
        setCustomFont(mContext, attrs);
    }

    public void setCustomFont(final Context mContext, final AttributeSet attrs) {
        @SuppressLint("CustomViewStyleable") final TypedArray mTypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.attCustomTextView);
        int font = mTypedArray.getInteger(R.styleable.attCustomTextView_fontName, R.integer.fontGothamBook);

        switch (font) {
            case R.integer.fontGothamBold:
                setCustomFont(mContext, mContext.getString(R.string.fontGothamBold));
                break;
        }
        mTypedArray.recycle();
    }

    /**
     * Sets a font on a textView
     *
     * @param mContext
     * @param font
     */

    public void setCustomFont(final Context mContext, final String font) {
        if (font == null) {
            return;
        }
        final Typeface mTypeface = FontCache.get(font, mContext);
        if (mTypeface != null) {
            setTypeface(mTypeface);
        }
    }
}