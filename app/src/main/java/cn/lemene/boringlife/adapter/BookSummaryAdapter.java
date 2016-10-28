package cn.lemene.boringlife.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.lemene.boringlife.fragment.SummaryFragment;

/**
 * @author snail 2016/10/28 17:29
 */

public class BookSummaryAdapter extends FragmentPagerAdapter {
    private List<String> mSummaries;
    private List<String> mTitles;

    public BookSummaryAdapter(FragmentManager fm, List<String> summaries, List<String> titles) {
        super(fm);
        mSummaries = summaries;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return SummaryFragment.newInstance(mSummaries.get(position));
    }

    @Override
    public int getCount() {
        return mSummaries.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
