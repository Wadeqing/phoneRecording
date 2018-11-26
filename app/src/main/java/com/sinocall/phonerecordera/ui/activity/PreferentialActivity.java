package com.sinocall.phonerecordera.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.api.account.BannerResponse;
import com.sinocall.phonerecordera.api.account.PreferentialResponse;
import com.sinocall.phonerecordera.event.account.BannerEvent;
import com.sinocall.phonerecordera.event.account.PreferentialEvent;
import com.sinocall.phonerecordera.event.account.RequireGiftEvent;
import com.sinocall.phonerecordera.event.account.UserShareLogEvent;
import com.sinocall.phonerecordera.manager.AccountManager;
import com.sinocall.phonerecordera.model.bean.UserInfo;
import com.sinocall.phonerecordera.ui.adapter.BannerUltraPagerAdapter;
import com.sinocall.phonerecordera.util.DialogUtils;
import com.sinocall.phonerecordera.util.StatusColorUtils;
import com.sinocall.phonerecordera.util.ToastManager;
import com.tmall.ultraviewpager.UltraViewPager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by qingchao on 2017/11/25.
 *
 * @author l
 */
@RuntimePermissions
public class PreferentialActivity extends BaseActivity {
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
    @BindView(R.id.linearlayout_mine_coupon)
    LinearLayout linearlayoutMineCoupon;
    @BindView(R.id.lineaylayout_share_friend)
    LinearLayout lineaylayoutShareFriend;
    @BindView(R.id.linearlayout_goto_opinion)
    LinearLayout linearlayoutGotoOpinion;
    @BindView(R.id.textview_mayuse_coupon)
    TextView textviewMayuseCoupon;
    @BindView(R.id.textview_share_friend)
    TextView textviewShareFriend;
    @BindView(R.id.textview_share_friend_desc)
    TextView textviewShareFriendDesc;
    @BindView(R.id.textview_goto_opinion)
    TextView textviewGotoOpinion;
    @BindView(R.id.textview_goto_opinion_desc)
    TextView textviewGotoOpinionDesc;
    @BindView(R.id.banner_ultraviewpager)
    UltraViewPager bannerUltraviewpager;
    private List<BannerResponse.DataBean> bannerList = new ArrayList<>();
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusColorUtils.setStatusColor(getWindow());
        setContentView(R.layout.activity_preferetial);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        textviewTitle.setText(getResources().getString(R.string.mine_preferential_activity));
        imageviewTitleLeft.setVisibility(View.VISIBLE);
        linearlayoutViewTitleBack.setVisibility(View.VISIBLE);
        initData();
        initUltraViewPager();

    }

    private void initUltraViewPager() {
        if (bannerList != null && bannerList.size() > 0) {
            bannerUltraviewpager.setVisibility(View.VISIBLE);
        } else {
            bannerUltraviewpager.setVisibility(View.GONE);
        }
        ViewPager viewPager = bannerUltraviewpager.getViewPager();
        bannerUltraviewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        PagerAdapter ultraPagerAdapter = new BannerUltraPagerAdapter(this, bannerList);
        viewPager.setAdapter(ultraPagerAdapter);
        bannerUltraviewpager.initIndicator();
        bannerUltraviewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        bannerUltraviewpager.getIndicator().build();
        bannerUltraviewpager.setInfiniteLoop(true);
        bannerUltraviewpager.setAutoScroll(2000);

    }

    private void initData() {
        if (AccountManager.isLogined()) {
            userInfo = AccountManager.getUserInfo();
            AccountManager.getInstance().getPreferential(userInfo.userID);
            AccountManager.getInstance().getBanner();
        }
    }

    @OnClick({R.id.imageview_title_left, R.id.linearlayout_view_title_back, R.id.linearlayout_mine_coupon, R.id.lineaylayout_share_friend, R.id.linearlayout_goto_opinion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_title_left:
                finish();
                break;
            case R.id.linearlayout_view_title_back:
                finish();
                break;
            case R.id.linearlayout_mine_coupon:
                startActivity(new Intent().setClass(this, MineCouponActivity.class));
                break;
            //分享给好友
            case R.id.lineaylayout_share_friend:
                PreferentialActivityPermissionsDispatcher.ShareUmengWithCheck(this);
                break;
            case R.id.linearlayout_goto_opinion:
                launchAppDetail(getPackageName(), "");
                break;
            default:
                break;
        }
    }

    public void onEventMainThread(PreferentialEvent event) {
        if (event.code == 0) {
            PreferentialResponse preferentialResponse = event.preferentialResponse;
            if (preferentialResponse != null) {
                PreferentialResponse.DataBean data = preferentialResponse.data;
                textviewMayuseCoupon.setText(data.couponNum + "张可用");
                List<PreferentialResponse.DataBean.ActivitiesBean> activities = data.activities;
                Collections.sort(activities, new Comparator<PreferentialResponse.DataBean.ActivitiesBean>() {
                    @Override
                    public int compare(PreferentialResponse.DataBean.ActivitiesBean o1, PreferentialResponse.DataBean.ActivitiesBean o2) {
                        return o1.activityId - o2.activityId;
                    }
                });
            }
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    public void onEventMainThread(BannerEvent event) {
        if (event.code == 0) {
            bannerList = event.response.data;
            initUltraViewPager();
        } else {
            if (!"".equals(event.message)) {
                ToastManager.show(this, event.message);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 到应用市场评论
     *
     * @param appPkg
     * @param marketPkg
     */
    public void launchAppDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) {
                return;
            }
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            AccountManager.getInstance().requireGift(userInfo.userID, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void ShareUmeng() {
        UMImage thumb = new UMImage(this, R.drawable.login_logo);
        thumb.compressFormat = Bitmap.CompressFormat.PNG;
        thumb.compressStyle = UMImage.CompressStyle.SCALE;
        UMWeb umWeb = new UMWeb("http://www.iluyin.cn/");
        umWeb.setTitle("录音取证-最具法律效力的取证软件");//标题
        umWeb.setThumb(thumb);  //缩略图
        umWeb.setDescription("高保真通话录音，云端永久保存，纠纷取证必备。");//描述
        new ShareAction(this)
                .withMedia(umWeb)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener)
                .open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PreferentialActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
                    AccountManager.getInstance().userShareLog(4, userInfo.userID);
                    break;
                case WEIXIN:
                    AccountManager.getInstance().userShareLog(1, userInfo.userID);
                    break;
                case WEIXIN_CIRCLE:
                    AccountManager.getInstance().userShareLog(2, userInfo.userID);
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            switch (platform) {
                case QQ:
                    ToastManager.show(PreferentialActivity.this, "QQ" + " 分享失败啦");
                    break;
                case WEIXIN:
                    ToastManager.show(PreferentialActivity.this, "微信" + " 分享失败啦");
                    break;
                case WEIXIN_CIRCLE:
                    ToastManager.show(PreferentialActivity.this, "朋友圈" + " 分享失败啦");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            switch (platform) {
                case QQ:
                    ToastManager.show(PreferentialActivity.this, "QQ" + " 分享取消了");
                    break;
                case WEIXIN:
                    ToastManager.show(PreferentialActivity.this, "微信" + " 分享取消了");
                    break;
                case WEIXIN_CIRCLE:
                    ToastManager.show(PreferentialActivity.this, "朋友圈" + " 分享取消了");
                    break;
                default:
                    break;
            }
        }
    };

    public void onEventMainThread(UserShareLogEvent event) {
        if (!"".equals(event.message)) {
            DialogUtils.showShareSuccessDialog(this, this, event.message);
        }
        if (event.code == 0) {
            initData();
        }
    }

    public void onEventMainThread(RequireGiftEvent event) {
        if (event.code == 0) {

        } else {
            if (!"".equals(event.message)) {
//                ToastManager.show(this, event.message);
            }
        }
    }
}
