package com.roy.automobileservice.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarOrder;
import com.roy.automobileservice.cls.ChinaCar;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.TestData;
import com.roy.automobileservice.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


/**
 * Created by Roy on 2016/5/10.
 */
public class CarCalculatorActivity extends BaseActivity implements View.OnClickListener,OnItemSelectedListener {
    public static void startAction(Context context){
        Intent intent = new Intent(context,CarCalculatorActivity.class);
        context.startActivity(intent);
    }
    private Spinner selectCar;
    private Spinner selectCarDetial;
    private EditText carPrice;
    private EditText gouZhiShui;
    private EditText shangPaiFeiYong;
    private EditText cheChuanShiYong;
    private EditText jiaoQiangXian;
    private EditText totalCoast;
    private String selectedCarName;
    private String selectedCarNameDetail;

    private Button resetData;
    private Button buyCar;

    private ArrayAdapter<String> carNameAdapter;
    private ArrayAdapter<String> carDetialAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.car_calculator_activity_view);
        initViews();
        initData();
    }
    private void initViews(){
        TextView textView = (TextView)findViewById(R.id.title_back_text);
        textView.setText(R.string.car_calculator);
        selectCar = (Spinner)findViewById(R.id.select_car);
        selectCarDetial = (Spinner)findViewById(R.id.select_car_detial);
        carPrice = (EditText)findViewById(R.id.select_car_value);
        gouZhiShui = (EditText)findViewById(R.id.gou_zhi_sui);
        shangPaiFeiYong = (EditText)findViewById(R.id.shang_pai_fei_yong);
        cheChuanShiYong = (EditText)findViewById(R.id.che_chuan_shi_yong_sui);
        jiaoQiangXian = (EditText)findViewById(R.id.jiao_qiang_sui);
        totalCoast = (EditText)findViewById(R.id.zong_hua_fei);

        resetData = (Button)findViewById(R.id.reset_data);
        buyCar = (Button)findViewById(R.id.buy_car);

        resetData.setOnClickListener(this);
        buyCar.setOnClickListener(this);


    }
    private void initData(){

        carNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        carNameAdapter.addAll("请选择车型");
        for(ChinaCar car: CarModelsListActivity.cars){
            carNameAdapter.add(car.getCarName());
        }
        carDetialAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line);
        carDetialAdapter.addAll("请选择车款");
        carDetialAdapter.add("1.5MT舒适型");
        carDetialAdapter.add("1.5MT精英型");
        carDetialAdapter.add("1.5TMT精英型");
        carDetialAdapter.add("1.5AT舒适型");
        carDetialAdapter.add("1.5AT精英型");
        carDetialAdapter.add("1.5TMT旗舰型");
        carDetialAdapter.add("1.5TAT精英型");
        carDetialAdapter.add("1.5TAT精英型");
        carDetialAdapter.add("1.5MT基本型");
        carDetialAdapter.add("1.5MT智能型");

        selectCar.setAdapter(carNameAdapter);
        selectCarDetial.setAdapter(carDetialAdapter);
        selectCar.setOnItemSelectedListener(this);
        selectCarDetial.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {

        selectedCarName = (String)selectCar.getSelectedItem();
        selectedCarNameDetail = (String)selectCarDetial.getSelectedItem();
        //Log.d("tag"," "+selectedCarName+":"+selectedCarNameDetail);

        if (!TextUtils.isEmpty(selectedCarName)&&
                !TextUtils.isEmpty(selectedCarNameDetail)
                &&!selectedCarName.equals("请选择车型")
                &&!selectedCarNameDetail.equals("请选择车款"))  {
            double total = getGouZhiShui()+getOthersPast()+Double.valueOf(carPrice.getText().toString());
            totalCoast.setText(String.valueOf(total));
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_data:
                resetData();
                break;
            case R.id.buy_car:
                if(!LoginActivity.isLogin){
                    Utils.showTipAndLogin(CarCalculatorActivity.this, R.string.tip_msg_to_login_text);
                }else{
                    if (!TextUtils.isEmpty(selectedCarName)&&
                            !TextUtils.isEmpty(selectedCarNameDetail)
                            &&!selectedCarName.equals("请选择车型")
                            &&!selectedCarNameDetail.equals("请选择车款")){
                        CarOrder carOrder = new CarOrder(selectedCarName,selectedCarNameDetail, GlobalVariable.currentUser.getUserName(),
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        Log.d("tag","用户名为："+carOrder.getUserName()+
                                "车名："+carOrder.getCarName()+
                                "车型："+carOrder.getCarDetialName()+
                                "得到时间为："+carOrder.getDate());
                        Utils.showCarorderInfo(CarCalculatorActivity.this,"您的订单信息为：\n车型： "+carOrder.getCarName()
                        +"\n车款： "+carOrder.getCarDetialName()+"\n提交请按确认！");
                    }else {
                        Toast.makeText(CarCalculatorActivity.this,"请选择车型",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    private double getGouZhiShui(){
        double temp = 0.0;
        if(!TextUtils.isEmpty(selectedCarName)&&
                !TextUtils.isEmpty(selectedCarNameDetail)
                &&!selectedCarName.equals("请选择车型")
                &&!selectedCarNameDetail.equals("请选择车款")){
            shangPaiFeiYong.setText("500");
            cheChuanShiYong.setText("480");
            jiaoQiangXian.setText("1100");
            switch (selectedCarNameDetail){
                case "1.5MT舒适型":
                    carPrice.setText("68700");
                    temp = (68700/1.17)*0.1;
                    break;
                case "1.5MT精英型":
                    carPrice.setText("72700");
                    temp = (72700/1.17)*0.1;
                    break;
                case "1.5TMT精英型":
                    carPrice.setText("84700");
                    temp = (84700/1.17)*0.1;
                    break;
                case "1.5AT舒适型":
                    carPrice.setText("87300");
                    temp = (87300/1.17)*0.1;
                    break;
                case "1.5AT精英型":
                    carPrice.setText("79700");
                    temp = (79700/1.17)*0.1;
                    break;
                case "1.5TMT旗舰型":
                    carPrice.setText("91700");
                    temp = (91700/1.17)*0.1;
                    break;
                case "1.5MT基本型":
                    carPrice.setText("65700");
                    temp = (65700/1.17)*0.1;
                    break;
                case "1.5TAT精英型":
                    carPrice.setText("77700");
                    temp = (77700/1.17)*0.1;
                    break;
                case "1.5MT智能型":
                    carPrice.setText("88700");
                    temp = (88700/1.17)*0.1;
                    break;
                default:
                    carPrice.setText("99000");
                    temp = (99000/1.17)*0.1;
                    break;
            }
            gouZhiShui.setText(String.valueOf((int)temp));
        }

        return  (int)temp;
    }
    //根据车名获取车信息
    private CarTemp getCarNameByCarName(){
        CarTemp carTemp = new CarTemp();
        Iterator<CarTemp> iterator = HomePageActivity.carTemps.iterator();
        while (iterator.hasNext()){
            if(iterator.next().getCarName().equals(selectedCarName)){
                carTemp = iterator.next();
                break;
            }
        }
        return  carTemp;
    }
    //得到其他收费
    private double getOthersPast(){
        double temp ;
        temp = 500+480+1100;
        return temp;
    }
    private void resetData(){
        selectCar.setSelection(0);
        selectCarDetial.setSelection(0);
        carPrice.setText(" ");
        shangPaiFeiYong.setText(" ");
        cheChuanShiYong.setText(" ");
        jiaoQiangXian.setText(" ");
        totalCoast.setText(" ");
        gouZhiShui.setText(" ");
    }
}
