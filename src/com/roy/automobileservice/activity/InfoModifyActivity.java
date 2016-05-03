package com.roy.automobileservice.activity;

import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.UserImageSelectAdapter;
import com.roy.automobileservice.cls.Car;
import com.roy.automobileservice.cls.HeadSculpture;
import com.roy.automobileservice.cls.User;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.TestData;
import com.roy.automobileservice.utils.Utils;

public class InfoModifyActivity extends BaseActivity implements OnClickListener,OnItemSelectedListener{
	
	public static void startAction(Context context){
		Intent intent = new Intent(context,InfoModifyActivity.class);
		context.startActivity(intent);
		
	}
	private User user = new User();
	private Button submmitButton;
	
	//private Spinner carSpinner;
	private Spinner imageSpinner;
	private EditText userName;
	private EditText realName;
	private EditText emaial;
	private EditText tel;
	private EditText address;
	private EditText password,checkPassword;
	//private RadioButton male,female;
	
	private ArrayAdapter<String> carNameAdapter;
	private UserImageSelectAdapter imageSelectAdapter;
	private AvatarImageView imageIcon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_modify_activity);
        init();
      //设置标题
      	TextView textView = (TextView)findViewById(R.id.title_back_text);
    	textView.setText(R.string.register_button);
    	
	}
	public void init(){
		submmitButton = (Button)findViewById(R.id.submit_button);
		
		//carSpinner = (Spinner)findViewById(R.id.info_modify_car_name);
		imageSpinner = (Spinner)findViewById(R.id.info_modify_select_image_name);
		imageIcon = (AvatarImageView)findViewById(R.id.info_modify_avatar_img);
		userName = (EditText)findViewById(R.id.info_modify_user_name);
		realName = (EditText)findViewById(R.id.info_modify_user_real_name);
		emaial = (EditText)findViewById(R.id.info_modify_email);
		emaial.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		tel = (EditText)findViewById(R.id.info_modify_tel);
		address = (EditText)findViewById(R.id.info_modify_user_address);
		password = (EditText)findViewById(R.id.info_modify_password);
		checkPassword = (EditText)findViewById(R.id.info_modify_check_password);
//		male = (RadioButton)findViewById(R.id.register_male);
//		female = (RadioButton)findViewById(R.id.register_female);
		
		imageSelectAdapter = new UserImageSelectAdapter(InfoModifyActivity.this,TestData.headSculpturesList);
		imageSpinner.setAdapter(imageSelectAdapter);
		carNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
		carNameAdapter.addAll(" ");
		for(Car car:CarModelsListActivity.carList){
			carNameAdapter.add(car.getName());
		}
		//carSpinner.setAdapter(carNameAdapter);
		imageSpinner.setOnItemSelectedListener(this);
		//carSpinner.setOnItemSelectedListener(this);
		submmitButton.setOnClickListener(this);
		showOrignValues();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.submit_button:
			user.setUserName(userName.getText().toString());
			user.setRealName(realName.getText().toString());
			user.setEmail(emaial.getText().toString());
			user.setTelNumber(tel.getText().toString());
			user.setAddress(address.getText().toString());
			user.setPassword(password.getText().toString().equals(checkPassword.getText().toString())?
	    			password.getText().toString():null);
//			user.setSex(male.isChecked()?
//					male.getText().toString():female.getText().toString());
			if(userInfoIsRight(user)){
				/*for(User tem:TestData.userTestList){
					if(tem.getUserName().equals(GlobalVariable.currentUser.getUserName())){
						
					}
				}*/
				Iterator<User> iterator = TestData.userTestList.iterator();
				while (iterator.hasNext()) {
					if(iterator.next().equals(GlobalVariable.currentUser)){
						iterator.remove();
					}
				}
				TestData.userTestList.add(user);
				GlobalVariable.currentUser = user;
				//Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
				Utils.showBackToLogin(InfoModifyActivity.this, "修改成功，是否返回用户信息界面?");
				break;
			}
			//Log.d("luoxiaosong", "nihao"+user.getTelNumber());
			break;
		default:
			break;
		
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		//user.setCar(getCarByName((String)carSpinner.getSelectedItem()));
		HeadSculpture headSculpture = (HeadSculpture)imageSpinner.getSelectedItem();
		user.setAvatarImage(headSculpture.getImageId());
		imageIcon.setImageResource(headSculpture.getImageId());	//设置选中头像	
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
	/**
	 * 判断输入是否正确
	 * @param user
	 * @return
	 */
	private Boolean userInfoIsRight(User user){
		if(TextUtils.isEmpty(user.getUserName())){
			Toast.makeText(InfoModifyActivity.this, "用户名不能为空,请输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(TextUtils.isEmpty(user.getPassword())){
			Toast.makeText(InfoModifyActivity.this, "密码不能为空，或者两次输入的密码不相同，请检查输入", Toast.LENGTH_SHORT).show();
			return false;
		}
		for(User tem:TestData.userTestList){
			if(user.getUserName().equals(tem.getUserName())){
				Toast.makeText(InfoModifyActivity.this, "用户名已经被注册，请从新输入用户名", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		return true;
		
	}
	/**
	 * 显示原始数据
	 */
	private void showOrignValues(){
		//carSpinner.sett;
		imageSpinner.setSelection(getImage(), true);
		userName.setText(GlobalVariable.currentUser.getUserName());
		realName.setText(GlobalVariable.currentUser.getRealName());
		emaial.setText(GlobalVariable.currentUser.getEmail());
		tel.setText(GlobalVariable.currentUser.getTelNumber());
		address.setText(GlobalVariable.currentUser.getAddress());
		imageIcon.setImageResource(GlobalVariable.currentUser.getAvatarImage());
	}
	/**
	 * 获取当前头像的位置
	 * @return
	 */
	private int getImage(){
		int postion = 0;
		int[] imIds = new int[]{R.drawable.user_default_icon1,
				R.drawable.user_default_icon2,
				R.drawable.user_default_icon3,
				R.drawable.user_default_icon4,
				R.drawable.user_default_icon5,
				R.drawable.user_default_icon6};
		for(int i=0;i<imIds.length;i++){
			if(GlobalVariable.currentUser.getAvatarImage()==imIds[i]){
				postion = i;
			}
		}
		return postion;
	}

}
