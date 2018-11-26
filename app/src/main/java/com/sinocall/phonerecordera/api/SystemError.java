package com.sinocall.phonerecordera.api;

import java.io.Serializable;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemError implements Serializable {

    private int code;
    private String codeInfo;
    private Object data;

    public SystemError(int code, String codeInfo, Object result) {
        this.code = code;
        this.codeInfo = codeInfo;
        this.data = result;
    }

    public SystemError() {
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return data;
    }

    public void setResult(String result) {
        this.data = result;
    }

    public String getCodeInfo() {
        return codeInfo;
    }

    public void setCodeInfo(String codeInfo) {
        this.codeInfo = codeInfo;
    }

    @Override
    public String toString() {
        return "SystemError{" +
                "code=" + code +
                ", result='" + data + '\'' +
                '}';
    }
}
