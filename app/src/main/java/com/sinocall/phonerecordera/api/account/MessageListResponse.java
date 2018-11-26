package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/1.
 */

public class MessageListResponse implements Serializable{

    /**
     * code : 0
     * codeInfo :
     * data : [{"sysmsgId":"spm2","subTitle":"","createTime":"2016-12-16 15:13:00","IsReadAlready":"Y","type":"0","title":"","msgInfo":"全新【专线通话】功能震撼发布！还在担心对方看到你的电话不接吗？电话录音专线号码，帮您打通一切拦截。超清VIP专属录音通道，重要录音电话一键拨通，让您拥有更好更快的录音体验！","url":""},{"sysmsgId":"spm1","subTitle":"","createTime":"2016-12-16 10:51:12","IsReadAlready":"Y","type":"0","title":"","msgInfo":"年底了讨不回工钱？欠钱的一直在拖？用【电话录音】给对方打电话取证维权！","url":""}]
     */

    public int code;
    public String codeInfo;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * sysmsgId : spm2
         * subTitle :
         * createTime : 2016-12-16 15:13:00
         * IsReadAlready : Y
         * type : 0
         * title :
         * msgInfo : 全新【专线通话】功能震撼发布！还在担心对方看到你的电话不接吗？电话录音专线号码，帮您打通一切拦截。超清VIP专属录音通道，重要录音电话一键拨通，让您拥有更好更快的录音体验！
         * url :
         */

        public String sysmsgId;
        public String subTitle;
        public String createTime;
        public String IsReadAlready;
        public String type;
        public String title;
        public String msgInfo;
        public String url;
    }
}
