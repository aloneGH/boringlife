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
     */
    @GET("book/search")
    Call<QueryDBBookRespone> searchBooksByKeyword(@Query("q") String keyword);

    /**
     * 通过关键字搜索图书
     * @param keyword 想要搜索的关键字
     * @param start 搜索结果的起始位置偏移
     * @param count 搜索结果的数量, 最大值为100
     */
    @GET("book/search")
    Call<QueryDBBookRespone> searchBooksByKeyword(@Query("q") String keyword,
                                                  @Query("start") int start,
                                                  @Query("count") int count);

    /**
     * 通过标签搜索图书
     * @param tag 想要搜索的标签
     */
    @GET("book/search")
    Call<QueryDBBookRespone> searchBooksByTag(@Query("t") String tag);

    /**
     * 通过标签搜索图书
     * @param tag 想要搜索的标签
     * @param start 搜索结果的起始位置偏移
     * @param count 搜索结果的数量, 最大值为100
     */
    @GET("book/search")
    Call<QueryDBBookRespone> searchBooksByTag(@Query("t") String tag,
                                              @Query("start") int start,
                                              @Query("count") int count);
}
