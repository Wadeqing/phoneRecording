package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/1.
 */

public class PreferentialResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : {"couponNum":0,"activities":[{"activityId":1,"h5Url":"http://luyin.4008365777.com/sinomini/landing.html","status":1,"taskDesc":"每天首次分享成功可随机获得优惠券","taskSeq":1,"taskTitle":"分享给好友","taskType":1},{"activityId":2,"h5Url":"","status":1,"taskDesc":"五星好评后即可获取优惠券","taskSeq":1,"taskTitle":"去五星好评","taskType":4}]}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * couponNum : 0
         * activities : [{"activityId":1,"h5Url":"http://luyin.4008365777.com/sinomini/landing.html","status":1,"taskDesc":"每天首次分享成功可随机获得优惠券","taskSeq":1,"taskTitle":"分享给好友","taskType":1},{"activityId":2,"h5Url":"","status":1,"taskDesc":"五星好评后即可获取优惠券","taskSeq":1,"taskTitle":"去五星好评","taskType":4}]
         */

        public int couponNum;
        public List<ActivitiesBean> activities;

        public static class ActivitiesBean {
            /**
             * activityId : 1
             * h5Url : http://luyin.4008365777.com/sinomini/landing.html
             * status : 1
             * taskDesc : 每天首次分享成功可随机获得优惠券
             * taskSeq : 1
             * taskTitle : 分享给好友
             * taskType : 1
             */

            public int activityId;
            public String h5Url;
            public int status;
            public String taskDesc;
            public int taskSeq;
            public String taskTitle;
            public int taskType;
        }
    }
}
