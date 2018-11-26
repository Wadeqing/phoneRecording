package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2018/2/2.
 */

public class BeginAppResponse implements Serializable {


    /**
     * code : 0
     * codeInfo :
     * data : {"phoneLine":"0","remainCoin":"0.0","pops":"[{\"picUrl\":\"http://192.168.10.242:8080/sinomini//res/popPic/20180202/SINOV201802021018165487.jpg\",\"popType\":2,\"sysScreenPopId\":63,\"url\":\"http://baidu.com\",\"title\":\"dsda\"}]","phone_setting":"1","just_setting":"0"}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * phoneLine : 0
         * remainCoin : 0.0
         * pops : [{"picUrl":"http://192.168.10.242:8080/sinomini//res/popPic/20180202/SINOV201802021018165487.jpg","popType":2,"sysScreenPopId":63,"url":"http://baidu.com","title":"dsda"}]
         * phone_setting : 1
         * just_setting : 0
         */

        public String phoneLine;
        public String remainCoin;
        public String pops;
        public String phone_setting;
        public String just_setting;
    }
}
