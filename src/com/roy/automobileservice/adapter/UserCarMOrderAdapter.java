package com.roy.automobileservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarMOrder;
import com.roy.automobileservice.utils.BaseViewHoler;
import com.roy.automobileservice.utils.CommonAdapter;
import com.roy.automobileservice.utils.OrderHP;

import java.util.List;

/**
 * Created by Roy on 2016/6/11.
 */
public class UserCarMOrderAdapter extends CommonAdapter<CarMOrder> {
    public UserCarMOrderAdapter(Context context, int resourceId, List<CarMOrder> datas) {
        super(context, resourceId, datas);
    }

    @Override
    public void convert(BaseViewHoler holer, CarMOrder data) {
        holer.setText(R.id.order_maintenance_user_name, data.getUserName())
                .setText(R.id.order_maintenance_complete_time, getOrderCompleteTime(data))
                .setText(R.id.order_maintenance_handle_staff_name_text, data.getStaffName())
                .setText(R.id.order_maintenance_state_text, data.getState())
                .setText(R.id.order_maintenance_milege, data.getMileage())
                .setText(R.id.order_maintenance_submit_time, data.getCreatedAt().toString());
        final Button button = holer.getView(R.id.order_maintenance_btn);
        button.setVisibility(View.GONE);

    }

    private String getOrderCompleteTime(CarMOrder data) {
        return data.getState().equals(OrderHP.ORDER_COMPLETE_STATE) ? data.getUpdatedAt().toString() : "";
    }
}
