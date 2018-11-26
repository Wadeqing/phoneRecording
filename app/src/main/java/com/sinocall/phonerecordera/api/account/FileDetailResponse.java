package com.sinocall.phonerecordera.api.account;

import com.sinocall.phonerecordera.model.bean.FileDetail;

import java.io.Serializable;

/**
 * Created by qingchao on 2017/11/30.
 */

public class FileDetailResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : {"duration":"58","descTel":"018551733959","fileName":"李芳.wav","expireTime":"2015-07-23 15:36:58","fileSize":"622764","createTime":"2015-06-23 15:36:58","srcTel":"13639607899","fileExt":"wav","srcType":"1"}
     */
    public int code;
    public String codeInfo;
    public FileDetail data;

}
