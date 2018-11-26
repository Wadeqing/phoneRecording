package com.sinocall.phonerecordera.api;

import android.os.Looper;

import com.google.gson.Gson;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.sinocall.phonerecordera.util.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 */

public abstract class BaseOkGoResponse<Response> implements Callback<String> {
    @Override
    public void onStart(Request request) {

    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response response) {
        final String responseString = response.body().toString();
        Runnable parser = new Runnable() {
            @Override
            public void run() {
                LogUtil.e("---", responseString);
                if (!"".equals(responseString)) {
                    try {
                        final SystemError sysErr = parseSystemError(responseString);
                        int code = sysErr.getCode();
                        if (code == -100) {
                            onUpload(sysErr.getCodeInfo(),sysErr.getResult());
                        } else {
                            final Response respons = parseResponse(new Gson().toJson(sysErr));
                            onSuccess(respons, responseString);
                        }
                    } catch (Throwable throwable) {
                        onSystemError(new SystemError(-2, "", null), responseString);
                    }
                }
            }
        };
        if (!isMainThread()) {
            new Thread(parser).start();
        } else {
            parser.run();
        }
    }

    private boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    @Override
    public void onCacheSuccess(com.lzy.okgo.model.Response response) {

    }

    @Override
    public void onError(com.lzy.okgo.model.Response response) {
        onFailure(response.code());
    }


    @Override
    public void onFinish() {

    }

    @Override
    public void uploadProgress(Progress progress) {

    }

    @Override
    public void downloadProgress(Progress progress) {

    }

    private StringConvert convert;

    @Override
    public String convertResponse(okhttp3.Response response) throws Throwable {
        convert = new StringConvert();
        String s = convert.convertResponse(response);
        response.close();
        return s;
    }

    public abstract void onSuccess(Response response, String rawJsonResponse);

    public abstract void onSystemError(SystemError sysErrResp, String rawJsonResponse);

    public abstract void onUpload(String codeInfo, Object result);

    /**
     * 请求失败
     */
    public abstract void onFailure(int statusCode);


    private Class<Response> getGenericType() {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (params.length < 1) {
            return null;
        }
        if (!(params[0] instanceof Class)) {
            return null;
        }
        return (Class<Response>) params[0];
    }

    protected SystemError parseSystemError(String rawJsonData) throws Throwable {
        if (rawJsonData != null) {
            Gson gson = new Gson();
            SystemError sysErr = gson.fromJson(rawJsonData, SystemError.class);
            if (sysErr == null) {
                throw new RuntimeException("Json parse error");
            }
            return sysErr;
        } else {
            throw new RuntimeException("Json string is null");
        }
    }

    private Response parseResponse(String rawJsonData) throws Throwable {
        if (rawJsonData != null) {
            Class<Response> clz = getGenericType();
            if (clz != null) {
                Response resp = null;
                resp = new Gson().fromJson(rawJsonData, clz);
                if (resp == null) {
                    throw new RuntimeException("Json parse error");
                }
                return resp;
            } else {
                throw new RuntimeException("generic type is valid");
            }
        } else {
            throw new RuntimeException("Json string is null");
        }
    }
}
