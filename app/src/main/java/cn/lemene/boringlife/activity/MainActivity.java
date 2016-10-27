package cn.lemene.boringlife.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;
import cn.lemene.boringlife.view.DBBookSearchView;
import cn.lemene.boringlife.view.MainNavigationView;

/**
 * 主页面
 * @author snail 2016/10/20
 * @version v1.0
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawer;

    @BindView(R.id.nav_view)
    protected MainNavigationView mNavigationView;

    @BindView(R.id.search_view)
    protected DBBookSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        mSearchView.setSearchView(searchView);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void init() {
        Logger.init(getClass().getSimpleName());
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setDrawer(mDrawer);
    }
}
