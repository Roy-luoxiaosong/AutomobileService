package com.roy.automobileservice.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.UserNameAdapter;
import com.roy.automobileservice.cls.User;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.TestData;
import com.roy.automobileservice.utils.Utils;



public class LoginActivity extends BaseActivity implements android.view.View.OnClickListener,
PopupWindow.OnDismissListener,AdapterView.OnItemClickListener{
	
	public static boolean isLogin=false;
	
	public static void startAction(Context context){
		Intent intent = new Intent(context,LoginActivity.class);
		context.startActivity(intent);
		
	}
	private List<User> userList= new ArrayList<User>();
	private AvatarImageView curUserImage;
	private Button backButton;
	private Button registerButton;
    private ListView listView;
    private UserNameAdapter userNameAdapter;
    private ArrayAdapter autoCompleteAdapter;
    private List<String> names;
    public static PopupWindow popupWindow;
    public static AutoCompleteTextView username_inputbox;
    public static EditText password_inputbox;
    ImageButton showUser_btn;
    Button login_btn;
    LinearLayout linearLayout;
    View popupwindow_view;
    int locationX ,locationY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //去掉导航栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity_layout);

        init();
    }
    void init(){
        linearLayout = (LinearLayout) findViewById(R.id.userName_layout);
        password_inputbox = (EditText) findViewById(R.id.password_inputbox);
        username_inputbox = (AutoCompleteTextView) findViewById(R.id.username_inputbox);
        login_btn = (Button) findViewById(R.id.login_btn);
        showUser_btn = (ImageButton) findViewById(R.id.showUser_btn);
        backButton = (Button)findViewById(R.id.login_title_back_button);
        registerButton = (Button)findViewById(R.id.login_register_button);
        curUserImage = (AvatarImageView)findViewById(R.id.login_avatar_img);
        userList = Utils.getBackRestraintLenthOfUserList(TestData.userTestList,6);

        //自动适配名字
        names =  getNames();
        autoCompleteAdapter = new ArrayAdapter<>(LoginActivity.this,android.R.layout.simple_dropdown_item_1line,
                names);
        username_inputbox.setAdapter(autoCompleteAdapter);
        username_inputbox.setThreshold(1);



        popupwindow_view = getLayoutInflater().inflate(R.layout.username_listview, null);
        listView = (ListView) popupwindow_view.findViewById(R.id.user_listview);

        userNameAdapter = new UserNameAdapter(this,R.layout.username_item,userList);
        listView.setAdapter(userNameAdapter);
        listView.setOnItemClickListener(this);
        showUser_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        backButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        userNameAdapter.notifyDataSetChanged();
        initPopupWindow();
    }

    @SuppressWarnings("deprecation")
	void initPopupWindow(){
        popupWindow = new PopupWindow(popupwindow_view);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//
        popupWindow.setOnDismissListener(this);
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
                    popupWindow.update(0, locationY, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                }
                break;
            case R.id.login_btn:
            	String userName = username_inputbox.getText().toString();
            	String password = password_inputbox.getText().toString();
            	if(TextUtils.isEmpty(password)){
            		Toast.makeText(this,getResources().getString(R.string.tip_password_is_empty), Toast.LENGTH_SHORT).show();
            		break;
            	}
            	if(Utils.isContainOfName(userName)){
            		for(User u:TestData.userTestList){
                		if(u.getUserName().equals(userName)){
                			if(u.getPassword().equals(password)){
                				GlobalVariable.currentUser = u;
                				isLogin = true;
                				finish();
                				break;
                			}else{
                				Toast.makeText(this, getResources().getString(R.string.tip_password_is_not_right), Toast.LENGTH_SHORT).show();
                				break;
                			}
                		}
                	}
            		break;
            	}else{
            		Utils.showLoRegister(this, getResources().getString(R.string.tip_the_user_is_not_exist));
            	}
            	
                break;
            case R.id.login_register_button:
            	RegisterActivity.startAction(LoginActivity.this);
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
        username_inputbox.setSelection(username.length());
        popupWindow.dismiss();
    	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		resetAapter();
	}
	private void resetAapter(){
		userList = Utils.getBackRestraintLenthOfUserList(TestData.userTestList,6);
		userNameAdapter = new UserNameAdapter(this,R.layout.username_item,userList);
		listView.setAdapter(userNameAdapter);
        names = getNames();
        autoCompleteAdapter.notifyDataSetChanged();
        username_inputbox.setAdapter(autoCompleteAdapter);
	}
    private List<String> getNames(){
        List<String> names = new ArrayList<>();
        Iterator<User> it =TestData.userTestList.iterator();
        while (it.hasNext()){
            names.add(it.next().getUserName());
        }
        return names;
    }
    
    }