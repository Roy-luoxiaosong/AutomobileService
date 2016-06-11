package com.roy.automobileservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarBOrder;
import com.roy.automobileservice.utils.BaseViewHoler;
import com.roy.automobileservice.utils.CommonAdapter;
import com.roy.automobileservice.utils.OrderHP;
import com.roy.automobileservice.utils.Utils;

import java.util.List;

/**
 * Created by Roy on 2016/6/11.
 */
public class UserCarBOrderAdapter extends CommonAdapter<CarBOrder> {
    public UserCarBOrderAdapter(Context context, int resourceId, List<CarBOrder> datas) {
        super(context, resourceId, datas);
    }

    @Override
    public void convert(BaseViewHoler holer, final CarBOrder data) {
        holer.setText(R.id.order_beauty_user_name,data.getUserName())
                .setText(R.id.order_beauty_content,data.getBeautyList().toString())
                .setText(R.id.order_beauty_state_text,data.getState())
                .setText(R.id.order_beauty_complete_time,getOrderCompleteTime(data))
                .setText(R.id.order_beauty_handle_staff_name_text,data.getStaffName());
        final Button button = holer.getView(R.id.order_beauty_btn);
        button.setVisibility(View.GONE);
        final TextView beautyContent = holer.getView(R.id.order_beauty_content);
        beautyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogBeautyContent(mContext,data);
            }
        });
    }
    private String getOrderCompleteTime(CarBOrder data){
        return  data.getState().equals(OrderHP.ORDER_COMPLETE_STATE)?data.getUpdatedAt().toString():"";
    }
}
