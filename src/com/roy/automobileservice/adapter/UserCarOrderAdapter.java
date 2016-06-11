package com.roy.automobileservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;


import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarOrder;
import com.roy.automobileservice.utils.BaseViewHoler;
import com.roy.automobileservice.utils.CommonAdapter;
import com.roy.automobileservice.utils.OrderHP;


import java.util.List;



/**
 * Created by Roy on 2016/6/11.
 */
public class UserCarOrderAdapter extends CommonAdapter<CarOrder> {
    public UserCarOrderAdapter(Context context, int resourceId, List<CarOrder> datas) {
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
        button.setVisibility(View.GONE);

       // Utils.setStaffButtonText(mContext,button,data);

    }
    private String getOrderCompleteTime(CarOrder data){
        return  data.getState().equals(OrderHP.ORDER_COMPLETE_STATE)?data.getUpdatedAt().toString():"";
    }
}
