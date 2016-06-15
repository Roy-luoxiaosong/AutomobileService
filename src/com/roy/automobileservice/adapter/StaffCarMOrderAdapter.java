package com.roy.automobileservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.CarMOrder;
import com.roy.automobileservice.cls.CarOrder;
import com.roy.automobileservice.cls.MaintenanceHistory;
import com.roy.automobileservice.utils.BaseViewHoler;
import com.roy.automobileservice.utils.CommonAdapter;
import com.roy.automobileservice.utils.OrderHP;
import com.roy.automobileservice.utils.Utils;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Roy on 2016/6/11.
 */
public class StaffCarMOrderAdapter extends CommonAdapter<CarMOrder> {
    public StaffCarMOrderAdapter(Context context, int resourceId, List<CarMOrder> datas) {
        super(context, resourceId, datas);
    }

    @Override
    public void convert(BaseViewHoler holer, final CarMOrder data) {
        holer.setText(R.id.order_maintenance_user_name, data.getUserName())
                .setText(R.id.order_maintenance_complete_time, getOrderCompleteTime(data))
                .setText(R.id.order_maintenance_handle_staff_name_text, data.getStaffName())
                .setText(R.id.order_maintenance_state_text, data.getState())
                .setText(R.id.order_maintenance_milege, data.getMileage())
                .setText(R.id.order_maintenance_submit_time, data.getCreatedAt().toString());
        final Button button = holer.getView(R.id.order_maintenance_btn);


        Utils.setStaffButtonText(mContext, button, data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().toString().equals(OrderHP.STAFF_COMPLETE_BUTTON_TEXT))
                    return;
                data.setState(OrderHP.ORDER_COMPLETE_STATE);
                data.update(mContext, data.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        MaintenanceHistory hitstory = new MaintenanceHistory(data.getUserName(), data.getStaffName());
                        hitstory.save(mContext, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                button.setText("已经处理");
                                button.setTextColor(mContext.getResources().getColor(R.color.handle_button_color));
                                StaffCarMOrderAdapter.this.notifyDataSetChanged();
                                Toast.makeText(mContext, "处理成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(mContext, "处理失败,可能是网络原因", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private String getOrderCompleteTime(CarMOrder data) {
        return data.getState().equals(OrderHP.ORDER_COMPLETE_STATE) ? data.getUpdatedAt().toString() : "";
    }

}
