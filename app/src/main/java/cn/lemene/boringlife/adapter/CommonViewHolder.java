package cn.lemene.boringlife.adapter;

import android.view.View;

/**
 * 通用的 MyViewHolder
 * @author cengt
 * @date 2016-8-15 上午9:52:24
 * @version v1.0
 * @param <T>
 */
public abstract class CommonViewHolder<T> {
	protected View view;

	/**
     * 初始化 UI 组件
     */
    public abstract void initView();
    
    /**
     * 初始化事件回调，比如注册点击事件
     */
    public abstract void initListener(int position, T item);
    
    /**
     * 更新 UI 组件
     * @param position Item 的位置
     * @param item
     */
    public abstract void updateView(int position, T item);
}
