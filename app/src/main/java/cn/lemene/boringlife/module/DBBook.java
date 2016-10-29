package cn.lemene.boringlife.module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 豆瓣图书实体类
 * @author snail 2016/10/22 17:55
 * @version v1.0
 */

public class DBBook implements Serializable {
    /** ID */
    @SerializedName("id")
    private String mId;

    /** 评价 */
    @SerializedName("rating")
    private Rating mRating;

    /** 标题 */
    @SerializedName("title")
    private String mTitle;

    /** 副标题 */
    @SerializedName("subtitle")
    private String mSubtile;

    /** 作者列表 */
    @SerializedName("author")
    private List<String> mAuthors;

    /** 出版日期 */
    @SerializedName("pubdate")
    private String mPUbDate;

    /** 标签 */
    @SerializedName("tags")
    private List<Tags> mTagses;

    /** 原作名 */
    @SerializedName("origin_title")
    private String mOriginTitle;

    /** 封面 */
    @SerializedName("image")
    private String mCover;

    /** 装帧方式 */
    @SerializedName("binding")
    private String mBinding;

    /** 译者列表 */
    @SerializedName("translator")
    private List<String> mTranslators;

    /** 目录 */
    @SerializedName("catalog")
    private String mCatalog;

    /** 页数 */
    @SerializedName("pages")
    private String mPages;

    /** 封面图片集合(不同大小) */
    @SerializedName("images")
    private Cover mCoveres;

    /** 网页链接 */
    @SerializedName("alt")
    private String mWebLink;

    /** 网页链接标题 */
    @SerializedName("alt_title")
    private String mWebTitle;

    /** Api 链接 */
    @SerializedName("url")
    private String mApiUrl;

    /** 出版社 */
    @SerializedName("publisher")
    private String mPUblisher;

    /** ISBN(10个字符) */
    @SerializedName("isbn10")
    private String mIsbn10;

    /** ISBN(13个字符) */
    @SerializedName("isbn13")
    private String mIsbn13;

    /** 作者简介 */
    @SerializedName("author_intro")
    private String mAuthorIntro;

    /** 内容简介 */
    @SerializedName("summary")
    private String mSummary;

    /** 价格 */
    @SerializedName("price")
    private String mPrice;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Rating getRating() {
        return mRating;
    }

    public void setRating(Rating rating) {
        mRating = rating;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtile() {
        return mSubtile;
    }

    public void setSubtile(String subtile) {
        mSubtile = subtile;
    }

    public List<String> getAuthors() {
        return mAuthors;
    }

    public void setAuthors(List<String> authors) {
        mAuthors = authors;
    }

    public String getPUbDate() {
        return mPUbDate;
    }

    public void setPUbDate(String PUbDate) {
        mPUbDate = PUbDate;
    }

    public List<Tags> getTagses() {
        return mTagses;
    }

    public void setTagses(List<Tags> tagses) {
        mTagses = tagses;
    }

    public String getOriginTitle() {
        return mOriginTitle;
    }

    public void setOriginTitle(String originTitle) {
        mOriginTitle = originTitle;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }

    public String getBinding() {
        return mBinding;
    }

    public void setBinding(String binding) {
        mBinding = binding;
    }

    public List<String> getTranslators() {
        return mTranslators;
    }

    public void setTranslators(List<String> translators) {
        mTranslators = translators;
    }

    public String getCatalog() {
        return mCatalog;
    }

    public void setCatalog(String catalog) {
        mCatalog = catalog;
    }

    public String getPages() {
        return mPages;
    }

    public void setPages(String pages) {
        mPages = pages;
    }

    public Cover getCoveres() {
        return mCoveres;
    }

    public void setCoveres(Cover coveres) {
        mCoveres = coveres;
    }

    public String getWebLink() {
        return mWebLink;
    }

    public void setWebLink(String webLink) {
        mWebLink = webLink;
    }

    public String getWebTitle() {
        return mWebTitle;
    }

    public void setWebTitle(String webTitle) {
        mWebTitle = webTitle;
    }

    public String getApiUrl() {
        return mApiUrl;
    }

    public void setApiUrl(String apiUrl) {
        mApiUrl = apiUrl;
    }

    public String getPUblisher() {
        return mPUblisher;
    }

    public void setPUblisher(String PUblisher) {
        mPUblisher = PUblisher;
    }

    public String getIsbn10() {
        return mIsbn10;
    }

    public void setIsbn10(String isbn10) {
        mIsbn10 = isbn10;
    }

    public String getIsbn13() {
        return mIsbn13;
    }

    public void setIsbn13(String isbn13) {
        mIsbn13 = isbn13;
    }

    public String getAuthorIntro() {
        return mAuthorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        mAuthorIntro = authorIntro;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getAuthorsString() {
        return list2String(mAuthors);
    }

    public String getTranslatorsString() {
        return list2String(mTranslators);
    }

    public String getTagString() {
        return list2String(mTagses);
    }

    /**
     * 字符串列表转换为字符串
     * @param list
     * @return
     */
    public String list2String(List<?> list) {
        StringBuilder builder = new StringBuilder();
        if (list != null && list.size() > 0) {
            int i;
            for (i = 0; i < list.size() - 2; i++) {
                builder.append(list.get(i) + ", ");
            }
            builder.append(list.get(i));
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBBook dbBook = (DBBook) o;

        return mId != null ? mId.equals(dbBook.mId) : dbBook.mId == null;

    }

    @Override
    public int hashCode() {
        return mId != null ? mId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DBBook{" +
                "mId='" + mId + '\'' +
                ", mRating=" + mRating +
                ", mTitle='" + mTitle + '\'' +
                ", mSubtile='" + mSubtile + '\'' +
                ", mAuthors=" + mAuthors +
                ", mPUbDate='" + mPUbDate + '\'' +
                ", mTagses=" + mTagses +
                ", mOriginTitle='" + mOriginTitle + '\'' +
                ", mCover='" + mCover + '\'' +
                ", mBinding='" + mBinding + '\'' +
                ", mTranslators=" + mTranslators +
                ", mCatalog='" + mCatalog + '\'' +
                ", mPages='" + mPages + '\'' +
                ", mCoveres=" + mCoveres +
                ", mWebLink='" + mWebLink + '\'' +
                ", mWebTitle='" + mWebTitle + '\'' +
                ", mApiUrl='" + mApiUrl + '\'' +
                ", mPUblisher='" + mPUblisher + '\'' +
                ", mIsbn10='" + mIsbn10 + '\'' +
                ", mIsbn13='" + mIsbn13 + '\'' +
                ", mAuthorIntro='" + mAuthorIntro + '\'' +
                ", mSummary='" + mSummary + '\'' +
                ", mPrice='" + mPrice + '\'' +
                '}';
    }

    /** 图书评价 */
    public class Rating implements Serializable {
        /** 最高评价 */
        private int max;

        /** 最低评价 */
        private int min;

        /** 平均评价 */
        private float average;

        /** 评价人数 */
        private int numRaters;

        public int getMax() {
            return max;
        }

        public int getMin() {
            return min;
        }

        public float getAverage() {
            return average;
        }

        public int getNumRaters() {
            return numRaters;
        }
    }

    /** 图书标签 */
    public class Tags implements Serializable {
        /** 标签数量 */
        private int count;

        /** 标签名称 */
        private String name;

        /** 标签标题 */
        private String title;

        @Override
        public String toString() {
            return name;
        }
    }

    /** 封面图片 */
    public class Cover implements Serializable {
        /** 封面小图 */
        private String small;

        /** 普通封面 */
        private String medium;

        /** 封面大图 */
        private String large;

        public String getSmall() {
            return small;
        }

        public String getMedium() {
            return medium;
        }

        public String getLarge() {
            return large;
        }
    }

}
