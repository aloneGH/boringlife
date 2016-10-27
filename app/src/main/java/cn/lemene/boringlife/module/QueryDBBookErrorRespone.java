package cn.lemene.boringlife.module;

import com.google.gson.annotations.SerializedName;

/**
 * 豆瓣图书搜索出错的响应
 * @author snail 2016/10/27 17:02
 * @version v1.0
 */

public class QueryDBBookErrorRespone {
    /** 错误信息 */
    @SerializedName("msg")
    private String mMsg;

    /** 错误代码 */
    @SerializedName("code")
    private int mCode;

    /** 请求路径 */
    @SerializedName("request")
    private String mRequest;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getRequest() {
        return mRequest;
    }

    public void setRequest(String request) {
        mRequest = request;
    }

    @Override
    public String toString() {
        return "QueryDBBookErrorRespone{" +
                "mMsg='" + mMsg + '\'' +
                ", mCode=" + mCode +
                ", mRequest='" + mRequest + '\'' +
                '}';
    }
}
