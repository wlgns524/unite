package com.rightcode.unite.Util;

import android.graphics.Paint;
import android.widget.TextView;

public class StringUtil {

    public static void  cancelLine(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void underLine(TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}
