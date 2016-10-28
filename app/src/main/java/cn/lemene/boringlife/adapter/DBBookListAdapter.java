package cn.lemene.boringlife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;
import cn.lemene.boringlife.activity.DBBookDetailActivity;
import cn.lemene.boringlife.module.DBBook;

/**
 * 图书列表适配器
 * @author snail 2016/10/24 11:23
 * @version v1.0
 */

public class DBBookListAdapter extends RecyclerView.Adapter<DBBookListAdapter.MyViewHolder> {
    private Context mContext;
    private List<DBBook> mList;
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(DBBook book, int position);
    }

    public DBBookListAdapter(Context context, List<DBBook> list) {
        mContext = context;
        mList = list;
    }

    public void updateBooks(List<DBBook> books, boolean replace) {
        if (replace) {
            mList = books;
        } else {
            mList.addAll(books);
        }

        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.onBindView(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public DBBook getItem(int position) {
        return mList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    private void notifyItemClick(DBBook book, int position) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(book, position);
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_cardview)
        protected CardView mCardView;

        @BindView(R.id.book_img)
        protected SimpleDraweeView mImg;

        @BindView(R.id.book_title)
        protected TextView mTitle;

        @BindView(R.id.book_author)
        protected TextView mAuthors;

        @BindView(R.id.book_sub_title)
        protected TextView mSubTitle;

        @BindView(R.id.book_pub_date)
        protected TextView mPubDate;

        @BindView(R.id.book_page)
        protected TextView mPage;

        @BindView(R.id.book_price)
        protected TextView mPrice;

        private MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void onBindView(int position) {
            initOnClickListener(position);
            updateView(getItem(position));
        }

        private void updateView(DBBook book) {
            mImg.setImageURI(book.getCover());
            mTitle.setText(book.getTitle());
            mAuthors.setText(String.format(mContext.getString(R.string.book_author), book.getAuthorsString()));
            mSubTitle.setText(String.format(mContext.getString(R.string.book_sub_title), book.getSubtile()));
            mPubDate.setText(String.format(mContext.getString(R.string.book_pub_date), book.getPUbDate()));
            mPage.setText(String.format(mContext.getString(R.string.book_page), book.getPages()));
            mPrice.setText(String.format(mContext.getString(R.string.book_price), book.getPrice()));
        }

        private void initOnClickListener(final int position) {
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemClick(getItem(position), position);
                }
            });
        }
    }
}
