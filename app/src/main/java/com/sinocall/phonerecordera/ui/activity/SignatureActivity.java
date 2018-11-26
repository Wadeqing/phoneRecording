package com.sinocall.phonerecordera.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sinocall.phonerecordera.PhonerecordreaConfig;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.event.account.UpLoadSignPicEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.sinocall.phonerecordera.widget.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.greenrobot.event.EventBus;


/**
 * Created by Administrator on 15-6-8.
 * 签字页面
 */
public class SignatureActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonClear, buttonSave;
    private ImageButton buttonCancel;
    private SignatureView signatureView;
    public static Bitmap mSignBitmap;
    public static String signPath;
    public static final int RESULT_CODE = 9090;
    private static final String[] PEEMISSION = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private ImageView imageviewtitleleft;
    public boolean SignatureSure = false;
    private UserInfo userInfo;
    private String signPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signature);
        buttonClear = (Button) findViewById(R.id.button_clear_sig);
        buttonSave = (Button) findViewById(R.id.button_save_sig);
        buttonCancel = (ImageButton) findViewById(R.id.button_cancel_sig);
        signatureView = (SignatureView) findViewById(R.id.signatureview_sig);
        imageviewtitleleft = (ImageView) findViewById(R.id.imageview_title_left);
        buttonClear.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        EventBus.getDefault().register(this);
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
        } else {
            startActivity(new Intent().setClass(this, LoginActivity.class));
        }

    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
//                Bitmap bitmap = Bitmap.createBitmap(40, 80,
//                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        v.draw(canvas);
        return bitmap;
    }


    /**
     * 创建手写签名文件
     *
     * @return
     */
    private String createFile() {
        ByteArrayOutputStream baos = null;
        String _path = null;
        try {
            String sign_dir = Environment.getExternalStorageDirectory() + File.separator;
            _path = createImagePath();
            baos = new ByteArrayOutputStream();
            mSignBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] photoBytes = baos.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(_path)).write(photoBytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _path;
    }


    private String createImagePath() {
        File path = new File(PhonerecordreaConfig.BASE_DIR + "/tmp/");
        if (!path.exists()) {
            path.mkdirs();
        }
        String fileName = "signature." + System.currentTimeMillis() + ".jpg";

        File file = new File(path, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clear_sig:
                signatureView.clear();
                break;
            case R.id.button_save_sig:
                if (signatureView.isEmpty) {
                    DialogUtils.showDialogSign(this);
                } else {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, PEEMISSION, 1);
                        } else {
                            mSignBitmap = createViewBitmap(signatureView);
                            signPath = createFile();
                            AccountManager.getInstance().upLoadSignPic(userInfo.userID, signPath);
                        }
                    } else {
                        mSignBitmap = createViewBitmap(signatureView);
                        signPath = createFile();
                        AccountManager.getInstance().upLoadSignPic(userInfo.userID, signPath);
                    }
                }
                break;
            case R.id.button_cancel_sig:
                finish();
                break;
            default:
                break;
        }
    }

    public void onEventMainThread(UpLoadSignPicEvent event) {
        if (event.code == 0) {
            SignatureSure = true;
            signPic = event.response.data.signPic;
            finish();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            } else {
                ToastManager.show(this, "上传失败，请重新操作");
            }
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("SignatureSure", SignatureSure);
        intent.putExtra("signPic", signPic);
        setResult(SignatureActivity.RESULT_CODE, intent);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.dismissDialog();
        EventBus.getDefault().unregister(this);
    }
}
