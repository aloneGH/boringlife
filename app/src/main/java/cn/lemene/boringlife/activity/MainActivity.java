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
import cn.lemene.boringlife.interfaces.DBBookService;
import cn.lemene.boringlife.manager.RetrofitManager;
import cn.lemene.boringlife.module.QueryDBBookRespone;
import cn.lemene.boringlife.utils.SoftInputUtils;
import cn.lemene.boringlife.view.DBBookSearchResultView;
import cn.lemene.boringlife.view.MainNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

    @BindView(R.id.search_result_view)
    protected DBBookSearchResultView mSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
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

    private void searchBooks(String query) {
        Retrofit retrofit = RetrofitManager.getIntance().createDBBookRetrofit();
        DBBookService bookService = retrofit.create(DBBookService.class);
        bookService.searchBooksByKeyword(query).enqueue(new Callback<QueryDBBookRespone>() {
            @Override
            public void onResponse(Call<QueryDBBookRespone> call, Response<QueryDBBookRespone> response) {
                Logger.d("query book done");
                QueryDBBookRespone bookRespone = response.body();
                if (response.isSuccessful() && bookRespone != null) {
                    SoftInputUtils.hideSoftInput(MainActivity.this);
                    mSearchResult.onSearchSuccess(bookRespone.getBooks());
                }
            }

            @Override
            public void onFailure(Call<QueryDBBookRespone> call, Throwable t) {
                String msg = t.getLocalizedMessage();
                Logger.e(t, "query book error " + msg);
                mSearchResult.onSearchError(msg);
            }
        });
    }
}
