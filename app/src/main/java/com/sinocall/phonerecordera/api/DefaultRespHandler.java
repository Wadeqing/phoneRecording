package com.sinocall.phonerecordera.api;


import com.sinocall.phonerecordera.event.BaseApiEvent;
import com.sinocall.phonerecordera.event.account.UpLoadAppEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/9/11.
 * OkGo的封装
 */

public class DefaultRespHandler<Response, Event> extends BaseOkGoResponse<Response> {

    @Override
    public void onSuccess(Response response, String rawJsonResponse) {

    }

    @Override
    public void onSystemError(SystemError sysErrResp, String rawJsonResponse) {

    }

    @Override
    public void onUpload(String codeInfo, Object result) {
        UpLoadAppEvent upLoadAppEvent = new UpLoadAppEvent(-100, codeInfo, "");
        EventBus.getDefault().post(upLoadAppEvent);
    }

    @Override
    public void onFailure(int statusCode) {
        BaseApiEvent event;
        if (statusCode == 500) {
            event = newApiEvent(-1, ApiErrorMessage.getServiceErrMsg());
        } else {
            event = newApiEvent(-1, ApiErrorMessage.getNetworkErrMsg());
        }
        if (event != null) {
            EventBus.getDefault().post(event);
        }
    }

    private BaseApiEvent newApiEvent(int code, String msg) {
        Class clz = getEventGenericType();
        if (clz != null) {
            try {
                Constructor constructor = clz.getConstructor(int.class, String.class);
                return (BaseApiEvent) constructor.newInstance(code, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Class<Event> getEventGenericType() {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (params.length < 2) {
            return null;
        }
        if (!(params[1] instanceof Class)) {
            return null;
        }
        return (Class<Event>) params[1];
    }

}
