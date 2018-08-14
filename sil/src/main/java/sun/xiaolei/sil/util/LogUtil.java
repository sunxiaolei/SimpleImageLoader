package sun.xiaolei.sil.util;

import android.util.Log;

/*
 * @author sun
 * description:
 */
public class LogUtil {

    private static final String TAG = "ImageLoader";

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void v(String msg) {
        Log.v(TAG, msg);
    }
}
