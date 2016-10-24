package cn.lemene.boringlife.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘工具类
 * @author snail 2016/10/24 17:46
 * @version v1.0
 */

public class SoftInputUtils {
    /**
     * 隐藏软键盘输入法
     * @param activity
     */
    public static void hideSoftInput(Activity activity) {
        if (activity != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 显示软键盘输入法
     * @param view
     */
    public static void showSoftInput(Context context, View view) {
        if (context != null && view != null) {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
