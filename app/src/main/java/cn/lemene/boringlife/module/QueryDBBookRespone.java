package cn.lemene.boringlife.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 豆瓣图书搜索结果的响应
 * @author snail 2016/10/22 12:44
 * @version v1.0
 */

public class QueryDBBookRespone {
    /** 请求的搜索结果数量 */
    @SerializedName("count")
    private int mCount;

    /** 搜索结果起始位置 */
    @SerializedName("start")
    private int mStart;

    /** 返回的搜索结果数量 */
    @SerializedName("total")
    private int mTotal;

    /** 搜索的图书列表 */
    @SerializedName("books")
    private List<DBBook> mBooks;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getStart() {
        return mStart;
    }

    public void setStart(int start) {
        mStart = start;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public List<DBBook> getBooks() {
        return mBooks;
    }

    public void setBooks(List<DBBook> books) {
        mBooks = books;
    }

    @Override
    public String toString() {
        return "QueryDBBookRespone{" +
                "mCount=" + mCount +
                ", mStart=" + mStart +
                ", mTotal=" + mTotal +
                ", mBooks=" + mBooks +
                '}';
    }
}
