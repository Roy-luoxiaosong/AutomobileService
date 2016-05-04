package com.roy.automobileservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.UserImageSelectAdapter;
import com.roy.automobileservice.cls.HeadSculpture;
import com.roy.automobileservice.cls.User;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.utils.TestData;
import com.roy.automobileservice.utils.Utils;

public class RegisterActivity extends BaseActivity implements OnClickListener,OnItemSelectedListener{
	
	public static void startAction(Context context){
		Intent intent = new Intent(context,RegisterActivity.class);
		context.startActivity(intent);
		
	}
	private User user = new User();
	private Button regiButton;
	
	private Spinner imageSpinner;
	private EditText userName;
	private EditText password,checkPassword;
	private RadioButton male,female;
	
	private UserImageSelectAdapter imageSelectAdapter;
	private AvatarImageView imageIcon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register_activity);
        init();
      //设置标题
      	TextView textView = (TextView)findViewById(R.id.title_back_text);
    	textView.setText(R.string.register_button);
    	
	}
	public void init(){
		regiButton = (Button)findViewById(R.id.register_button);
		
		imageSpinner = (Spinner)findViewById(R.id.register_select_image_name);
		imageIcon = (AvatarImageView)findViewById(R.id.register_avatar_img);
		userName = (EditText)findViewById(R.id.register_user_name);
		password = (EditText)findViewById(R.id.register_password);
		checkPassword = (EditText)findViewById(R.id.register_check_password);
		male = (RadioButton)findViewById(R.id.register_male);
		female = (RadioButton)findViewById(R.id.register_female);
		
		imageSelectAdapter = new UserImageSelectAdapter(RegisterActivity.this,TestData.headSculpturesList);
		imageSpinner.setAdapter(imageSelectAdapter);
		imageSpinner.setOnItemSelectedListener(this);
		regiButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.register_button:
			user.setUserName(userName.getText().toString());
			user.setPassword(password.getText().toString().equals(checkPassword.getText().toString())?
	    			password.getText().toString():null);
			user.setSex(male.isChecked()?
					male.getText().toString():female.getText().toString());
			if(userInfoIsRight(user)){
				TestData.userTestList.add(user);
				Utils.showBackToLogin(RegisterActivity.this, getResources().getString(R.string.tip_register_succeed));
				break;
			}
			break;
		default:
			break;
		
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		HeadSculpture headSculpture = (HeadSculpture)imageSpinner.getSelectedItem();
		user.setAvatarImage(headSculpture.getImageId());
		imageIcon.setImageResource(headSculpture.getImageId());		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*private Car getCarByName(String name){
		Car car = new Car();
		if(!TextUtils.isEmpty(name)){
			for(Car tem:CarModelsListActivity.carList){
				if(tem!=null&&tem.getName().equals(name)){
						car = tem;
						break;
				}
			}
		}
		return car;
	}*/
	private Boolean userInfoIsRight(User user){
		if(TextUtils.isEmpty(user.getUserName())){
			Toast.makeText(RegisterActivity.this, getResources().getString(R.string.tip_user_name_is_empty), Toast.LENGTH_SHORT).show();
			return false;
		}
		if(TextUtils.isEmpty(user.getPassword())){
			Toast.makeText(RegisterActivity.this, getResources().getString(R.string.tip_password_is_empty_or_not_right), Toast.LENGTH_SHORT).show();
			return false;
		}
		/*if(TextUtils.isEmpty(user.getEmail())){
			Toast.makeText(RegisterActivity.this, "邮箱不能为空，请输入邮箱", Toast.LENGTH_SHORT).show();
			return false;
		}*/
		/*if(TextUtils.isEmpty(user.getTelNumber())){
			Toast.makeText(RegisterActivity.this, "手机号码不能为空，请输入手机号码", Toast.LENGTH_SHORT).show();
			return false;
		}*/
		for(User tem:TestData.userTestList){
			if(user.getUserName().equals(tem.getUserName())){
				Toast.makeText(RegisterActivity.this, getResources().getString(R.string.tip_user_is_exist), Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		return true;
		
	}

}
