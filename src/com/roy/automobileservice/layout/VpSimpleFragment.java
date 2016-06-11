package com.roy.automobileservice.layout;


import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;
import com.roy.automobileservice.activity.ManagerActivity;
import com.roy.automobileservice.adapter.CarBOrderAdapter;
import com.roy.automobileservice.adapter.CarMOrderAdapter;
import com.roy.automobileservice.adapter.CarOrderAdapter;
import com.roy.automobileservice.adapter.StaffInfoAdapter;
import com.roy.automobileservice.adapter.UserNameAdapter;
import com.roy.automobileservice.cls.CarBOrder;
import com.roy.automobileservice.cls.CarMOrder;
import com.roy.automobileservice.cls.CarOrder;
import com.roy.automobileservice.cls.IOnDeleteUserButtonClickListener;
import com.roy.automobileservice.cls.Staff;
import com.roy.automobileservice.cls.User;
import com.roy.automobileservice.utils.GlobalVariable;
import com.roy.automobileservice.utils.SqlHelper;
import com.roy.automobileservice.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Roy on 2016/6/9.
 */
public class VpSimpleFragment extends Fragment implements View.OnClickListener {
    private int mResourceId;

    public static final String LAYOUT_ID = "resourceId";

    //用户信息管理相关
    private ListView mUserInfoList ;
    private List<User> mUsers = new ArrayList<>();
    private UserNameAdapter mUserInfoAdapter;

    //员工管理相关
    private ImageButton mAddStaffBtn;
    private List<Staff> mStaffInfoList;
    private ListView mStaffListView;
    private StaffInfoAdapter mStaffAdapter;
    private LinearLayout mAddStaffView;
    private Button mSubmitBtn;
    private Button mQuitBtn;
    private EditText mStaffName;
    private EditText mStaffEmial;
    private EditText mStaffPassword;
    private Spinner mStaffDepartSpinner;
    private ArrayAdapter mDepartAdapter;
    private String mStaffDepart;

    //订单管理相关
    private ListView mCarOrderListView,mCarBOrderListView,mCarMOrderListView;
    private List<CarOrder> mCarOrderList;
    private List<CarBOrder> mCarBOrderList;
    private List<CarMOrder> mCarMOrderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle!=null){
            mResourceId = bundle.getInt(LAYOUT_ID);
        }
        View view = inflater.inflate(mResourceId,container,false);
        //用户管理的时候
        switch (mResourceId){
            case R.layout.manager_user_view:
                mUserInfoList = (ListView) view.findViewById(R.id.id_manager_user_info);
                new BmobQuery<User>().doSQLQuery(getActivity(), SqlHelper.SEARCH_USER_FROM_BMBO, new SQLQueryListener<User>() {
                    @Override
                    public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                        if(e==null){
                            mUsers = bmobQueryResult.getResults();
                            mUserInfoAdapter = new UserNameAdapter(getActivity(),R.layout.username_item,mUsers);
                            mUserInfoList.setAdapter(mUserInfoAdapter);
                            mUserInfoAdapter.setOnDeleteUserListener(new IOnDeleteUserButtonClickListener() {
                                @Override
                                public void onClick(ImageButton button, int pos, UserNameAdapter adapter) {
                                    Utils.showTipToManagerAndDeleteUer(getActivity(),"你确定要删除这个用户吗？",
                                            pos,mUserInfoAdapter,mUsers);
                                }
                            });
                        }
                    }
                });
                break;
            case R.layout.manager_staff_view:
                initManageStaffViews(view);
                new BmobQuery<Staff>().doSQLQuery(getActivity(), SqlHelper.SEARCH_STAFF_FROM_BMBO, new SQLQueryListener<Staff>() {
                    @Override
                    public void done(BmobQueryResult<Staff> bmobQueryResult, BmobException e) {
                        if(e==null){
                            mStaffInfoList = bmobQueryResult.getResults();
                            if(mStaffInfoList!=null&&mStaffInfoList.size()>0){
                                mStaffAdapter = new StaffInfoAdapter(getActivity(),R.layout.staff_info_item,mStaffInfoList);
                                mStaffListView.setAdapter(mStaffAdapter);
                            }
                        }
                    }
                });
                break;
            case R.layout.manager_order_view:
                initManageOrderViews(view);
                new BmobQuery<CarOrder>().doSQLQuery(getActivity(), SqlHelper.SEARCH_CARORDER_FROM_BMBO, new SQLQueryListener<CarOrder>() {
                    @Override
                    public void done(BmobQueryResult<CarOrder> bmobQueryResult, BmobException e) {
                        if(e==null){
                            mCarOrderList = bmobQueryResult.getResults();
                            if(mCarOrderList!=null&&mCarOrderList.size()>0){
                                CarOrderAdapter adapter = new CarOrderAdapter(getActivity(),R.layout.car_order_item,mCarOrderList);
                                mCarOrderListView.setAdapter(adapter);
                                Utils.setListViewHeightBasedOnChildren(mCarOrderListView);
                            }
                        }else {
                            Toast.makeText(getActivity(),"获取订单失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                new BmobQuery<CarBOrder>().doSQLQuery(getActivity(), SqlHelper.SEARCH_CARBORDER_FROM_BMBO, new SQLQueryListener<CarBOrder>() {
                    @Override
                    public void done(BmobQueryResult<CarBOrder> bmobQueryResult, BmobException e) {
                        if(e==null){
                            mCarBOrderList = bmobQueryResult.getResults();
                            if(mCarBOrderList!=null&&mCarBOrderList.size()>0){
                                CarBOrderAdapter adapter = new CarBOrderAdapter(getActivity(),R.layout.car_beauty_order_item,mCarBOrderList);
                                mCarBOrderListView.setAdapter(adapter);
                                Utils.setListViewHeightBasedOnChildren(mCarBOrderListView);
                            }
                        }else {
                            Toast.makeText(getActivity(),"获取订单失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                new BmobQuery<CarMOrder>().doSQLQuery(getActivity(), SqlHelper.SEARCH_CARMORDER_FROM_BMBO, new SQLQueryListener<CarMOrder>() {
                    @Override
                    public void done(BmobQueryResult<CarMOrder> bmobQueryResult, BmobException e) {
                        if(e==null){
                            mCarMOrderList = bmobQueryResult.getResults();
                            if(mCarMOrderList!=null&&mCarMOrderList.size()>0){
                                CarMOrderAdapter adapter = new CarMOrderAdapter(getActivity(),R.layout.car_maintenance_order_item,mCarMOrderList);
                                mCarMOrderListView.setAdapter(adapter);
                                Utils.setListViewHeightBasedOnChildren(mCarMOrderListView);
                            }
                        }
                    }
                });
                break;
        }
        return view;
    }

    public static VpSimpleFragment newInstance(int resourceId){
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_ID,resourceId);


        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_staff_submit_btn:
                String name = mStaffName.getText().toString();
                String email = mStaffEmial.getText().toString();
                String password = mStaffPassword.getText().toString();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getActivity(),"请输入名字",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(),"请输入密码",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(),"请输入邮件",Toast.LENGTH_SHORT).show();
                    break;
                }
                final Staff staff = new Staff(name,"",email,password);
                staff.setDepart(mStaffDepart);
                staff.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        if(mStaffAdapter == null){
                            new BmobQuery<Staff>().doSQLQuery(getActivity(), SqlHelper.SEARCH_STAFF_FROM_BMBO, new SQLQueryListener<Staff>() {
                                @Override
                                public void done(BmobQueryResult<Staff> bmobQueryResult, BmobException e) {
                                    if(e==null){
                                        mStaffInfoList = bmobQueryResult.getResults();
                                        mStaffAdapter = new StaffInfoAdapter(getActivity(),R.layout.staff_info_item,mStaffInfoList);
                                        mStaffListView.setAdapter(mStaffAdapter);
                                    }
                                }
                            });
                        }else{
                            mStaffInfoList.add(staff);
                            mStaffAdapter.notifyDataSetChanged();
                        }
                        mAddStaffView.setVisibility(View.GONE);
                        mStaffName.setText("");
                        mStaffEmial.setText("");
                        Toast.makeText(getActivity(),"添加员工成功",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getActivity(),"添加员工失败,员工已经存在或者网络不稳定",Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.add_staff_btn:
                mAddStaffView.setVisibility(View.VISIBLE);
                break;
            case R.id.add_staff_quit_btn:
                mAddStaffView.setVisibility(View.GONE);
                break;
        }
    }
    private void initManageStaffViews(View view){
        mAddStaffBtn =(ImageButton) view.findViewById(R.id.add_staff_btn);
        mAddStaffView = (LinearLayout)view.findViewById(R.id.register_staff_view);
        mSubmitBtn = (Button)view.findViewById(R.id.add_staff_submit_btn);
        mQuitBtn = (Button)view.findViewById(R.id.add_staff_quit_btn);
        mStaffEmial = (EditText)view.findViewById(R.id.register_staff_email);
        mStaffName = (EditText)view.findViewById(R.id.register_staff_name);
        mStaffPassword = (EditText)view.findViewById(R.id.register_staff_password);
        mStaffListView = (ListView)view.findViewById(R.id.mangager_staff_list) ;
        mStaffDepartSpinner = (Spinner)view.findViewById(R.id.staff_depart_select);
        mAddStaffBtn.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);
        mQuitBtn.setOnClickListener(this);
        mDepartAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item);
        mDepartAdapter.add(GlobalVariable.CAR_BEAUTY_DEPART);
        mDepartAdapter.add(GlobalVariable.CAR_MAINTENANCE_DEPART);
        mDepartAdapter.add(GlobalVariable.CAR_SALE_DEPART);
        mDepartAdapter.add(GlobalVariable.CAR_PART_DEPART);
        mStaffDepartSpinner.setAdapter(mDepartAdapter);
        mStaffDepartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStaffDepart = (String)mStaffDepartSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void initManageOrderViews(View view){
        mCarOrderListView = (ListView)view.findViewById(R.id.manager_car_order_list);
        //测是数据
        mCarBOrderListView = (ListView)view.findViewById(R.id.manager_beauty_order_list);
        mCarMOrderListView = (ListView)view.findViewById(R.id.manager_maintenance_order_list);
    }

}
