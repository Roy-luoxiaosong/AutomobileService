package com.roy.automobileservice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.cls.BeautyHistory;
import com.roy.automobileservice.cls.CarBOrder;
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
public class StaffCarBOrderAdapter extends CommonAdapter<CarBOrder> {
    public StaffCarBOrderAdapter(Context context, int resourceId, List<CarBOrder> datas) {
        super(context, resourceId, datas);
    }
    @Override
    public void convert(BaseViewHoler holer, final CarBOrder data) {
      holer.setText(R.id.order_beauty_user_name,data.getUserName())
              .setText(R.id.order_beauty_content,data.getBeautyList().toString())
              .setText(R.id.order_beauty_state_text,data.getState())
              .setText(R.id.order_beauty_complete_time,getOrderCompleteTime(data))
              .setText(R.id.order_beauty_handle_staff_name_text,data.getStaffName())
                .setText(R.id.order_beauty_submit_time_text,data.getCreatedAt().toString());
        final Button button = holer.getView(R.id.order_beauty_btn);
        final TextView beautyContent = holer.getView(R.id.order_beauty_content);
        beautyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogBeautyContent(mContext,data);
            }
        });
        Utils.setStaffButtonText(mContext,button,data);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equals(OrderHP.STAFF_COMPLETE_BUTTON_TEXT))
                    return;
                data.setState(OrderHP.ORDER_COMPLETE_STATE);
                data.update(mContext, data.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        BeautyHistory beautyHistory = new BeautyHistory();
                        beautyHistory.setUserName(data.getUserName());
                        beautyHistory.setStaffName(data.getStaffName());
                        beautyHistory.save(mContext, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                button.setText("已经处理");
                                button.setTextColor(mContext.getResources().getColor(R.color.handle_button_color));
                                StaffCarBOrderAdapter.this.notifyDataSetChanged();
                                Toast.makeText(mContext,"处理成功",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(mContext,"处理失败,可能是网络原因",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private String getOrderCompleteTime(CarBOrder data){
        return  data.getState().equals(OrderHP.ORDER_COMPLETE_STATE)?data.getUpdatedAt().toString():"";
    }

}
