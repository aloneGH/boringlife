package cn.lemene.boringlife.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.orhanobut.logger.Logger;

import cn.lemene.boringlife.module.DBBook;

/**
 * 豆瓣图书详情页面
 * @author snail 2016/10/25 9:38
 * @version v1.0
 */

public class DBBookDetailFragment extends Fragment {
    private DBBook mBook;

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

    private void checkBook() {
        Logger.d("mBook = " + mBook);
        if (mBook == null) {
            Logger.e("mBook is null!");
            getActivity().finish();
        }
    }
}
