package com.sinocall.phonerecordera.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.compat.BuildConfig;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.util.LogUtil;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * 所有webview 用这个activity显示
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.button_save_native)
    Button buttonSaveNative;
    @BindView(R.id.button_save_internet)
    Button buttonSaveInternet;
    @BindView(R.id.linearlayout_bottom_button)
    LinearLayout linearlayoutBottomButton;

    WebView webView;
    @BindView(R.id.fragment_webview)
    FrameLayout fragmentWebview;
    @BindView(R.id.imageview_title_left)
    ImageView imageviewTitleLeft;
    @BindView(R.id.textview_title_left)
    TextView textviewTitleLeft;
    @BindView(R.id.linearlayout_view_title_back)
    LinearLayout linearlayoutViewTitleBack;
    @BindView(R.id.textview_title)
    TextView textviewTitle;
    @BindView(R.id.imageview_title_right)
    ImageView imageviewTitleRight;
    @BindView(R.id.textview_title_right)
    TextView textviewTitleRight;
    @BindView(R.id.linearlayout_view_title_setting)
    LinearLayout linearlayoutViewTitleSetting;
    @BindView(R.id.imageview_small_red)
    ImageView imageviewSmallRed;
    @BindView(R.id.framelayout_view_title)
    FrameLayout framelayoutViewTitle;
    private String url;
    private String title = "";//标题栏内容
    private long iouId;
    private Bitmap bitmap;
    private boolean isShowButton;
    private int operationActivityId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        if (url.contains("operationActivityId=")) {
            operationActivityId = Integer.parseInt(url.substring(url.indexOf("operationActivityId=") + "operationActivityId=".length(),
                    url.indexOf("&", url.indexOf("operationActivityId"))));
        }
        title = intent.getStringExtra("title");
        //是否显示下面两个保存 webview 的 button
        isShowButton = intent.getBooleanExtra("isShowButton", false);
        iouId = intent.getLongExtra("id", 0);
//        Android 5.0 以后对webview进行了 优化  加入这一行 绘制webVIew 全部内容
//        enableSlowWholeDocumentDraw();
        webView = new WebView(this.getApplicationContext());
        fragmentWebview.addView(webView);
        if (!"".equals(title)) {
            framelayoutViewTitle.setVisibility(View.VISIBLE);
            textviewTitle.setText(title);
            textviewTitle.setVisibility(View.VISIBLE);
            linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
            imageviewTitleLeft.setVisibility(View.VISIBLE);
        } else {
            framelayoutViewTitle.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl(url);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        settings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(new H5Click(), "android");
        /*
         * 解决在webview中跳转的问题
         */
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) {
                    return false;
                }
                try {
                    if (url.startsWith("weixin://") //微信
                            || url.startsWith("alipays://") //支付宝
                            || url.startsWith("mailto://") //邮件
                            || url.startsWith("tel://")//电话
                            || url.startsWith("dianping://")//大众点评
                            || url.startsWith("tmast://")
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                try {
                    if (url.contains("recharge")) {
                        Intent intent1 = new Intent(WebViewActivity.this, RechargeActivity.class);
                        intent1.putExtra("operationActivityId", operationActivityId);
                        startActivity(intent1);
                        return true;
                    }
                } catch (Exception e) {
                    return true;
                }
                view.loadUrl(url);   //在当前的webview中跳转到新的url
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                showLoading(null);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dismissLoading();
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    @OnClick({R.id.button_save_native, R.id.button_save_internet,
            R.id.imageview_title_left, R.id.linearlayout_view_title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_save_native:
                //保存文件到本地   保存为图片格式
                String screenshot = screenshot(this, bitmap, iouId + "");
                if (screenshot != null) {
                    ToastManager.show(this, "保存成功，可以进入本地相册查看");
                } else {
                    ToastManager.show(this, "保存失败");
                }
                break;
            case R.id.button_save_internet:
                //保存文件到其他路径
                break;
           /* case R.id.linearlayout_view_title_back:
                finish();
                break;*/
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            default:
                break;
        }
    }

    // 生成图片
    public static Bitmap capture(View view, float width, float height, boolean scroll, Bitmap.Config config) {
        if (!view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, config);
        bitmap.eraseColor(Color.WHITE);
        final Canvas canvas = new Canvas(bitmap);
        int left = view.getLeft();
        int top = view.getTop();
        if (scroll) {
            left = view.getScrollX();
            top = view.getScrollY();
        }
        int status = canvas.save();
        canvas.translate(-left, -top);
        float scale = width / view.getWidth();
        canvas.scale(scale, scale, left, top);
        view.draw(canvas);
        canvas.restoreToCount(status);
        Paint alphaPaint = new Paint();
        alphaPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0f, 0f, 1f, height, alphaPaint);
        canvas.drawRect(width - 1f, 0f, width, height, alphaPaint);
        canvas.drawRect(0f, 0f, width, 1f, alphaPaint);
        canvas.drawRect(0f, height - 1f, width, height, alphaPaint);
        canvas.setBitmap(null);
        return bitmap;
    }

    //保存在本地相册
    public static String screenshot(Context context, Bitmap bitmap, String name) {
        if (bitmap == null) {
            return null;
        }
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (name == null || name.trim().isEmpty()) {
            name = String.valueOf(System.currentTimeMillis());
        }
        name = name.trim();
        int count = 0;
        File file = new File(dir, name + ".png");
        while (file.exists()) {
            count++;
            file = new File(dir, name + "." + count + ".png");
        }
        try {
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        CookieSyncManager.createInstance(this);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        releaseAllWebViewCallback();
        System.gc();
        fragmentWebview.removeAllViews();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void releaseAllWebViewCallback() {
        if (Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class H5Click {
        @JavascriptInterface
        public String recharge() {
            LogUtil.e("--------", "");
            return "hello world";
        }
    }
}
