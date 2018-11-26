package com.sinocall.phonerecordera.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by qingchao on 2017/12/13.
 *
 * @author l
 */
@RuntimePermissions
public class CertificateDetailActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.imageview_activity_certificate)
    ImageView imageview;
    @BindView(R.id.textview_save_native)
    TextView textviewSaveNative;
    @BindView(R.id.textview_save_share)
    TextView textviewSaveShare;
    private String picUrl;
    private int finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_certificate_detail);
        ButterKnife.bind(this);
        textviewTitle.setText("申请存证");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        picUrl = intent.getStringExtra("verifyPic");
        finish = intent.getIntExtra("finish", 0);
        Glide.with(this).load(picUrl)
//                .placeholder(R.drawable.)
                .asBitmap().into(imageview);
    }


    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back,
            R.id.textview_save_native, R.id.textview_save_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.textview_save_native:
                new Thread() {
                    @Override
                    public void run() {
                        CertificateDetailActivityPermissionsDispatcher.saveNativeImageWithCheck(CertificateDetailActivity.this);
                    }
                }.start();
                break;
            case R.id.textview_save_share:
                CertificateDetailActivityPermissionsDispatcher.ShareUmengWithCheck(this);
                break;
            default:
                break;
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void ShareUmeng() {
//        UMImage image = new UMImage(this, picUrl);//网络图片
        UMWeb image = new UMWeb(picUrl);
        image.setTitle("录音取证");
        image.setDescription("录音取证的存证证书");
        UMImage thumb = new UMImage(this, R.drawable.login_logo);
        image.setThumb(thumb);
        thumb.compressFormat = Bitmap.CompressFormat.PNG;
//        thumb.compressStyle = UMImage.CompressStyle.SCALE;
        new ShareAction(this)
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener)
                .open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void saveNativeImage() {
        try {
            URL url = new URL(picUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());

            // 首先保存图片
            String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "phoneRecorder";
            File appDir = new File(storePath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            if (isSuccess) {
                ToastManager.show(this, "保存成功");
            } else {
                ToastManager.show(this, "保存失败");
            }
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void showWhyCall(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("保存图片需要权限")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void show(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("分享到其他途径需要权限")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CertificateDetailActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            switch (platform) {
                case QQ:
                    ToastManager.show(CertificateDetailActivity.this, "QQ" + " 分享成功啦");
                    break;
                case WEIXIN:
                    ToastManager.show(CertificateDetailActivity.this, "微信" + " 分享成功啦");
                    break;
                case WEIXIN_CIRCLE:
                    ToastManager.show(CertificateDetailActivity.this, "朋友圈" + " 分享成功啦");
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            switch (platform) {
                case QQ:
                    ToastManager.show(CertificateDetailActivity.this, "QQ" + " 分享失败啦");
                    break;
                case WEIXIN:
                    ToastManager.show(CertificateDetailActivity.this, "微信" + " 分享失败啦");
                    break;
                case WEIXIN_CIRCLE:
                    ToastManager.show(CertificateDetailActivity.this, "朋友圈" + " 分享失败啦");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            switch (platform) {
                case QQ:
                    ToastManager.show(CertificateDetailActivity.this, "QQ" + " 分享取消了");
                    break;
                case WEIXIN:
                    ToastManager.show(CertificateDetailActivity.this, "微信" + " 分享取消了");
                    break;
                case WEIXIN_CIRCLE:
                    ToastManager.show(CertificateDetailActivity.this, "朋友圈" + " 分享取消了");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void finish() {
        if (finish == 0) {
            Intent intent = new Intent();
            intent.putExtra("fragment", 1);
            startActivity(intent.setClass(this, EvidenceManagerActivity.class));
        }
        super.finish();
    }
}
