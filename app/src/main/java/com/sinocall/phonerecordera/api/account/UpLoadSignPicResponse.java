package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/12/13.
 */

public class UpLoadSignPicResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * code : 0
     * codeInfo :
     * data : {"signPic":"/res/signPic/20171213/1149168_201712131518485562.png"}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * signPic : /res/signPic/20171213/1149168_201712131518485562.png
         */

        public String signPic;
    }
}
