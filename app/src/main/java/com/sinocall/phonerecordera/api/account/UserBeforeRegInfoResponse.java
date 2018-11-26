package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2018/1/25.
 */

public class UserBeforeRegInfoResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : {"infoImage":"http://192.168.20.46:8080/sinomini/res/info/info.png"}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * infoImage : http://192.168.20.46:8080/sinomini/res/info/info.png
         */

        public String infoImage;
    }
}
