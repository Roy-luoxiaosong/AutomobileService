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
	
	private Spinner imageSpinner;
	private EditText userName;
	private EditText realName;
	private EditText email;
	private EditText tel;
	private EditText address;
	private EditText oldPassword,password,checkPassword;
	
	private ArrayAdapter<String> carNameAdapter;
	private UserImageSelectAdapter imageSelectAdapter;
	private AvatarImageView imageIcon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

        //����ʾϵͳ�ı�����
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info_modify_activity);
        init();
      //���ñ���
      	TextView textView = (TextView)findViewById(R.id.title_back_text);
    	textView.setText(R.string.reset_user_info);
    	
	}
	public void init(){
		submmitButton = (Button)findViewById(R.id.submit_button);
		
		imageSpinner = (Spinner)findViewById(R.id.info_modify_select_image_name);
		imageIcon = (AvatarImageView)findViewById(R.id.info_modify_avatar_img);
		userName = (EditText)findViewById(R.id.info_modify_user_name);
		realName = (EditText)findViewById(R.id.info_modify_user_real_name);
		email = (EditText)findViewById(R.id.info_modify_email);
		email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		tel = (EditText)findViewById(R.id.info_modify_tel);
		address = (EditText)findViewById(R.id.info_modify_user_address);
		oldPassword = (EditText)findViewById(R.id.info_modify_old_password);
		password = (EditText)findViewById(R.id.info_modify_password);
		checkPassword = (EditText)findViewById(R.id.info_modify_check_password);
		//设置editText的提示字体大小
		Utils.setEditTextHintSize(oldPassword,getResources().getString(R.string.noti_to_modify_password),10);
		Utils.setEditTextHintSize(password,getResources().getString(R.string.noti_to_modify_password),10);
		Utils.setEditTextHintSize(checkPassword,getResources().getString(R.string.noti_to_modify_password),10);
		
		imageSelectAdapter = new UserImageSelectAdapter(InfoModifyActivity.this,TestData.headSculpturesList);
		imageSpinner.setAdapter(imageSelectAdapter);
		carNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
		carNameAdapter.addAll(" ");
		for(Car car:TestData.carList){
			carNameAdapter.add(car.getName());
		}
		imageSpinner.setOnItemSelectedListener(this);
		submmitButton.setOnClickListener(this);
		showOrignValues();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.submit_button:
			user.setSex(GlobalVariable.currentUser.getSex());
			user.setCar(GlobalVariable.currentUser.getCar());
			user.setUserName(userName.getText().toString());
			user.setRealName(realName.getText().toString());
			user.setEmail(email.getText().toString());
			user.setTelNumber(tel.getText().toString());
			user.setAddress(address.getText().toString());
			user.setPassword(GlobalVariable.currentUser.getPassword());
			if(!TextUtils.isEmpty(password.getText().toString())||
					!TextUtils.isEmpty(checkPassword.getText().toString())){
				if(oldPassword.getText().toString().equals(GlobalVariable.currentUser.getPassword())){
					user.setPassword(password.getText().toString().equals(checkPassword.getText().toString())?
							password.getText().toString():null);
				}else{
					Toast.makeText(this, getResources().getString(R.string.tip_old_password_is_not_right), Toast.LENGTH_SHORT).show();
					break;
				}
			}
			if(userInfoIsRight(user)){
				//删除以前的用户
				Iterator<User> iterator = TestData.userTestList.iterator();
				while (iterator.hasNext()) {
					if(iterator.next().equals(GlobalVariable.currentUser)){
						iterator.remove();
					}
				}
				TestData.userTestList.add(user);
				GlobalVariable.currentUser = user;
				Utils.showBackToMyInfo(InfoModifyActivity.this, getResources().getString(R.string.tip_user_info_modify_succeed));
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
		//user.setCar(getCarByName((String)carSpinner.getSelectedItem()));
		HeadSculpture headSculpture = (HeadSculpture)imageSpinner.getSelectedItem();
		user.setAvatarImage(headSculpture.getImageId());
		imageIcon.setImageResource(headSculpture.getImageId());	//����ѡ��ͷ��	
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 输入用户是否正确
	 * @param user
	 * @return
	 */
	private Boolean userInfoIsRight(User user){
		if(TextUtils.isEmpty(user.getUserName())){
			Toast.makeText(InfoModifyActivity.this,  getResources().getString(R.string.tip_user_name_is_empty), Toast.LENGTH_SHORT).show();
			return false;
		}
		if(TextUtils.isEmpty(user.getPassword())){
			Toast.makeText(InfoModifyActivity.this, getResources().getString(R.string.tip_password_is_empty_or_not_right), Toast.LENGTH_SHORT).show();
			return false;
		}
		//如果没有修改，不判断用户名被注册了
		if(user.getUserName().equals(GlobalVariable.currentUser.getUserName())){
			return true;
		}
		for(User tem:TestData.userTestList){
			if(user.getUserName().equals(tem.getUserName())){
				Toast.makeText(InfoModifyActivity.this, getResources().getString(R.string.tip_user_is_exist), Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		return true;
		
	}
	/**
	 * 显示原有信息
	 */
	private void showOrignValues(){
		//carSpinner.sett;
		imageSpinner.setSelection(getImage(), true);
		userName.setText(GlobalVariable.currentUser.getUserName());
		realName.setText(GlobalVariable.currentUser.getRealName());
		email.setText(GlobalVariable.currentUser.getEmail());
		tel.setText(GlobalVariable.currentUser.getTelNumber());
		address.setText(GlobalVariable.currentUser.getAddress());
		imageIcon.setImageResource(GlobalVariable.currentUser.getAvatarImage());
	}

	/**
	 * 获取头像的位置
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
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		MyInfoActivity.startAction(this);
	}
	
	

}
