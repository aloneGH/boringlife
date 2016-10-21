package cn.lemene.boringlife.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MenuItem;

import cn.lemene.boringlife.R;

/**
 * 侧滑导航栏组件
 * @author snail 2016/10/20 15:13
 * @version v1.0
 */

public class MainNavigationView extends NavigationView
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawer;

    public MainNavigationView(Context context) {
        this(context, null);
    }

    public MainNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:
                break;

            case R.id.nav_gallery:
                break;

            case R.id.nav_send:
                break;

            case R.id.nav_settings:
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_slideshow:
                break;
        }

        closeDrawer();
        return true;
    }

    public void setDrawer(DrawerLayout drawer) {
        mDrawer = drawer;
    }

    private void closeDrawer() {
        if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
    }
}
