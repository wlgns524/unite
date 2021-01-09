package com.rightcode.unite.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

public class DeviceSizeUtil {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }

    public static int getDeviceWidth(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.heightPixels;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        if (px <= 0) {
            px = (int) (dp * (displayMetrics.densityDpi / 160f));
        }

        return px;
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int getDp(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    // 사이즈 비율 변환
    public static ViewGroup.LayoutParams setAspectRatio(View v, int displaySize) {
        if (v == null) {
            return null;
        }

        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();

        if (layoutParams == null) {
            return null;
        }

        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
            int newSize = displaySize - (lp.leftMargin + lp.rightMargin + v.getPaddingLeft() + v.getPaddingRight());
            float ratio = newSize / (float) lp.width;
            lp.width = (int) (lp.width * ratio);
            lp.height = (int) (lp.height * ratio);
            v.setLayoutParams(lp);
        } else if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) layoutParams;
            int newSize = displaySize - (lp.leftMargin + lp.rightMargin + v.getPaddingLeft() + v.getPaddingRight());
            float ratio = newSize / (float) lp.width;
            lp.width = (int) (lp.width * ratio);
            lp.height = (int) (lp.height * ratio);
            v.setLayoutParams(lp);
        } else if (layoutParams instanceof RecyclerView.LayoutParams) {
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) layoutParams;
            int newSize = displaySize - (lp.leftMargin + lp.rightMargin + v.getPaddingLeft() + v.getPaddingRight());
            float ratio = newSize / (float) lp.width;
            lp.width = (int) (lp.width * ratio);
            lp.height = (int) (lp.height * ratio);
            v.setLayoutParams(lp);
        }

        return layoutParams;
    }

    public static int getMargin(View v) {
        int margin = 0;

        if (v != null) {
            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutParams;
                    margin = lp.leftMargin + lp.rightMargin + v.getPaddingLeft() + v.getPaddingRight();
                } else if (layoutParams instanceof LinearLayout.LayoutParams) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) layoutParams;
                    margin = lp.leftMargin + lp.rightMargin + v.getPaddingLeft() + v.getPaddingRight();
                } else if (layoutParams instanceof RecyclerView.LayoutParams) {
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) layoutParams;
                    margin = lp.leftMargin + lp.rightMargin + v.getPaddingLeft() + v.getPaddingRight();
                }
            }
        }

        return margin;
    }

    public static int getRatioHeight(int viewWidth, int width, int height) {
        int ratioHeight;

        if (width == height) {
            ratioHeight = viewWidth;
        } else {
            float ratio = (float) viewWidth / (float) width;
            ratioHeight = Math.round(height * ratio);
        }

        return ratioHeight;
    }
}
