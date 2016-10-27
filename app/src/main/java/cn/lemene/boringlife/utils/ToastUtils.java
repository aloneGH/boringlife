package cn.lemene.boringlife.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具类, 可以显示一个Toast
 * @author snail 2016/10/27 9:36
 * @version v1.0
 */

public class ToastUtils {
    private static Toast sToast;

    public static void showToast(Context context, String text) {
        if (sToast == null) {
            sToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sToast.setDuration(Toast.LENGTH_SHORT);
            sToast.setText(text);
        }

        sToast.show();
    }

    public static void showToast(Context context, int resid) {
        if (sToast == null) {
            sToast = Toast.makeText(context, resid, Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sToast.setDuration(Toast.LENGTH_SHORT);
            sToast.setText(resid);
        }

        sToast.show();
    }
}
