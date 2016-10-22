package cn.lemene.boringlife.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;
import cn.lemene.boringlife.interfaces.DBBookService;
import cn.lemene.boringlife.manager.RetrofitManager;
import cn.lemene.boringlife.module.DBBookRespone;
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

    @BindView(R.id.search_input)
    protected EditText searchInput;

    @BindView(R.id.seach_result)
    protected TextView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        test();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void test() {
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks();
            }
        });
    }

    private void searchBooks() {
        String keyword = searchInput.getText().toString();

        Retrofit retrofit = RetrofitManager.getIntance().createRetrofit(RetrofitManager.DOUBAN_BOOK_BASE_URL);
        DBBookService bookService = retrofit.create(DBBookService.class);
        bookService.searchBooksByKeyword(keyword).enqueue(new Callback<DBBookRespone>() {
            @Override
            public void onResponse(Call<DBBookRespone> call, Response<DBBookRespone> response) {
                Log.d("cgt", "onRespone");
                Log.d("cgt", "data: " + response.body());
                DBBookRespone bookRespone = response.body();
                if (response.isSuccessful() && bookRespone != null) {
//                    String result = bookRespone.getRespone();
                    searchResult.setText(bookRespone.toString());
                }
            }

            @Override
            public void onFailure(Call<DBBookRespone> call, Throwable t) {
                Log.e("cgt", "onFailure", t);
            }
        });
    }
}
