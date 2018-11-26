package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/11/28.
 */

public class PersonalInfoResponse implements Serializable{


    /**
     * code : 0
     * codeInfo :
     * data : {"callRecordLeft":"0","chargeFlag":"1","justFee":"0.0","lawSwitch":0,"lawTools":"http://apps.legaland.cn/CloudOffice/tool/?winzoom=1","law_consult_switch":"0","leftMoney":"392","mobileNo":"18801024803","nickName":"18801024803","proofFileNum":"17","spaceAll":"0.0","spaceUsed":"0.0","unreadMsgNum":"0","userCouponNum":"1","verifyCoin":"900"}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * callRecordLeft : 0
         * chargeFlag : 1
         * justFee : 0.0
         * lawSwitch : 0
         * lawTools : http://apps.legaland.cn/CloudOffice/tool/?winzoom=1
         * law_consult_switch : 0
         * leftMoney : 392
         * mobileNo : 18801024803
         * nickName : 18801024803
         * proofFileNum : 17
         * spaceAll : 0.0
         * spaceUsed : 0.0
         * unreadMsgNum : 0
         * userCouponNum : 1
         * verifyCoin : 900
         */
        public String aboutUs;
        public String callRecordLeft;
        public String chargeFlag;
        public String justFee;
        public int lawSwitch;
        public String lawTools;
        public String law_consult_switch;
        public String leftMoney;
        public String mobileNo;
        public String nickName;
        public String proofFileNum;
        public String spaceAll;
        public String spaceUsed;
        public String unreadMsgNum;
        public String userCouponNum;
        public String verifyCoin;
    }
}
