package cn.lemene.boringlife.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

/**
 * 单一Fragment的Activity
 * @author snail 2016/10/25 9:41
 * @version v1.0
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFragment();

    public abstract int getFragmentContainer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.init(getClass().getSimpleName());
        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(getFragmentContainer(), createFragment())
                .commitAllowingStateLoss();
    }
}
