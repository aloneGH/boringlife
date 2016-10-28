package cn.lemene.boringlife.manager;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author snail 2016/10/28 13:41
 * @version v1.0
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Fresco.initialize(this);
    }
}
