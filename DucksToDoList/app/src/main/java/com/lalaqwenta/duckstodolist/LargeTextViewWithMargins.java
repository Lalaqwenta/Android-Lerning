package com.lalaqwenta.duckstodolist;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

public class LargeTextViewWithMargins extends androidx.appcompat.widget.AppCompatTextView {

    public int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public LargeTextViewWithMargins(Context context, int width) {
        super(context);
        setGravity(11); //center
        //setTypeface(Typeface.SANS_SERIF);
        setTextSize(30f);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (dpToPx(context, 300 * width), LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(dpToPx(context, 10), 10, dpToPx(context, 10), 10);
        setLayoutParams(layoutParams);
    }
}
