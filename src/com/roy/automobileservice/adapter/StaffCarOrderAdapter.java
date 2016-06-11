package com.roy.automobileservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarBOrder;
import com.roy.automobileservice.cls.CarOrder;
import com.roy.automobileservice.cls.User;
import com.roy.automobileservice.utils.BaseViewHoler;
import com.roy.automobileservice.utils.CommonAdapter;
import com.roy.automobileservice.utils.OrderHP;
import com.roy.automobileservice.utils.Utils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Roy on 2016/6/11.
 */
public class StaffCarOrderAdapter extends CommonAdapter<CarOrder> {
    public StaffCarOrderAdapter(Context context, int resourceId, List<CarOrder> datas) {
        super(context, resourceId, datas);
    }
    @Override
    public void convert(BaseViewHoler holer, final CarOrder data) {
      holer.setText(R.id.order_user_name,data.getUserName())
              .setText(R.id.order_complete_time_text,getOrderCompleteTime(data))
              .setText(R.id.order_handle_staff_name_text,data.getStaffName())
            .setText(R.id.order_car_type,data.getCarDetialName())
      .setText(R.id.order_car_name,data.getCarName());
        final Button button = holer.getView(R.id.order_btn);


        Utils.setStaffButtonText(mContext,button,data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equals(OrderHP.STAFF_COMPLETE_BUTTON_TEXT))
                    return;


                String bql = "select * from User where userName="+"'"+data.getUserName()+"'";
                new BmobQuery<User>().doSQLQuery(mContext, bql, new SQLQueryListener<User>() {
                    @Override
                    public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                        if(e==null&&bmobQueryResult.getResults().size()>0){
                            String userId = bmobQueryResult.getResults().get(0).getObjectId();
                            User user = new User();
                            user.setCarName(data.getCarName());
                            user.update(mContext, userId, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    data.setState(OrderHP.ORDER_COMPLETE_STATE);
                                    data.update(mContext, data.getObjectId(), new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            button.setText("已经处理");
                                            button.setTextColor(mContext.getResources().getColor(R.color.handle_button_color));
                                            StaffCarOrderAdapter.this.notifyDataSetChanged();
                                            Toast.makeText(mContext,"处理成功",Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            Toast.makeText(mContext,"处理失败,可能是网络原因",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(mContext,"处理失败,可能是网络原因",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });



            }
        });
    }
    private String getOrderCompleteTime(CarOrder data){
        return  data.getState().equals(OrderHP.ORDER_COMPLETE_STATE)?data.getUpdatedAt().toString():"";
    }

}
