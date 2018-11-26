package com.sinocall.phonerecordera;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.lzy.okgo.OkGo;
import com.sinocall.phonerecordera.greendao.gen.DaoMaster;
import com.sinocall.phonerecordera.greendao.gen.DaoSession;
import com.sinocall.phonerecordera.util.Constants;
import com.sinocall.phonerecordera.util.JNITest;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by qingchao on 2017/11/23.
 * @author l
 */

public class PhonerecorderaApplication extends MultiDexApplication {
    private static PhonerecorderaApplication instance;
    //    public static AppComponent appComponent;
    private Set<Activity> allActivities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;
    private DaoSession mDaoSession;
    private SQLiteDatabase db;


    public static synchronized PhonerecorderaApplication getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JPushInterface.init(instance);
        //初始化屏幕宽高
        getScreenSize();
        OkGo.getInstance().init(this);
        //初始化数据库
        setDatabase();
        // 信鸽
        registerXGPush();
        //在子线程中完成其他初始化
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(Constants.WX_APP_ID);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        initUmengSDK();

        OkGo.getInstance().init(this);
        //初始化下载
//        OkDownload.getInstance().setFolder(FileUtils.DOWNLOAD_PATH);
//        OkDownload.getInstance().getThreadPool().setCorePoolSize(3);
//
//        /* 从数据库中恢复数据 */
//        DownloadManager.getInstance().clear();
//        List<Progress> progressList = DownloadManager.getInstance().getAll();
//        OkDownload.restore(progressList);

    }

    public String getKey() {
        return JNITest.getsecretKeyFromC(this);
    }

    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "constact.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        db = devOpenHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    /**
     * 初始化友盟SD卡
     */
    private void initUmengSDK() {
        PlatformConfig.setWeixin("wx1f5aafb0417ee63f", "99ade4c240daa0a0bfa14613105ad37d");
        PlatformConfig.setQQZone("1104692129", "iK2KAQ9JxAmKAywe");
    }

    private void registerXGPush() {
        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(this.getApplicationContext(), BuildConfig.DEBUG);
        //注册
        XGPushManager.registerPush(this.getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {

                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                    }
                });
    }

}
