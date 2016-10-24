package cn.lemene.boringlife.adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;
import cn.lemene.boringlife.module.DBBook;

/**
 * 图书列表适配器
 * @author snail 2016/10/24 11:23
 * @version v1.0
 */

public class BookListAdapter extends CommonAdapter<DBBook> {

    public BookListAdapter(Context context, List<DBBook> list) {
        super(context, list);
    }

    @Override
    protected CommonViewHolder<DBBook> createViewHolder() {
        return new ViewHolder();
    }

    @Override
    protected int getLayout() {
        return R.layout.list_item_book;
    }

    public void updateBooks(List<DBBook> books) {
        mList = books;
        notifyDataSetChanged();
    }

    protected class ViewHolder extends CommonViewHolder<DBBook> {
        @BindView(R.id.book_title)
        protected TextView mTitle;

        @BindView(R.id.book_author)
        protected TextView mAuthors;

        @Override
        public void initView() {
            ButterKnife.bind(this, view);
        }

        @Override
        public void initListener(int position, DBBook item) {}

        @Override
        public void updateView(int position, DBBook item) {
            mTitle.setText(item.getTitle());
            mAuthors.setText(item.getAuthorsString());
        }
    }
}
