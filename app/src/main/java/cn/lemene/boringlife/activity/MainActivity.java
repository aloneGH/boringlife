package cn.lemene.boringlife.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;
import cn.lemene.boringlife.adapter.BookListAdapter;
import cn.lemene.boringlife.interfaces.DBBookService;
import cn.lemene.boringlife.manager.RetrofitManager;
import cn.lemene.boringlife.module.DBBook;
import cn.lemene.boringlife.module.QueryDBBookRespone;
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
    protected EditText mSearchInput;

    @BindView(R.id.search_result)
    protected ListView mSearchResult;

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

    private void test() {
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks();
            }
        });
    }

    private void searchBooks() {
        String keyword = mSearchInput.getText().toString();

        Retrofit retrofit = RetrofitManager.getIntance().createRetrofit(RetrofitManager.DOUBAN_BOOK_BASE_URL);
        DBBookService bookService = retrofit.create(DBBookService.class);
        bookService.searchBooksByKeyword(keyword).enqueue(new Callback<QueryDBBookRespone>() {
            @Override
            public void onResponse(Call<QueryDBBookRespone> call, Response<QueryDBBookRespone> response) {
                Logger.d("query book done");
                QueryDBBookRespone bookRespone = response.body();
                if (response.isSuccessful() && bookRespone != null) {
                    updateBookList(bookRespone.getBooks());
                }
            }

            @Override
            public void onFailure(Call<QueryDBBookRespone> call, Throwable t) {
                Logger.e(t, "query book error");
            }
        });
    }

    /** 更新图书列表 */
    private void updateBookList(List<DBBook> books) {
        mSearchResult.setAdapter(new BookListAdapter(this, books));
    }
}
