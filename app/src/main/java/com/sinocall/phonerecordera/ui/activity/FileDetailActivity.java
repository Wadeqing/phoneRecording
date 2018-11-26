package com.sinocall.phonerecordera.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.lzy.okserver.OkDownload;
import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.FileDetailResponse;
import com.sinocall.phonerecordera.dao.FileDownloadBean;
import com.sinocall.phonerecordera.event.account.DelFolderAndFileEvent;
import com.sinocall.phonerecordera.event.account.DownloadFinishEvent;
import com.sinocall.phonerecordera.event.account.FileDetailEvent;
import com.sinocall.phonerecordera.event.account.ProofFileRenameEvent;
import com.sinocall.phonerecordera.event.account.RemainCoinEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.FileDetail;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.util.AppUtil;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.FileSizeUtil;
import com.sinocall.phonerecordera.util.FileUtils;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.TimeUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.sinocall.phonerecordera.widget.DownLoadProgressbar;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by qingchao on 2017/11/30.
 */

public class FileDetailActivity extends BaseActivity {
    @BindView(R.id.imageview_title_left)
    ImageView imageviewTitleLeft;
    @BindView(R.id.textview_title_left)
    TextView textviewTitleLeft;
    @BindView(R.id.linearlayout_view_title_back)
    LinearLayout linearlayoutViewTitleBack;
    @BindView(R.id.textview_title)
    TextView textviewTitle;
    @BindView(R.id.downLoadProgressbar)
    DownLoadProgressbar mDownLoadProgressbar;
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
    @BindView(R.id.textview_file_detail_size)
    TextView textviewFileDetailSize;
    @BindView(R.id.textview_file_detail_save_time)
    TextView textviewFileDetailSaveTime;
    @BindView(R.id.textview_file_detail_type)
    TextView textviewFileDetailType;
    @BindView(R.id.textview_file_detail_dialing_num)
    TextView textviewFileDetailDialingNum;
    @BindView(R.id.textview_file_detail_called_num)
    TextView textviewFileDetailCalledNum;
    @BindView(R.id.textview_file_detail_calling_time)
    TextView textviewFileDetailCallingTime;
    @BindView(R.id.textview_bottom_apply_preservation)
    TextView textviewBottomApplyPreservation;
    @BindView(R.id.textview_bottom_download_file)
    TextView textviewBottomDownloadFile;
    @BindView(R.id.textview_bottom_rename)
    TextView textviewBottomRename;
    @BindView(R.id.textview_bottom_delete)
    TextView textviewBottomDelete;
    @BindView(R.id.textview_file_Detail_name)
    TextView textviewFileDetailName;

    @BindView(R.id.playSeekBar)
    SeekBar seekBar;

    @BindView(R.id.playClose)
    TextView playClose;

    @BindView(R.id.playStop)
    TextView playStop;

    @BindView(R.id.FileDetail_bottom_ll)
    LinearLayout bottom_ll;
    @BindView(R.id.FileDetail_play_ll)
    LinearLayout play_ll;

//    @BindView(R.id.playCurrentTime)
//    TextView playCurrentTime;
//
//    @BindView(R.id.playTotalTime)
//    TextView playTotalTime;


    private FileDetail data;

    private String folderID;
    private int verifyStatus; //1.存证
    private UserInfo userInfo;
    private String fileName;
    private OkDownload instance;

    private boolean iffirst = false;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean ifplay = false;
    private MediaPlayer player = null;
    private boolean isChanging = false;//互斥变量，防止定时器与SeekBar拖动时进度冲突
    private int isDownLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_file_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
        initUI();
        OkDownload okDownload = OkDownload.getInstance();
//        String path = Environment.getExternalStorageDirectory().getPath() + "/download/";
        String path = Environment.getExternalStorageDirectory().getPath();
        Log.e("path", path);
        okDownload.setFolder(path);

        mDownLoadProgressbar.setVisibility(View.INVISIBLE);
        mDownLoadProgressbar.setMaxValue(100);
    }

    private void initData() {
        Intent intent = getIntent();
        folderID = intent.getStringExtra("folderID");
        String userID = intent.getStringExtra("userID");
        isDownLoad = intent.getIntExtra("isDownLoad", 0);
        verifyStatus = intent.getIntExtra("verifyStatus", 0);
        if (!"".equals(folderID) && !"".equals(userID)) {
            userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getFileDetail(folderID, userID);
        }


    }

    private static final int REQUEST_PERMISSION_STORAGE = 0x01;

    private void checkSDCardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
        }
    }


    private void initUI() {
        textviewTitle.setText("文件详情");
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        if (data != null) {
            textviewFileDetailName.setText(data.fileName);
            textviewFileDetailSaveTime.setText(data.createTime);
            textviewFileDetailSize.setText(FileSizeUtil.getPrintSize(Long.parseLong(data.fileSize)));
            if ("1".equals(data.srcType)) {
                textviewFileDetailType.setText("电话录音");
            } else if ("2".equals(data.srcType)) {
                textviewFileDetailType.setText("现场录音");
            } else if ("0".equals(data.srcType)) {
                textviewFileDetailType.setText("文件上传");
            } else if ("3".equals(data.srcType)) {
                textviewFileDetailType.setText("律和律师通话");
            } else if ("4".equals(data.srcType)) {
                textviewFileDetailType.setText("一键呼叫律师");
            }
            textviewFileDetailDialingNum.setText(data.srcTel);
            textviewFileDetailCalledNum.setText(data.descTel);
            textviewFileDetailCallingTime.setText(TimeUtils.secondToTime(data.duration));
        }
        seekBar.setProgress(0);
        bottom_ll.setVisibility(View.VISIBLE);
        play_ll.setVisibility(View.INVISIBLE);

        //检查下载与否
        //if (1 == checkFileExist()) {
        if (1 == checkFileExist()) {
            textviewBottomDownloadFile.setText("播放");
            textviewBottomDownloadFile.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.consulting_icon_play_default), null, null);
        } else {
            textviewBottomDownloadFile.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.consulting_icon_download_default), null, null);
            textviewBottomDownloadFile.setText("下载");
            mDownLoadProgressbar.setVisibility(View.VISIBLE);
        }
        //Uri playUri = Uri.parse(FileUtils.DOWNLOAD_PATH + "123.wav");//获取wav文件路径
        //player =  MediaPlayer.create (this, playUri);//新建一个的实例

        player = new MediaPlayer();
        seekBar.setOnSeekBarChangeListener(new MySeekbar());
        checkSDCardPermission();
        // }
    }

    //进度条处理
    class MySeekbar implements OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isChanging = true;

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.seekTo(seekBar.getProgress());
            isChanging = false;

        }

    }


    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.textview_bottom_apply_preservation,
            R.id.textview_bottom_download_file, R.id.textview_bottom_rename, R.id.textview_bottom_delete, R.id.playStop,
            R.id.playClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.playClose:
                playClose();
                break;
            case R.id.playStop:
                play();
                break;
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.textview_bottom_apply_preservation:
                Intent intent = new Intent(this, MineRecordingEvidenceActivity.class);
                intent.putExtra("fileId", Long.parseLong(folderID));
                startActivity(intent);
                break;
            case R.id.textview_bottom_download_file:

                if ("下载".equals(textviewBottomDownloadFile.getText())) {
                    checkAllowDowanload();
                } else {
                    bottom_ll.setVisibility(View.INVISIBLE);
                    play_ll.setVisibility(View.VISIBLE);
                    playMedia();
                }
                break;
            case R.id.textview_bottom_rename:
                showDialogRename();
                break;
            case R.id.textview_bottom_delete:
                DialogUtils.showDeleteFileDialog(this, this);
                break;
            default:
                break;
        }
    }

    private void playClose() {
        if (player != null) {
            if (player.isPlaying()) {

                player.stop();
            }
        }
        ifplay = false;
        bottom_ll.setVisibility(View.VISIBLE);
        play_ll.setVisibility(View.INVISIBLE);
    }

    private void play() {
        if (player != null && !ifplay ) {
            playStop.setText("暂停");
            if (iffirst) {
                player.reset();
                try {
                    player.setDataSource(FileUtils.DOWNLOAD_PATH + data.fileName);
                    player.prepare();// 准备

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                seekBar.setMax(player.getDuration());//设置进度条
                //----------定时器记录播放进度---------//
                mTimer = new Timer();
                mTimerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (isChanging) {
                            return;
                        }

                        try {
                            seekBar.setProgress(player.getCurrentPosition());
                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }
                };
                mTimer.schedule(mTimerTask, 0, 10);
                iffirst = false;
            }
            player.start();// 开始
            ifplay = true;
        } else if (ifplay ) {
            playStop.setText("播放");
            player.pause();
            ifplay = false;
        }
    }


    private void playMedia() {
        File file = new File(FileUtils.DOWNLOAD_PATH, data.fileName);
        iffirst = true;

        if (file.exists()) {
            play();
        }
    }


    private int checkFileExist() {

        int isExist = 0;
        String fPath = FileUtils.getFilePath(folderID);
        if (fPath != null) {

            isExist = FileUtils.fileIsExist(fPath);
        }
        return isExist;

    }

    private void checkAllowDowanload() {

        AccountManager.getInstance().checkAllowDownloadFile(userInfo.userID, folderID, "0");
    }

    private void downLoadFile() {

        int isExist = checkFileExist();

        if (isExist == 1) {
            ToastManager.show(this, "已经下载过了");
            return;
        }


        //开始下载

        ToastManager.show(this, "开始下载");
        new Thread() {
            @Override
            public void run() {
                AccountManager.getInstance().downloadFile(userInfo.userID, folderID, data.fileName, data.fileExt);
            }
        }.start();  //开启一个线程


    }


    private void showDialogRename() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_add_zone, null);
        dialog.setView(view);
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = AppUtil.getWindowWidth(PhonerecorderaApplication.getInstance()) - 180;
        dialog.getWindow().setAttributes(lp);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //加上下面这一行弹出对话框时软键盘随之弹出
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        final EditText editText = (EditText) view.findViewById(R.id.edittext_dialog_zone);
        TextView textCancle = (TextView) view.findViewById(R.id.textview_dialog_cancle);
        TextView textTitle = (TextView) view.findViewById(R.id.textview_title_dialog);
        TextView textSure = (TextView) view.findViewById(R.id.textview_dialog_sure);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        textTitle.setText("请输入文件名");
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置可获得焦点
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                //请求获得焦点
                editText.requestFocus();
                //调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) editText
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        });
        textCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        textSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = editText.getText().toString();
                if (fileName.length() == 0) {
                    ToastManager.show(FileDetailActivity.this, "文件名不能为空");
                } else {
                    AccountManager.getInstance().proofFileRename(userInfo.userID, folderID, fileName);
                }
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            if (player.isPlaying()) {

                player.stop();
            }
            player.release();
        }
        super.onDestroy();

        EventBus.getDefault().unregister(this);
//        instance.removeAll();
    }

    @Override
    protected void onPause() {
        if (player != null) {
            if (player.isPlaying()) {

                player.pause();
            }
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (player != null) {
            if (!player.isPlaying() && play_ll.getVisibility() == View.VISIBLE) {

                player.start();
            }
        }
        super.onResume();
    }




    public void onEventMainThread(FileDetailEvent event) {
        if (event.code == 0) {
            FileDetailResponse fileDetailResponse = event.fileDetailResponse;
            data = fileDetailResponse.data;
            initUI();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void onEventMainThread(ProofFileRenameEvent event) {
        if (event.code == 0) {
            if ("0".equals(event.response.data.status)) {
                ToastManager.show(this, "命名成功");
                textviewFileDetailName.setText(fileName + "." + data.fileExt);
                //修改文件名
                FileUtils.renameFile(data.fileName, fileName, data.fileExt);
                data.fileName = fileName + "." + data.fileExt;
                //修改数据库中下载记录文件名
                FileUtils.updateDownloadFileDBName(folderID, fileName + "." + data.fileExt);
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void onEventMainThread(DelFolderAndFileEvent event) {
        if (event.code == 0) {
            if ("0".equals(event.response.data.status)) {
                //删除文件 和 下载记录
                FileUtils.deleteFile(FileUtils.DOWNLOAD_PATH + data.fileName);
                FileUtils.deleteDBDownloadRecord(folderID);
                ToastManager.show(this, "删除成功");
                finish();
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void onEventMainThread(RemainCoinEvent event) {
        if (event.code == 0) {
            //有余额，可以下载
            downLoadFile();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void onEventMainThread(DownloadFinishEvent event) {
        if (event.code == 0) {
            ToastManager.show(this, event.message);
            FileDownloadBean entity = new FileDownloadBean();
            entity.setFid(Long.valueOf(folderID));

            entity.setFileName(data.fileName);
            entity.setFileSize(Long.valueOf(data.fileSize));

            entity.setCreateTime(data.createTime);
            entity.setFilePath(FileUtils.DOWNLOAD_PATH + data.fileName);
            try {
                FileUtils.insertDownloadToDB(entity);
            } catch (Exception e) {

            }
            textviewBottomDownloadFile.setText("播放");
            textviewBottomDownloadFile.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.consulting_icon_play_default), null, null);
            ToastManager.show(this, "下载完成");

            FileUtils.insertDownloadToDB(entity);
        } else if (event.code == 1) {
            float pp = Integer.valueOf(event.message);
            pp = pp / Integer.valueOf(data.fileSize) * 100;
            mDownLoadProgressbar.setCurrentValue(pp);
            mDownLoadProgressbar.setMaxValue(100);
            mDownLoadProgressbar.forceLayout();
//            mDownLoadProgressbar.notify();
        }
    }


    public boolean deleteRecordFile(String folderID) {
        String filePath = FileUtils.getFilePath(folderID);
        if (filePath == null) {
            return true;
        } else {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview_dialog_delete_file_sure:
                if (isDownLoad == 0) {
                    AccountManager.getInstance().delFolderAndFile(userInfo.userID, folderID);
                }
                DialogUtils.dismissDialog();
                if (isDownLoad == 1) {
                    FileUtils.deleteFile(FileUtils.DOWNLOAD_PATH + data.fileName);
                    FileUtils.deleteDBDownloadRecord(folderID);
                    ToastManager.show(this, "删除成功");
                    isDownLoad = 2;
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        if (isDownLoad == 2) {
            setResult(20180116);
        }
        super.finish();
    }
}