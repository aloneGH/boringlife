package cn.lemene.boringlife.module;

import java.util.List;

/**
 * 豆瓣读书API的响应
 * @author snail 2016/10/22 12:44
 * @version v1.0
 */

public class DBBookRespone {
    private int count;

    private int start;

    private int total;

    private List<String> books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "DBBookRespone{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                '}';
    }
}
