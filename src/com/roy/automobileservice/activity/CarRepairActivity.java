package com.roy.automobileservice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarMOrder;
import com.roy.automobileservice.cls.CarMaintenance;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Roy on 2016/5/9.
 */
public class CarRepairActivity extends BaseActivity implements View.OnClickListener{
    public static void startAction(Context context){
        Intent intent = new Intent(context,CarRepairActivity.class);
        context.startActivity(intent);
    }
    private Spinner mileage;
    private ListView baseContent;
    private ListView recommendContent;
    private Button maintenanceApply;
    private List<CarMaintenance> carMaintenances = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayAdapter<String> baseAdapter;
    private ArrayAdapter<String> recommendAdapter;
    private List<String> selectedBaseContent = new ArrayList<>();
    private List<String> selectedRecommendContent = new ArrayList<>();
    private String selectedMileage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.car_repair_activity_view);

        TextView textView = (TextView)findViewById(R.id.title_back_text);
        textView.setText(R.string.auto_repair_option_text);

        initView();
        initDatas();

    }
    private void initView() {
        mileage = (Spinner)findViewById(R.id.select_mileage);
        baseContent = (ListView)findViewById(R.id.base_content);
        recommendContent = (ListView)findViewById(R.id.recommend_content);
        maintenanceApply = (Button)findViewById(R.id.maintenance_request);
        maintenanceApply.setOnClickListener(this);

    }
    private void initDatas(){
        String bql = "select * from CarMaintenance";
        new BmobQuery<CarMaintenance>().doSQLQuery(CarRepairActivity.this, bql, new SQLQueryListener<CarMaintenance>() {
            @Override
            public void done(BmobQueryResult<CarMaintenance> bmobQueryResult, BmobException e) {
                if(e==null){
                    carMaintenances = bmobQueryResult.getResults();

                    spinnerAdapter = new ArrayAdapter<>(CarRepairActivity.this,
                            android.R.layout.simple_spinner_dropdown_item) ;
                    Iterator<CarMaintenance> it = carMaintenances.iterator();
                    while (it.hasNext()){
                        spinnerAdapter.add(it.next().getMileage());
                    }

                    mileage.setAdapter(spinnerAdapter);
                    spinnerAdapter.notifyDataSetChanged();
                    mileage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedMileage = (String) mileage.getSelectedItem();
                           // Log.d("tag","xuanzhong de wei :" +selectedMileage);
                            Iterator<CarMaintenance> it = carMaintenances.iterator();
                            while (it.hasNext()){
                                CarMaintenance tem = it.next();
                                if(selectedMileage.equals(tem.getMileage())){
                                    selectedBaseContent = tem.getBaseContents();
                                    selectedRecommendContent = tem.getRecommendContent();
                                    baseAdapter = (new ArrayAdapter<>(CarRepairActivity.this,
                                            R.layout.string_center_list_item,selectedBaseContent));
                                    baseContent.setAdapter(baseAdapter);
                                    baseAdapter.notifyDataSetChanged();
                                    recommendAdapter = new ArrayAdapter<>(CarRepairActivity.this,
                                            R.layout.string_center_list_item,selectedRecommendContent);
                                    recommendContent.setAdapter(recommendAdapter);
                                    recommendAdapter.notifyDataSetChanged();
                                   // Log.d("tag","reach here:"+tem.getMileage());
                                }

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.maintenance_request:
                if(!LoginActivity.isLogin){
                    Utils.showTipAndLogin(CarRepairActivity.this, R.string.tip_msg_to_login_text);
                }else if(GlobalVariable.loginType!=GlobalVariable.USER_LOGIN){
                    Toast.makeText(CarRepairActivity.this,"请以用户身份登录",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(GlobalVariable.currentUser.getCarName())){
                    Utils.showTipAndJumpToCarModel(CarRepairActivity.this,"您还没有拥有汽车，去看看有没有喜欢的汽车吧？");
                }else{
                    CarMOrder order = new CarMOrder(GlobalVariable.currentUser.getUserName(),selectedMileage);
                    order.save(CarRepairActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(CarRepairActivity.this,"您已经预约成功，我们的管理员会电话联系您取车时间",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(CarRepairActivity.this,"预约失败了，可能网络不好，晚点再试试吧",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }


}
