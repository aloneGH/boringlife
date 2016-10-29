package cn.lemene.boringlife.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lemene.boringlife.R;

/**
 * 简介页面
 * @author snail 2016/10/27 17:18
 * @version v1.0
 */
public class SummaryFragment extends Fragment {
    private String mSummary;

    @BindView(R.id.summary_fragment_text_view)
    protected TextView mTextView;

    private static final String KEY_SUMMARY = "summary_fragment_summary";

    public static SummaryFragment newInstance(String summary) {
        Bundle args = new Bundle();
        args.putString(KEY_SUMMARY, summary);
        SummaryFragment fragment = new SummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_summary, container, false);
        initView(view);
        return view;
    }

    private void init() {
        Logger.init(getClass().getSimpleName());

        Bundle args = getArguments();
        if (args != null) {
            mSummary = args.getString(KEY_SUMMARY);
        }
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
        mTextView.setText(mSummary);
    }
}
