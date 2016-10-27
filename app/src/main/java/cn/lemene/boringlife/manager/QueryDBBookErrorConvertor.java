package cn.lemene.boringlife.manager;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.lemene.boringlife.module.QueryDBBookErrorRespone;

/**
 * 转换豆瓣图书错误信息
 * @author snail 2016/10/27 17:32
 * @version v1.0
 */

public class QueryDBBookErrorConvertor {
    public static QueryDBBookErrorRespone fromJson(String json) {
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, QueryDBBookErrorRespone.class);
        } else {
            return null;
        }
    }
}
