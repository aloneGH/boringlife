package cn.lemene.boringlife.manager;

import android.content.Context;

import cn.lemene.boringlife.R;

/**
 * @author snail 2016/10/27 15:55
 * @version v1.0
 */

public class DBErrorManager {
    private static transient DBErrorManager sIntance;

    public static final int CODE_400        = 400;
    public static final int CODE_401        = 401;
    public static final int CODE_403        = 403;
    public static final int CODE_404        = 404;
    public static final int CODE_500        = 500;

    public static final int CODE_999        = 999;
    public static final int CODE_1000       = 1000;
    public static final int CODE_1001       = 1001;
    public static final int CODE_1002       = 1002;
    public static final int CODE_1003       = 1003;
    public static final int CODE_1004       = 1004;
    public static final int CODE_1005       = 1005;
    public static final int CODE_1006       = 1006;
    public static final int CODE_1007       = 1007;
    public static final int CODE_1008       = 1008;
    public static final int CODE_1009       = 1009;
    public static final int CODE_1010       = 1010;
    public static final int CODE_1011       = 1011;
    public static final int CODE_1012       = 1012;
    public static final int CODE_1013       = 1013;

    public static final int CODE_100        = 100;
    public static final int CODE_107        = 107;

    public static DBErrorManager getInstance() {
        if (sIntance == null) {
            synchronized (DBErrorManager.class) {
                if (sIntance == null) {
                    sIntance = new DBErrorManager();
                }
            }
        }
        return sIntance;
    }

    private DBErrorManager() {
        if (sIntance != null) {
            throw new IllegalStateException("already has a instance: " + sIntance);
        }
    }

    public int getErrorMsg(int code) {
        switch (code) {
            case CODE_400:
                return R.string.http_error_400;

            case CODE_401:
                return R.string.http_error_401;

            case CODE_403:
                return R.string.http_error_403;

            case CODE_404:
                return R.string.http_error_404;

            case CODE_500:
                return R.string.http_error_500;

            case CODE_999:
                return R.string.db_error_999;

            case CODE_1000:
                return R.string.db_error_1000;

            case CODE_1001:
                return R.string.db_error_1001;

            case CODE_1002:
                return R.string.db_error_1002;

            case CODE_1003:
                return R.string.db_error_1003;

            case CODE_1004:
                return R.string.db_error_1004;

            case CODE_1005:
                return R.string.db_error_1005;

            case CODE_1006:
                return R.string.db_error_1006;

            case CODE_1007:
                return R.string.db_error_1007;

            case CODE_1008:
                return R.string.db_error_1008;

            case CODE_1009:
                return R.string.db_error_1009;

            case CODE_1010:
                return R.string.db_error_1010;

            case CODE_1011:
                return R.string.db_error_1011;

            case CODE_1012:
                return R.string.db_error_1012;

            case CODE_1013:
                return R.string.db_error_1013;

            case CODE_100:
                return R.string.db_error_100;

            case CODE_107:
                return R.string.db_error_107;

            default:
                return -1;
        }
    }

    public String getErrorMsg(Context context, int code) {
        int resid = getErrorMsg(code);
        if (context != null && resid > 0) {
            return context.getString(getErrorMsg(code));
        } else {
            return null;
        }
    }
}
