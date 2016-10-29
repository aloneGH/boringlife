package cn.lemene.boringlife.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

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

public class DBBookSearchView extends RelativeLayout
        implements SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener, DBBookListAdapter.OnItemClickListener {
    private Context mContext;
    private DBBookListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    /** 搜索结果起始位置的偏移量 */
    private int mStartOffset;

    /** 搜索结果最大值 */
    private int mMaxQueryCount;

    /** 区别搜索结果是全新的搜索还是加载更多的搜索 */
    private boolean mIsNewQuery;

    @BindView(R.id.search_result)
    protected RecyclerView mSearchResult;

    @BindView(R.id.search_refresh_container)
    protected SwipeRefreshLayout mRefreshLayout;

    private SearchView mSearchView;

    /** 默认的搜索结果数量 */
    private static final int DEFAULT_QUERY_COUNT = 100;

    public DBBookSearchView(Context context) {
        this(context, null);
    }

    public DBBookSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
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
    public void onItemClick(DBBook book, int position) {
        showBookDetail(book);
    }

    /**
     * 搜索图书
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

        if (mRefreshLayout.isRefreshing()) {
            Logger.w("refreshing now");
            mRefreshLayout.setRefreshing(false);
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
        mStartOffset = 0;
        mMaxQueryCount = 0;
        mIsNewQuery = true;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_db_book_search_result, this, true);
        initView(view);
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        setEnabled(false);

        mLayoutManager = new LinearLayoutManager(mContext);
        mSearchResult.setHasFixedSize(true);
        mSearchResult.setLayoutManager(mLayoutManager);
        mSearchResult.addOnScrollListener(mOnScrollListener);
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
            mAdapter.setOnItemClickListener(this);
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
        String msg = null;
        int code = -1;
        if (errorRespone != null) {
            code = errorRespone.getCode();
            msg = errorManager.getErrorMsg(mContext, code);
            if (msg == null) {
                msg = errorRespone.getMsg();
            }
        }

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
        mRefreshLayout.setRefreshing(true);
        mSearchView.setEnabled(false);
    }

    private void onSearchStop() {
        mRefreshLayout.setRefreshing(false);
        mSearchView.setEnabled(true);
    }

    private String getQueryString() {
        return mSearchView == null ? null : mSearchView.getQuery().toString();
    }

    private void loadMoreBook() {
        Logger.d("loading more book..." + mAdapter.getItemCount() + ", " + mMaxQueryCount);
        if (mAdapter == null || mAdapter.getItemCount() >= mMaxQueryCount) {
            ToastUtils.showToast(mContext, R.string.search_no_more_data);
            return;
        }

        mIsNewQuery = false;
        searchBook(getQueryString(), mStartOffset, DEFAULT_QUERY_COUNT);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE) {
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem == mAdapter.getItemCount() - 1) {
                    loadMoreBook();
                }
            }
        }
    };
}
