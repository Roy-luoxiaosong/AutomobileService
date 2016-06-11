package com.roy.automobileservice.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarBOrder;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobObject;


/**
 * Created by Roy on 2016/5/11.
 */
public class AskForBeautyActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{
    public static void startAction(Context context){
        Intent intent = new Intent(context,AskForBeautyActivity.class);
        //intent.putExtra("beauty_names",list);
        context.startActivity(intent);
    }

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private CheckBox checkBox9;
    private CheckBox checkBox10;
    private CheckBox checkBox11;
    private CheckBox checkBox12;
    private CheckBox checkBox13;

    private List<String> checkedList = new ArrayList<>();
    private Button submmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ask_for_beauty_activity_view);
        TextView textView = (TextView)findViewById(R.id.title_back_text);
        textView.setText(getResources().getString(R.string.beauty_request));
        initViews();
    }
    private void initViews(){
        checkBox1 = (CheckBox)findViewById(R.id.checkbox_1);
        checkBox2 = (CheckBox)findViewById(R.id.checkbox_2);
        checkBox3 = (CheckBox)findViewById(R.id.checkbox_3);
        checkBox4 = (CheckBox)findViewById(R.id.checkbox_4);
        checkBox5 = (CheckBox)findViewById(R.id.checkbox_5);
        checkBox6 = (CheckBox)findViewById(R.id.checkbox_6);
        checkBox7 = (CheckBox)findViewById(R.id.checkbox_7);
        checkBox8 = (CheckBox)findViewById(R.id.checkbox_8);
        checkBox9 = (CheckBox)findViewById(R.id.checkbox_9);
        checkBox10 = (CheckBox)findViewById(R.id.checkbox_10);
        checkBox11 = (CheckBox)findViewById(R.id.checkbox_11);
        checkBox12 = (CheckBox)findViewById(R.id.checkbox_12);
        checkBox13 = (CheckBox)findViewById(R.id.checkbox_13);
        submmit = (Button)findViewById(R.id.submit_beauty_request);
        submmit.setOnClickListener(this);

        checkBox1.setOnCheckedChangeListener(this);
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3.setOnCheckedChangeListener(this);
        checkBox4.setOnCheckedChangeListener(this);
        checkBox5.setOnCheckedChangeListener(this);
        checkBox6.setOnCheckedChangeListener(this);
        checkBox7.setOnCheckedChangeListener(this);
        checkBox8.setOnCheckedChangeListener(this);
        checkBox9.setOnCheckedChangeListener(this);
        checkBox10.setOnCheckedChangeListener(this);
        checkBox11.setOnCheckedChangeListener(this);
        checkBox12.setOnCheckedChangeListener(this);
        checkBox13.setOnCheckedChangeListener(this);


    }
    private void initDatas(){

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkbox_1:
                if(isChecked){
                    checkedList.add(checkBox1.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox1.getText().toString());
                    break;
                }

            case R.id.checkbox_2:
                if(isChecked){
                    checkedList.add(checkBox2.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox2.getText().toString());
                    break;
                }

            case R.id.checkbox_3:
                if(isChecked){
                    checkedList.add(checkBox3.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox3.getText().toString());
                    break;
                }
            case R.id.checkbox_4:
                if(isChecked){
                    checkedList.add(checkBox4.getText().toString());
                    break;

                }else{
                    checkedList.remove(checkBox4.getText().toString());
                    break;
                }

            case R.id.checkbox_5:
                if(isChecked){
                    checkedList.add(checkBox5.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox5.getText().toString());
                    break;
                }

            case R.id.checkbox_6:
                if(isChecked){
                    checkedList.add(checkBox6.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox6.getText().toString());
                    break;
                }
            case R.id.checkbox_7:
                if(isChecked){
                    checkedList.add(checkBox7.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox7.getText().toString());
                    break;
                }
            case R.id.checkbox_8:
                if(isChecked){
                    checkedList.add(checkBox8.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox8.getText().toString());
                    break;
                }
            case R.id.checkbox_9:
                if(isChecked){
                    checkedList.add(checkBox9.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox9.getText().toString());
                    break;
                }
            case R.id.checkbox_10:
                if(isChecked){
                    checkedList.add(checkBox10.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox10.getText().toString());
                    break;
                }
            case R.id.checkbox_11:
                if(isChecked){
                    checkedList.add(checkBox11.getText().toString());
                }else{
                    checkedList.remove(checkBox11.getText().toString());
                }
            case R.id.checkbox_12:
                if(isChecked){
                    checkedList.add(checkBox12.getText().toString());
                    break;
                }else{
                    checkedList.remove(checkBox12.getText().toString());
                    break;
                }
            case R.id.checkbox_13:
                if(isChecked){
                    checkedList.add(checkBox13.getText().toString()+",");
                    break;
                }else{
                    checkedList.remove(checkBox13.getText().toString()+",");
                    break;
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_beauty_request:

                if(!LoginActivity.isLogin){
                    Utils.showTipAndLogin(AskForBeautyActivity.this, R.string.tip_msg_to_login_text);
                }else if(GlobalVariable.loginType!=GlobalVariable.USER_LOGIN){
                    Toast.makeText(AskForBeautyActivity.this,"请以用户身份登录",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(GlobalVariable.currentUser.getCarName())){
                    Utils.showTipAndJumpToCarModel(AskForBeautyActivity.this,"您还没有拥有汽车，去看看有没有喜欢的汽车吧？");
                }else{
                    CarBOrder order = new CarBOrder(checkedList, GlobalVariable.currentUser.getUserName());
                    Utils.showTipAndSubmitBOrder(AskForBeautyActivity.this,"请确认需要美容的项目\n" +
                            "提交成功后您将会在我的订单里面看到订单内容",order);
                }
                break;


        }
    }
}
