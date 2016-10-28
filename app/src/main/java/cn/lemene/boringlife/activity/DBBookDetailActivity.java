package cn.lemene.boringlife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import cn.lemene.boringlife.R;
import cn.lemene.boringlife.fragment.DBBookDetailFragment;
import cn.lemene.boringlife.module.DBBook;

/**
 * 豆瓣图书详情页面
 * @author snail 2016/10/24 14:22
 * @version v1.0
 */
public class DBBookDetailActivity extends SingleFragmentActivity {

    public static final String KEY_BOOK = "activity_db_book_detail_key_book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
    }

    @Override
    public Fragment createFragment() {
        return DBBookDetailFragment.newInstance(getExtraBook());
    }

    @Override
    public int getFragmentContainer() {
        return R.id.book_detail_fragment_container;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private DBBook getExtraBook() {
        Intent intent = getIntent();
        return (DBBook) intent.getSerializableExtra(KEY_BOOK);
    }
}
