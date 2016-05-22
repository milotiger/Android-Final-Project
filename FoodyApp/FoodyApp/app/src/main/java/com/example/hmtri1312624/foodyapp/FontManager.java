package com.example.hmtri1312624.foodyapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by M-Tae on 5/21/2016.
 */
public class FontManager {
    public static final String ROOT = "fonts/",
            FONTAWESOME = "fontawesome-webfont.ttf",
            ROBOTO = "Roboto-LightItalic.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    /// use it by:
    //yourTextView.setTypeface(FontManager.getTypeface(FontManager.));
}
