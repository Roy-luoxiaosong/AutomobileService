package com.roy.automobileservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;


import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarOrder;

import com.roy.automobileservice.utils.BaseViewHoler;
import com.roy.automobileservice.utils.CommonAdapter;
import com.roy.automobileservice.utils.OrderHP;
import com.roy.automobileservice.utils.Utils;

import java.util.List;

/**
 * Created by Roy on 2016/6/11.
 */
public class CarOrderAdapter extends CommonAdapter<CarOrder> {
    public CarOrderAdapter(Context context, int resourceId, List<CarOrder> datas) {
        super(context, resourceId, datas);
    }

    @Override
    public void convert(BaseViewHoler holer,final CarOrder data) {
        holer.setText(R.id.order_user_name,data.getUserName())
                .setText(R.id.order_car_name,data.getCarName())
                .setText(R.id.order_state_text,data.getState())
                .setText(R.id.order_car_type,data.getCarDetialName())
                .setText(R.id.order_handle_staff_name_text,data.getStaffName())
                .setText(R.id.order_complete_time_text, getOrderCompleteTime(data))
                .setText(R.id.order_submit_time_text,data.getCreatedAt().toString());

        final Button button = holer.getView(R.id.order_btn);
        Utils.setButtonText(mContext,button,data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!button.getText().toString().equals(OrderHP.SUBMIT_BUTTON_TEXT))
                    return;
                Utils.showDialog(mContext,data,button,CarOrderAdapter.this);
            }
        });
    }
    private String getOrderCompleteTime(CarOrder data){
        return  data.getState().equals(OrderHP.ORDER_COMPLETE_STATE)?data.getUpdatedAt().toString():"";
    }

}
