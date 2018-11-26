package com.sinocall.phonerecordera.api;

import java.io.Serializable;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse implements Serializable {

    private int code;
    private String codeInfo;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int status) {
        this.code = status;
    }

    public String getCodeInfo() {
        return codeInfo;
    }

    public void setCodeInfo(String codeInfo) {
        this.codeInfo = codeInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + code +
                ", codeInfo='" + codeInfo + '\'' +
                ", data='" + data.toString() + '\'' +
                '}';
    }
}
