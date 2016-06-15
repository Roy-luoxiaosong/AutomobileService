package com.roy.automobileservice.activity;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;


import android.text.TextUtils;


import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.broadcast.NetworkChangeReceiver;
import com.roy.automobileservice.cls.ActivityCollector;

import com.roy.automobileservice.cls.Car;
import com.roy.automobileservice.cls.HeadSculpture;

import com.roy.automobileservice.cls.User;

import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.layout.CircleMenuLayout;
import com.roy.automobileservice.layout.CircleMenuLayout.OnMenuItemClickListener;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.TestData;
import com.roy.automobileservice.utils.Utils;


import java.util.List;

import cn.bmob.v3.Bmob;


public class HomePageActivity extends BaseActivity implements android.view.View.OnClickListener, PopupWindow.OnDismissListener {
    private final static int MY_INFO = R.drawable.my_info_option_bg;
    private final static int AUTO_BEAUTY = R.drawable.auto_beauty_option_bg;
    private final static int AUTO_REPAIR = R.drawable.auto_repair_option_bg;
    private final static int AUTO_PART = R.drawable.auto_part_option_bg;
    private final static int AUTO_ROAD = R.drawable.road_assistant_option_bg;
    private final static int CAR_MODLE = R.drawable.car_models_option_bg;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }


    public static List<CarTemp> carTemps;

    private AvatarImageView userImage;
    private CircleMenuLayout mCircleMenuLayout;
    private String[] itemTexts = new String[6];

    private int[] itemImagIds = new int[6];
    private NetworkChangeReceiver networkChangeReceiver;
    private IntentFilter intentFilter;

    //private RelativeLayout noNetNotifictionLayout;
    private Button loginButton;

    //登录相关
    private LinearLayout mNologin;
    private LinearLayout mUserLogin;
    private LinearLayout mManagerLogin;
    private LinearLayout mStaffLogin;
    private PopupWindow mLoginPopupWindow;
    private int popupWindowWeidth = 500;
    private int popupWindowHeight = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_page_view);

        Bmob.initialize(this, "811186c53ef97fe8b6579c684e61b053");

        initMenuItems();
        init();
        popupWindowWeidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, popupWindowWeidth, getResources().getDisplayMetrics());
        popupWindowHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, popupWindowHeight, getResources().getDisplayMetrics());

        registerReceiver(networkChangeReceiver, intentFilter);

    }

    private void init() {
        Utils.getUserInfoFromBmob(HomePageActivity.this);
        //Utils.initChinaCars(HomePageActivity.this);
        Utils.initHeadSculptures(HomePageActivity.this);
        networkChangeReceiver = new NetworkChangeReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        //登录相关
        mNologin = (LinearLayout) getLayoutInflater().inflate(R.layout.no_login_pop_window, null);
        mManagerLogin = (LinearLayout) mNologin.findViewById(R.id.manager_login);
        mUserLogin = (LinearLayout) mNologin.findViewById(R.id.user_login);
        mStaffLogin = (LinearLayout) mNologin.findViewById(R.id.staff_login);
        mManagerLogin.setOnClickListener(this);
        mUserLogin.setOnClickListener(this);
        mStaffLogin.setOnClickListener(this);
        initPopupWindow();
        userImage = (AvatarImageView) findViewById(R.id.login_avatar_img);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoginActivity.isLogin == true) {
                    switch (GlobalVariable.loginType) {
                        case GlobalVariable.MANAGER_LOGIN:
                            ManagerActivity.startAction(HomePageActivity.this);
                            break;
                        case GlobalVariable.USER_LOGIN:
                            InfoModifyActivity.startAction(HomePageActivity.this);
                            break;
                        case GlobalVariable.STAFF_LOGIN:
                            StaffActivity.startAction(HomePageActivity.this);
                            break;
                    }
                }
            }
        });
        //noNetNotifictionLayout = (RelativeLayout) findViewById(R.id.net_view_r1);

        loginButton = (Button) findViewById(R.id.login_title_button);


        loginButton.setOnClickListener(this);
        //noNetNotifictionLayout.setOnClickListener(this);

    }
    private void initPopupWindow() {
        mLoginPopupWindow = new PopupWindow(mNologin);
        if (LoginActivity.isLogin = false) {
            mLoginPopupWindow.setContentView(mNologin);
        } else {

        }
        mLoginPopupWindow.setOutsideTouchable(true);
        mLoginPopupWindow.setFocusable(true);
        mLoginPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mLoginPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mLoginPopupWindow.setOnDismissListener(this);
        mLoginPopupWindow.setHeight(popupWindowHeight);
        mLoginPopupWindow.setWidth(popupWindowWeidth);
        mLoginPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_title_button:

                if (LoginActivity.isLogin == true) {

                    Utils.showTipAndExit(HomePageActivity.this, "你确定要退出吗？", loginButton, userImage);
                } else {
                    mLoginPopupWindow.showAsDropDown(loginButton);
                }
                break;

            case R.id.net_view_r1:
               /* if (noNetNotifictionLayout != null) {
                    Intent intent = new Intent("android.settings.SETTINGS");
                    startActivity(intent);
                }*/
            case R.id.manager_login:
                GlobalVariable.loginType = GlobalVariable.MANAGER_LOGIN;
                LoginActivity.startAction(HomePageActivity.this);
                break;
            case R.id.user_login:
                GlobalVariable.loginType = GlobalVariable.USER_LOGIN;
                LoginActivity.startAction(HomePageActivity.this);
                break;
            case R.id.staff_login:
                GlobalVariable.loginType = GlobalVariable.STAFF_LOGIN;
                LoginActivity.startAction(HomePageActivity.this);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        LoginActivity.isLogin = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginPopupWindow.dismiss();

    }


    @Override
    protected void onResume() {
        super.onResume();
        //noNetNotifictionLayout.setVisibility(NetworkChangeReceiver.netState ? View.GONE : View.VISIBLE);
        refreshTitleContent();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        Utils.deleteStaticVariables();
        //Log.d("tag","销毁后的长度是："+carTemps.size());
    }

    private void refreshTitleContent() {
        if (LoginActivity.isLogin) {
            loginButton.setText(R.string.exit_button);
            if (GlobalVariable.loginType == GlobalVariable.USER_LOGIN) {
                if (!TextUtils.isEmpty(GlobalVariable.currentUser.getUserName()) && GlobalVariable.currentUser.getAvatarImage() > 0) {
                    userImage.setImageResource(Utils.getUserIconByInt(GlobalVariable.currentUser.getAvatarImage()));
                }
            } else if (GlobalVariable.loginType == GlobalVariable.MANAGER_LOGIN) {

            } else if (GlobalVariable.loginType == GlobalVariable.STAFF_LOGIN) {

            }

        } else {
            loginButton.setText(R.string.login_button);
            userImage.setImageResource(R.drawable.user_default_icon1);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void exitApp() {

        //
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, R.string.notifiction_to_exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            ActivityCollector.finishAll();
            LoginActivity.isLogin = false;
        }
    }


    private void initMenuItems() {
        itemTexts = new String[]{getResources().getString(R.string.my_info_option_text),
                getResources().getString(R.string.auto_beauty_option_text),
                getResources().getString(R.string.auto_repair_option_text),
                /*getResources().getString(R.string.auto_part_option_text),*/
                getResources().getString(R.string.road_assis_option_text),
                getResources().getString(R.string.car_models_text)};
        itemImagIds = new int[]{MY_INFO,
                AUTO_BEAUTY,
                AUTO_REPAIR,
               /* AUTO_PART,*/
                AUTO_ROAD,
                CAR_MODLE};
        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(itemImagIds, itemTexts);


        mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public void itemClick(View view, int pos) {

                switch (itemImagIds[pos]) {
                    case MY_INFO:
                        if (!LoginActivity.isLogin) {
                            Utils.showTipAndLogin(HomePageActivity.this, R.string.tip_msg_to_login_text);
                        } else if (GlobalVariable.loginType == GlobalVariable.USER_LOGIN) {
                            MyInfoActivity.startAction(HomePageActivity.this);
                        } else {
                            Toast.makeText(HomePageActivity.this, "非用户登录，不能查看用户资料", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case AUTO_BEAUTY:

                        CarBeautyActivity.startAction(HomePageActivity.this);
                        break;
                    case AUTO_REPAIR:

                        CarRepairActivity.startAction(HomePageActivity.this);
                        break;
                    case AUTO_PART:

                        //MapActivity.startAction(HomePageActivity.this);
                        break;
                    case AUTO_ROAD:
                        ConvenientService.startAction(HomePageActivity.this);
                        break;
                    case CAR_MODLE:
                        CarModelsListActivity.startAction(HomePageActivity.this);
                        break;

                    default:
                        break;
                }

            }
            @Override
            public void itemCenterClick(View view) {

            }
        });

    }
    @Override
    public void onDismiss() {

    }
}
