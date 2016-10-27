package cn.lemene.boringlife.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;
import cn.lemene.boringlife.activity.DBBookDetailActivity;
import cn.lemene.boringlife.adapter.DBBookListAdapter;
import cn.lemene.boringlife.interfaces.DBBookService;
import cn.lemene.boringlife.manager.DBErrorManager;
import cn.lemene.boringlife.manager.QueryDBBookErrorConvertor;
import cn.lemene.boringlife.manager.RetrofitManager;
import cn.lemene.boringlife.module.DBBook;
import cn.lemene.boringlife.module.QueryDBBookErrorRespone;
import cn.lemene.boringlife.module.QueryDBBookRespone;
import cn.lemene.boringlife.utils.SoftInputUtils;
import cn.lemene.boringlife.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 豆瓣图书搜索结果组件
 * @author snail 2016/10/24 14:58
 * @version v1.0
 */

public class DBBookSearchView extends SwipeRefreshLayout
        implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, AbsListView.OnScrollListener {
    private Context mContext;
    private DBBookListAdapter mAdapter;
    private int mLastItemPos;

    /** 搜索结果起始位置的偏移量 */
    private int mStartOffset;

    /** 搜索结果最大值 */
    private int mMaxQueryCount;

    /** 区别搜索结果是全新的搜索还是加载更多的搜索 */
    private boolean mIsNewQuery;

    @BindView(R.id.search_result)
    protected ListView mSearchResult;

    private SearchView mSearchView;

    /** 默认的搜索结果数量 */
    private static final int DEFAULT_QUERY_COUNT = 20;

    public DBBookSearchView(Context context) {
        this(context, null);
    }

    public DBBookSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DBBook book = mAdapter.getItem(position);
        showBookDetail(book);
    }

    @Override
    public void onRefresh() {
        Logger.d("refresh query...");
        mIsNewQuery = true;
        searchBook(getQueryString());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mIsNewQuery = true;
        searchBook(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        setEnabled(!TextUtils.isEmpty(newText));
        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if (mLastItemPos == mAdapter.getCount()) {
                loadMoreBook();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastItemPos = firstVisibleItem + visibleItemCount;
    }

    /**
     * 搜索图书(默认搜索20条记录)
     * @param query 搜索的关键字
     */
    public void searchBook(String query) {
        mStartOffset = 0;
        searchBook(query, mStartOffset, DEFAULT_QUERY_COUNT);
    }

    /**
     * 搜索图书
     * @param query 搜索的关键字
     * @param start 搜索结果的起始偏移
     * @param count 搜索结果的数量
     */
    public void searchBook(String query, int start, int count) {
        Logger.d("query = " + query + ", start = " + start + ", count = " + count);

        if (start < 0 || count < 0) {
            Logger.e("invalid params ${start} or ${count}");
            return;
        }

        if (isRefreshing()) {
            Logger.w("refreshing now");
            setRefreshing(false);
        }

        Retrofit retrofit = RetrofitManager.getIntance().createDBBookRetrofit();
        DBBookService bookService = retrofit.create(DBBookService.class);
        bookService.searchBooksByKeyword(query, start, count).enqueue(new Callback<QueryDBBookRespone>() {
            @Override
            public void onResponse(Call<QueryDBBookRespone> call, Response<QueryDBBookRespone> response) {
                Logger.d("query book done");
                onSearchRespone(response);
            }

            @Override
            public void onFailure(Call<QueryDBBookRespone> call, Throwable t) {
                String msg = t.getLocalizedMessage();
                Logger.e(t, "query book error " + msg);
                onSearchFailure(msg);
            }
        });
        onSearchStart();
    }

    public void setSearchView(SearchView searchView) {
        mSearchView = searchView;
        mSearchView.setOnQueryTextListener(this);
    }

    private void init(Context context) {
        Logger.init(getClass().getSimpleName());
        mContext = context;
        mLastItemPos = -1;
        mStartOffset = 0;
        mMaxQueryCount = 0;
        mIsNewQuery = true;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_db_book_search_result, this, true);
        initView(view);
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
        mSearchResult.setOnItemClickListener(this);
        setOnRefreshListener(this);
        setColorSchemeResources(R.color.colorAccent);
        setEnabled(false);
        mSearchResult.setOnScrollListener(this);
    }

    private void showBookDetail(DBBook book) {
        Logger.d("book = " + book);
        Intent intent = new Intent(mContext, DBBookDetailActivity.class);
        intent.putExtra(DBBookDetailActivity.KEY_BOOK, book);
        mContext.startActivity(intent);
    }

    private void updateBookList(List<DBBook> books) {
        Logger.d("books = " + (books == null ? null : books.size()));
        if (books == null) {
            return;
        }

        if (mAdapter == null) {
            mAdapter = new DBBookListAdapter(mContext, books);
            mSearchResult.setAdapter(mAdapter);
        } else {
            mAdapter.updateBooks(books, mIsNewQuery);
        }
    }

    private void onSearchRespone(Response<QueryDBBookRespone> respone) {
        Logger.d("respone = " + respone);

        onSearchStop();
        List<DBBook> books = null;
        QueryDBBookRespone bookRespone;

        if (respone != null && respone.isSuccessful()) {
            if ((bookRespone = respone.body()) != null && (books = bookRespone.getBooks()) != null) {
                mMaxQueryCount = bookRespone.getTotal();
                mStartOffset += bookRespone.getCount();
                SoftInputUtils.hideSoftInput((Activity) mContext);
            } else {
                mMaxQueryCount = 0;
                ToastUtils.showToast(mContext, R.string.search_empty);
            }
        } else {
            onSearchResponeError(respone);
        }

        updateBookList(books);
    }

    private void onSearchResponeError(Response<QueryDBBookRespone> respone) {
        QueryDBBookErrorRespone errorRespone = null;

        if (respone != null) {
            String errorMsg = null;
            try {
                errorMsg = respone.errorBody().string();
            } catch (IOException e) {
                Logger.e(e, "get respone error msg error");
            }

            errorRespone = QueryDBBookErrorConvertor.fromJson(errorMsg);
            Logger.w("errorRespone = " + errorRespone);

        }

        DBErrorManager errorManager = DBErrorManager.getInstance();
        String msg;
        int code = DBErrorManager.CODE_999;
        if (errorRespone != null) {
            code = errorRespone.getCode();
            msg = errorRespone.getMsg();
        }

        msg = errorManager.getErrorMsg(mContext, code);

        msg += ": " + code;
        onSearchFailure(msg);
    }

    private void onSearchFailure(String msg) {
        Logger.w("msg = " + msg);
        onSearchStop();
        msg = mContext.getText(R.string.search_error) + msg;
        showErrorMsg(msg);
    }

    private void showErrorMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    private void onSearchStart() {
        setRefreshing(true);
        mSearchView.setEnabled(false);
    }

    private void onSearchStop() {
        setRefreshing(false);
        mSearchView.setEnabled(true);
    }

    private String getQueryString() {
        return mSearchView == null ? null : mSearchView.getQuery().toString();
    }

    private void loadMoreBook() {
        Logger.d("loading more book..." + mAdapter.getCount() + ", " + mMaxQueryCount);
        if (mAdapter == null || mAdapter.getCount() == mMaxQueryCount) {
            ToastUtils.showToast(mContext, "no more data");
            return;
        }

        mIsNewQuery = false;
        searchBook(getQueryString(), mStartOffset, DEFAULT_QUERY_COUNT);
    }
}
