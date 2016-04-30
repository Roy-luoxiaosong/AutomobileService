package com.roy.automobileservice.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.UserNameAdapter;
import com.roy.automobileservice.cls.UserNameItem;
import com.roy.automobileservice.layout.AvatarImageView;



public class LoginActivity extends Activity implements android.view.View.OnClickListener,
PopupWindow.OnDismissListener,AdapterView.OnItemClickListener{
	
	public static boolean isLogin=false;
	public static UserNameItem curUserNameItem ;
	
	public static void startAction(Context context){
		Intent intent = new Intent(context,LoginActivity.class);
		context.startActivity(intent);
		
	}
	private AvatarImageView curUserImage;
	private int curUserImageId = R.drawable.user_default_icon;
	private Button backButton;
	private Button registerButton;
    ListView listView;
    List<UserNameItem> list;
    UserNameAdapter userNameAdapter;
    public static PopupWindow popupWindow;
    public static EditText username_inputbox;
    public static EditText password_inputbox;
    ImageButton popupBtn;
    Button login_btn;
    LinearLayout linearLayout;
    View popupwindow_view;
    ImageButton showUser_btn;
    int locationX ,locationY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示程序的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity_layout);

        init();
    }
    void init(){
        linearLayout = (LinearLayout) findViewById(R.id.userName_layout);
        password_inputbox = (EditText) findViewById(R.id.password_inputbox);
        username_inputbox = (EditText) findViewById(R.id.username_inputbox);
        login_btn = (Button) findViewById(R.id.login_btn);
        showUser_btn = (ImageButton) findViewById(R.id.showUser_btn);
        backButton = (Button)findViewById(R.id.login_title_back_button);
        registerButton = (Button)findViewById(R.id.login_register_button);
        popupBtn = (ImageButton) findViewById(R.id.showUser_btn);
        curUserImage = (AvatarImageView)findViewById(R.id.login_avatar_img);



        popupwindow_view = getLayoutInflater().inflate(R.layout.username_listview, null);
        listView = (ListView) popupwindow_view.findViewById(R.id.user_listview);
        list = new ArrayList<UserNameItem>();


        /**
         * 待完善
         * 调试用
         * 放点数据进去
         * 以后要封装成函数
         * */
        UserNameItem userNameItem = new UserNameItem();
        userNameItem.setUserName("33333333");
        userNameItem.setAvatarImage(R.drawable.my_picture);
        list.add(userNameItem);
        userNameItem = new UserNameItem();
        userNameItem.setAvatarImage(R.drawable.my_picture);
        userNameItem.setUserName("43333333");
        list.add(userNameItem);
        userNameItem = new UserNameItem();
        userNameItem.setUserName("53333333");
        userNameItem.setAvatarImage(R.drawable.my_picture);
        list.add(userNameItem);
        userNameItem = new UserNameItem();
        userNameItem.setUserName("3333333");
        userNameItem.setAvatarImage(R.drawable.my_picture);
        list.add(userNameItem);

        userNameAdapter = new UserNameAdapter(this,R.layout.username_item,list);
        listView.setAdapter(userNameAdapter);
        listView.setOnItemClickListener(this);
        popupBtn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        backButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        userNameAdapter.notifyDataSetChanged();
        initPopupWindow();
    }

    void initPopupWindow(){
        popupWindow = new PopupWindow(popupwindow_view);
        //注意要加这句代码，点击弹出窗口其它区域才会让窗口消失
        popupWindow.setOutsideTouchable(true);
        //获取焦点，响应按键
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());// 响应返回键必须的语句。
        //添加 Dismiss 事件监听
        popupWindow.setOnDismissListener(this);
        //忽略键盘
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int [] location = new int[2];
        password_inputbox.getLocationOnScreen(location);
        locationX = location[0];
        locationY = location[1];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showUser_btn:
                if(popupWindow.isShowing() == false)
                {
                    showUser_btn.setBackgroundResource(R.drawable.up_arrow);
                    popupWindow.showAtLocation(linearLayout, Gravity.CENTER | Gravity.TOP, 0, locationY);
                    //一定要update
                    popupWindow.update(0, locationY, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                    //隐藏键盘
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }
                break;
            case R.id.login_btn:
            	String userName = username_inputbox.getText().toString();
            	for(UserNameItem u:UserNameAdapter.list){
            		if(u.getUserName()!=null&&u.getUserName().equals(userName)){
            			curUserImageId = u.getAvatarImage();
            			break;
            		}
            	}
                HomePageActivity.startAction(LoginActivity.this);
            	isLogin = true;
            	curUserNameItem = new UserNameItem(userName,curUserImageId);
                break;
            case R.id.login_register_button:
            	//something here
            	break;
            case R.id.login_title_back_button:
            	finish();
            	break;
        }
    }

    @Override
    public void onDismiss() {
        showUser_btn.setBackgroundResource(R.drawable.down_arrow);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String username = userNameAdapter.getItem(position).getUserName();
        int userImage = userNameAdapter.getItem(position).getAvatarImage();
        username_inputbox.setText(username);
        curUserImage.setImageResource(userImage);
        //移动光标指行尾
        username_inputbox.setSelection(username.length());
        
        //隐藏下拉列表
        popupWindow.dismiss();
    }
    }