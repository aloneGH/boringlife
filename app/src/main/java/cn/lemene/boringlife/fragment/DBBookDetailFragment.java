package cn.lemene.boringlife.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;
import cn.lemene.boringlife.adapter.BookSummaryAdapter;
import cn.lemene.boringlife.module.DBBook;
import cn.lemene.boringlife.module.Summary;

/**
 * 豆瓣图书详情页面
 * @author snail 2016/10/25 9:38
 * @version v1.0
 */

public class DBBookDetailFragment extends Fragment {
    private DBBook mBook;

    @BindView(R.id.db_book_detail_collapsing_toolbar)
    protected CollapsingToolbarLayout mCollapsingToolbar;

    @BindView(R.id.db_book_detail_view_pager)
    protected ViewPager mViewPager;

    @BindView(R.id.db_book_detail_tab_layout)
    protected TabLayout mTabLayout;

    @BindView(R.id.db_book_detail_toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.db_book_detail_cover)
    protected SimpleDraweeView mBookImg;

    private static final String KEY_BOOK = "fragment_db_book_detail_key_book";

    public static DBBookDetailFragment newInstance(DBBook book) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_BOOK, book);
        DBBookDetailFragment fragment = new DBBookDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db_book_detail, container, false);
        initView(view);
        return view;
    }

    private void init() {
        Logger.init(getClass().getSimpleName());
        initArgs();
    }

    private void initArgs() {
        Bundle args = getArguments();
        if (args != null) {
            mBook = (DBBook) args.getSerializable(KEY_BOOK);
        }
        checkBook();
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);

        initToolbar();
        initViewPager();
        initTabLayout();

        mCollapsingToolbar.setTitle(mBook.getTitle());
        mBookImg.setImageURI(mBook.getCoveres().getLarge());
    }

    private void initViewPager() {
        List<Summary> summaries = new ArrayList<>();
        summaries.add(new Summary(getString(R.string.book_summary), mBook.getSummary()));
        summaries.add(new Summary(getString(R.string.book_author_intro), mBook.getAuthorIntro()));
        summaries.add(new Summary(getString(R.string.book_catalog), mBook.getCatalog()));
        summaries.add(new Summary(getString(R.string.book_detail), getBookDetail(mBook)));
        mViewPager.setAdapter(new BookSummaryAdapter(getChildFragmentManager(), summaries));
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void checkBook() {
        if (mBook == null) {
            Logger.e("mBook is null, check book failed");
            getActivity().finish();
        }
    }

    private String getBookDetail(DBBook book) {
        if (book == null) {
            return null;
        }

        DBBook.Rating rating = book.getRating();
        String ratingString = String.format(getString(R.string.book_rating), rating.getAverage(), rating.getNumRaters());
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(getString(R.string.book_author), book.getAuthors()) + "\n");
        builder.append(String.format(getString(R.string.book_origin_title), book.getOriginTitle()) + "\n");
        builder.append(String.format(getString(R.string.book_sub_title), book.getSubtile()) + "\n");
        builder.append(String.format(getString(R.string.book_pub_date), book.getPUbDate()) + "\n");
        builder.append(String.format(getString(R.string.book_publisher), book.getPUblisher()) + "\n");
        builder.append(String.format(getString(R.string.book_page), book.getPages()) + "\n");
        builder.append(String.format(getString(R.string.book_binding), book.getBinding()) + "\n");
        builder.append(String.format(getString(R.string.book_isbn13), book.getIsbn13()) + "\n");
        builder.append(String.format(getString(R.string.book_price), book.getPrice()) + "\n");
        builder.append(String.format(getString(R.string.book_tag), book.getTagString()) + "\n");
        builder.append(ratingString + "\n");
        builder.append(String.format(getString(R.string.book_web_link), book.getWebLink()) + "\n");
        return builder.toString();
    }
}
