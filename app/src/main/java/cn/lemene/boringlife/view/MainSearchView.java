package cn.lemene.boringlife.view;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;

/**
 * 搜索框组件
 * @author snail 2016/10/20 16:56
 * @version v1.0
 */

public class MainSearchView extends SearchView {
    public MainSearchView(Context context) {
        this(context, null);
    }

    public MainSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
