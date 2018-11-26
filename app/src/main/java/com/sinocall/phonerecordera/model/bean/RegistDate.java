package com.sinocall.phonerecordera.model.bean;

import java.io.Serializable;

/**
 * Created by qingchao on 2018/1/26.
 */

public class RegistDate implements Serializable {
    /**
     * code : 0
     * codeInfo :
     * data : {"flag":"true"}
     */
    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * flag : true
         */
        public String flag;
    }
}
