package com.sinocall.phonerecordera.api;

import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiErrorMessage {

    public static String getSystemErrMsg() {
        //系统错误
        return PhonerecorderaApplication.getInstance().getString(R.string.system_error);
    }

    public static String getServiceErrMsg(){
        return  PhonerecorderaApplication.getInstance().getString(R.string.system_error);
    }

    public static String getNetworkErrMsg() {
        return PhonerecorderaApplication.getInstance().getString(R.string.network_error);
    }
}
