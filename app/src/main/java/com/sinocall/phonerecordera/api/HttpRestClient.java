package com.sinocall.phonerecordera.api;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.event.account.DownloadFinishEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.util.AppInfoUtils;
import com.sinocall.phonerecordera.util.DigestUtils;
import com.sinocall.phonerecordera.util.FileUtils;
import com.sinocall.phonerecordera.util.NetWorkUtil;
import com.sinocall.phonerecordera.util.ToastManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/11/24.
 *
 */

public class HttpRestClient {

        private static final String BASE_URL = "http://192.168.10.242:8080/sinomini/";
//  private static final String BASE_URL = "http://192.168.20.46:8080/sinomini/";
//    private static final String BASE_URL = "http://iluyin.cn/sinomini/";

    public static void post(String url, HttpParams params, BaseOkGoResponse responseHandler) {
        String absoluteUrl = getAbsoluteUrl(url);
        HttpParams requestParams = getParams(params, url);
//        HttpParams requestParams = getTestOkParams(params, url);
        if (!NetWorkUtil.isOpenNet(PhonerecorderaApplication.getInstance())) {
            ToastManager.show(PhonerecorderaApplication.getInstance(), PhonerecorderaApplication.getInstance().getString(R.string.network_off_tip));
        } else if (!NetWorkUtil.isNetworkAvailable(PhonerecorderaApplication.getInstance())) {
            ToastManager.show(PhonerecorderaApplication.getInstance(), PhonerecorderaApplication.getInstance().getString(R.string.network_error_tip));
        } else {
            OkGo.<String>post(absoluteUrl)
                    .params(requestParams)
                    .execute(responseHandler);
        }
    }

    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static HttpParams getParams(HttpParams params, String url) {
        if (params == null) {
            params = new HttpParams();
        }
        if (AccountManager.isLogined()) {
            UserInfo userInfo = AccountManager.getUserInfo();
            String mobileNo = userInfo.mobileNo;
            params.put("mobileNo", mobileNo);
        }
        params.put("_appId", "6666");
        String versionName = AppInfoUtils.getVersionName(PhonerecorderaApplication.getInstance());
        params.put("v", versionName);
        params.put("src", "0");
//        String sig = getSig(params, PhonerecorderaApplication.getInstance().getKey());
        String sig = getSig(params, "androidwqerf*6205");
        params.put("sig", sig.toLowerCase());
        return params;
    }

    public static String getDownlaodUrlParams(String userID, String fileIDs) {
        String urlParams = "";
        HttpParams params = new HttpParams();

        params.put("userID", userID);
        params.put("fileID", fileIDs);

        if (AccountManager.isLogined()) {
            UserInfo userInfo = AccountManager.getUserInfo();
            String mobileNo = userInfo.mobileNo;
            params.put("mobileNo", mobileNo);
            urlParams += "?mobileNo=" + mobileNo;
        }
        params.put("_appId", "6666");
        String versionName = AppInfoUtils.getVersionName(PhonerecorderaApplication.getInstance());
        params.put("v", versionName);
        params.put("src", "0");
        String sig = getSig(params, PhonerecorderaApplication.getInstance().getKey());
        urlParams += "&userID=" + userID;
        urlParams += "&fileID=" + fileIDs;
        urlParams += "&_appId=" + "6666";
        urlParams += "&v=" + versionName;
        urlParams += "&src=" + "0";
        urlParams += "&sig=" + sig.toLowerCase();
        return urlParams;
    }

    private static String getSig(HttpParams params, String secretKey) {
        HashMap<String, String> sortedParams = new HashMap<>();
        for (String key : params.urlParamsMap.keySet()) {
            sortedParams.put(key, params.urlParamsMap.get(key).toString().substring(1, params.urlParamsMap.get(key).toString().length() - 1));
        }
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(sortedParams.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> mapping : list) {
            sb.append(mapping.getKey()).append('=').append(mapping.getValue());
        }
        sb.append(secretKey);
        return DigestUtils.md5Hex(sb.toString());
    }

    public static void downloadFile(String url, String userID, String fileIDs, String fileName, String fileExt, BaseOkGoResponse responseHandler) {

        String absoluteUrl = getAbsoluteUrl(url);
        //HttpParams requestParams = getParams(params, url);
        if (!NetWorkUtil.isOpenNet(PhonerecorderaApplication.getInstance())) {
            ToastManager.show(PhonerecorderaApplication.getInstance(), PhonerecorderaApplication.getInstance().getString(R.string.network_off_tip));

        } else if (!NetWorkUtil.isNetworkAvailable(PhonerecorderaApplication.getInstance())) {
            ToastManager.show(PhonerecorderaApplication.getInstance(), PhonerecorderaApplication.getInstance().getString(R.string.network_error_tip));
        } else {
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            int fileSize = 0;
            int curSize = 0;
            try {
                URL uul = new URL(absoluteUrl + getDownlaodUrlParams(userID, fileIDs));
                httpURLConnection = (HttpURLConnection) uul.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");

                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream = httpURLConnection.getInputStream();
                    File dirFirstFolder = new File(FileUtils.DOWNLOAD_PATH);
                    if (!dirFirstFolder.exists()) { //如果该文件夹不存在，则进行创建
                        dirFirstFolder.mkdirs();//创建文件夹
                    }
                    File dirFirstFolderT = new File(FileUtils.DOWNLOAD_PATH + "tmp/");
                    if (!dirFirstFolderT.exists()) { //如果该文件夹不存在，则进行创建
                        dirFirstFolderT.mkdirs();//创建文件夹
                    }

                    File outFile = new File(FileUtils.DOWNLOAD_PATH + "tmp/" + fileName);
                    outputStream = new FileOutputStream(outFile);

                    byte[] buf = new byte[1024 * 100];
                    int read = 0;
                    while ((read = inputStream.read(buf)) != -1) {
                        outputStream.write(buf, 0, read);
                        curSize += read;

                        DownloadFinishEvent downloadFinishEvent = new DownloadFinishEvent(1, String.valueOf(curSize), null);
                        EventBus.getDefault().post(downloadFinishEvent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    String key = AccountManager.getUserInfo().secretKey + userID + fileIDs + fileExt;
                    FileUtils.decryptFile(key, FileUtils.DOWNLOAD_PATH + "tmp/" + fileName, FileUtils.DOWNLOAD_PATH + fileName);
                    (new File(FileUtils.DOWNLOAD_PATH + "tmp/" + fileName)).delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DownloadFinishEvent downloadFinishEvent = new DownloadFinishEvent(0, null, null);
                EventBus.getDefault().post(downloadFinishEvent);
            }
        }

    }
}
