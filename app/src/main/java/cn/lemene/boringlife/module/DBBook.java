package cn.lemene.boringlife.module;

import com.google.gson.annotations.SerializedName;

/**
 * @author cengt
 * @date 2016/10/22 17:55
 */

public class DBBook {
    /** 评价 */
    @SerializedName("rating")
    private Rating mRating;

    /** 副标题 */
    @SerializedName("subtitle")
    private String mSubtile;

    /** 评价 */
    public class Rating {
        private int max;
        private int min;
        private float average;
        private int numRaters;
    }
}
