package cn.lemene.boringlife.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;
import cn.lemene.boringlife.activity.DBBookDetailActivity;
import cn.lemene.boringlife.adapter.DBBookListAdapter;
import cn.lemene.boringlife.module.DBBook;

/**
 * 豆瓣图书搜索结果组件
 * @author snail 2016/10/24 14:58
 * @version v1.0
 */

public class DBBookSearchResultView extends RelativeLayout implements AdapterView.OnItemClickListener {
    private Context mContext;
    private DBBookListAdapter mAdapter;

    @BindView(R.id.search_result)
    protected ListView mSearchResult;

    @BindView(R.id.search_error)
    protected TextView mSearchError;

    public DBBookSearchResultView(Context context) {
        this(context, null);
    }

    public DBBookSearchResultView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DBBookSearchResultView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Logger.init(getClass().getSimpleName());
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_db_book_search_result, this, true);
        initView(view);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DBBook book = mAdapter.getItem(position);
        showBookDetail(book);
    }

    public void onSearchSuccess(List<DBBook> books) {
        clearSearchError();

        if (books == null) {
            books = new ArrayList<>();
        }

        if (mAdapter == null) {
            mAdapter = new DBBookListAdapter(mContext, books);
            mSearchResult.setAdapter(mAdapter);
        } else {
            mAdapter.updateBooks(books);
        }
    }

    public void onSearchError(String msg) {
        msg = mContext.getText(R.string.search_error) + "\n" + msg;
        mSearchError.setText(msg);
    }

    public void clearSearchError() {
        mSearchError.setText(null);
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
        mSearchResult.setOnItemClickListener(this);
    }

    private void showBookDetail(DBBook book) {
        Logger.d("book = " + book);
        Intent intent = new Intent(mContext, DBBookDetailActivity.class);
        intent.putExtra(DBBookDetailActivity.KEY_BOOK, book);
        mContext.startActivity(intent);
    }
}
