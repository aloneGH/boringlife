package cn.lemene.boringlife.interfaces;

import cn.lemene.boringlife.module.QueryDBBookRespone;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 豆瓣读书API
 * @author snail 2016/10/22 11:20
 * @version v1.0
 */

public interface DBBookService {
    /**
     * 通过关键字搜索图书
     * @param keyword 想要搜索的关键字
     * @return 搜索结果字符串
     */
    @GET("book/search")
    Call<QueryDBBookRespone> searchBooksByKeyword(@Query("q") String keyword);

    /**
     * 通过标签搜索图书
     * @param tag 想要搜索的标签
     * @return 搜索结果字符串
     */
    @GET("book/search")
    Call<QueryDBBookRespone> searchBooksByTag(@Query("t") String tag);
}
