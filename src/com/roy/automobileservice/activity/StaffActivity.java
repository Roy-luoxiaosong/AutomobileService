package com.roy.automobileservice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.adapter.StaffCarBOrderAdapter;
import com.roy.automobileservice.adapter.StaffCarMOrderAdapter;
import com.roy.automobileservice.adapter.StaffCarOrderAdapter;
import com.roy.automobileservice.cls.CarBOrder;
import com.roy.automobileservice.cls.CarMOrder;
import com.roy.automobileservice.cls.CarOrder;
import com.roy.automobileservice.utils.GlobalVariable;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by Roy on 2016/6/10.
 */
public class StaffActivity extends Activity {

    public static void startAction(Context context) {
        Intent intent = new Intent(context, StaffActivity.class);
        context.startActivity(intent);

    }

    private ListView mOrderListView;
    private List<CarOrder> mCarOrder;
    private List<CarBOrder> mCarBOrder;
    private List<CarMOrder> mCarMOrder;

    private String bql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.staff_activity_view);
        //设置标题
        TextView textView = (TextView) findViewById(R.id.title_back_text);
        textView.setText("订单任务");
        initView();
        inttData();
    }

    private void inttData() {
        switch (GlobalVariable.currentStaff.getDepart()) {
            case GlobalVariable.CAR_BEAUTY_DEPART:
                bql = "select * from CarBOrder where staffName = " + "'" + GlobalVariable.currentStaff.getName() + "'";
                new BmobQuery<CarBOrder>().doSQLQuery(StaffActivity.this, bql, new SQLQueryListener<CarBOrder>() {
                    @Override
                    public void done(BmobQueryResult<CarBOrder> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            mCarBOrder = bmobQueryResult.getResults();
                            if (mCarBOrder != null && mCarBOrder.size() > 0) {
                                StaffCarBOrderAdapter adapter = new StaffCarBOrderAdapter(StaffActivity.this, R.layout.car_beauty_order_item, mCarBOrder);
                                mOrderListView.setAdapter(adapter);
                            }

                        } else {
                            Toast.makeText(StaffActivity.this, "获取任务失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case GlobalVariable.CAR_SALE_DEPART:
                bql = "select * from CarOrder where staffName = " + "'" + GlobalVariable.currentStaff.getName() + "'";
                new BmobQuery<CarOrder>().doSQLQuery(StaffActivity.this, bql, new SQLQueryListener<CarOrder>() {
                    @Override
                    public void done(BmobQueryResult<CarOrder> bmobQueryResult, BmobException e) {
                        if(e==null){
                            mCarOrder = bmobQueryResult.getResults();
                            if(mCarOrder!=null&&mCarOrder.size()>0){
                                StaffCarOrderAdapter adapter = new StaffCarOrderAdapter(StaffActivity.this,R.layout.car_order_item,mCarOrder);
                                mOrderListView.setAdapter(adapter);
                            }
                        }else {
                            Toast.makeText(StaffActivity.this, "获取任务失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case GlobalVariable.CAR_MAINTENANCE_DEPART:
                bql = "select * from CarMOrder where staffName = " + "'" + GlobalVariable.currentStaff.getName() + "'";
                new BmobQuery<CarMOrder>().doSQLQuery(StaffActivity.this, bql, new SQLQueryListener<CarMOrder>() {
                    @Override
                    public void done(BmobQueryResult<CarMOrder> bmobQueryResult, BmobException e) {
                        if(e==null){
                            mCarMOrder = bmobQueryResult.getResults();
                            if(mCarMOrder!=null&&mCarMOrder.size()>0){
                                StaffCarMOrderAdapter adapter = new StaffCarMOrderAdapter(StaffActivity.this,R.layout.car_maintenance_order_item,mCarMOrder);
                                mOrderListView.setAdapter(adapter);
                            }
                        }
                    }
                });
                break;
            case GlobalVariable.CAR_PART_DEPART:
                break;
        }
    }

    private void initView() {
        mOrderListView = (ListView) findViewById(R.id.staff_order_list);
    }
}
