package cn.lemene.boringlife.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.lemene.boringlife.fragment.SummaryFragment;
import cn.lemene.boringlife.module.Summary;

/**
 * @author snail 2016/10/28 17:29
 */

public class BookSummaryAdapter extends FragmentPagerAdapter {
    private List<Summary> mSummaries;

    public BookSummaryAdapter(FragmentManager fm, List<Summary> summaries) {
        super(fm);
        mSummaries = summaries;
    }

    @Override
    public Fragment getItem(int position) {
        return SummaryFragment.newInstance(mSummaries.get(position).getSummary());
    }

    @Override
    public int getCount() {
        return mSummaries.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSummaries.get(position).getTitle();
    }
}
