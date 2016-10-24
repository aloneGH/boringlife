package cn.lemene.boringlife.manager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 管理Retrofit
 * @author snail 2016/10/22 14:44
 * @version v1.0
 */

public class RetrofitManager {
    private static transient RetrofitManager sIntance;

    /** 豆瓣读书API的基础路径 */
    public static final String BASE_URL_DOUBAN_BOOK = "https://api.douban.com/v2/";

    public static RetrofitManager getIntance() {
        if (sIntance == null) {
            synchronized (RetrofitManager.class) {
                if (sIntance == null) {
                    sIntance = new RetrofitManager();
                }
            }
        }
        return sIntance;
    }

    public Retrofit createRetrofit(String baseUrl) {
        return createRetrofit(baseUrl, createDefaultHttpClient());
    }

    public Retrofit createDBBookRetrofit() {
        return createRetrofit(BASE_URL_DOUBAN_BOOK);
    }

    public Retrofit createRetrofit(String baseUrl, OkHttpClient client) {
        return createRetrofit(baseUrl, client, GsonConverterFactory.create());
    }

    public Retrofit createRetrofit(String baseUrl, OkHttpClient client, GsonConverterFactory factory) {
        if (baseUrl == null || client == null || factory == null) {
            return null;
        }

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(factory)
                .build();
    }

    private RetrofitManager() {
        if (sIntance != null) {
            throw new IllegalAccessError("already has a instance, sIntance = " + sIntance);
        }
    }

    private OkHttpClient createDefaultHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
