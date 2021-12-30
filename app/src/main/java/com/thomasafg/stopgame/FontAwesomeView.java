package com.thomasafg.stopgame;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class FontAwesomeView extends androidx.appcompat.widget.AppCompatTextView {

    public FontAwesomeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontAwesomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesomeView(Context context) {
        super(context);
        init();
    }
    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fontawesome.ttf");
        setTypeface(tf);
    }
}
