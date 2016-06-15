package com.roy.automobileservice.utils;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.roy.automobileservice.R;

import com.roy.automobileservice.activity.CarModelsListActivity;

import com.roy.automobileservice.activity.LoginActivity;
import com.roy.automobileservice.activity.RegisterActivity;
import com.roy.automobileservice.adapter.CarBOrderAdapter;
import com.roy.automobileservice.adapter.CarMOrderAdapter;
import com.roy.automobileservice.adapter.CarOrderAdapter;
import com.roy.automobileservice.adapter.StaffInfoAdapter;
import com.roy.automobileservice.adapter.UserNameAdapter;
import com.roy.automobileservice.cls.ActivityCollector;

import com.roy.automobileservice.cls.CarBOrder;
import com.roy.automobileservice.cls.CarMOrder;
import com.roy.automobileservice.cls.CarOrder;
import com.roy.automobileservice.cls.ChinaCar;
import com.roy.automobileservice.cls.HeadSculpture;
import com.roy.automobileservice.cls.Staff;
import com.roy.automobileservice.cls.User;
import com.roy.automobileservice.layout.AvatarImageView;
import com.roy.automobileservice.thread.AssistantCallThread;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.roy.automobileservice.utils.GlobalVariable.*;

public class Utils {
    //public static final int HOME_PAGE_REQUEST_CODE = 1;
    public static void showTipAndCall(final Context context, final int tipMsg, final String telNum) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        setDialogContent(dialog, tipMsg);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new AssistantCallThread(context, telNum)).start()
                ;
            }
        });

        dialog.show();
    }

    /**
     * 提示用户是否要购买汽车
     * @param context
     * @param tipMsg

     */
    public static void showTipAndJumpToCarModel(final Context context, final String tipMsg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        setDialogContent(dialog, tipMsg);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                CarModelsListActivity.startAction(context);
            }
        });

        dialog.show();
    }

    /**
     * 提示用户确认美容订单
     * @param context
     * @param tipMsg
     */
    public static void showTipAndSubmitBOrder(final Context context, final String tipMsg, final CarBOrder order) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        setDialogContent(dialog, tipMsg);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                order.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context,"提交美容订单成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context,"提交美容订单失败，可能是网络原因导致",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    /**
     * 提示用户是否真的要退出
     *
     * @param context
     * @param tipMsg
     */
    public static void showTipAndExit(final Context context, final String tipMsg, final Button login, final AvatarImageView userIcon) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        setDialogContent(dialog, tipMsg);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                login.setText(R.string.login_button);
                userIcon.setImageResource(R.drawable.user_default_icon1);
                currentUser = null;
                LoginActivity.isLogin = false;
                loginType = LOGIN_FALSE;
            }
        });

        dialog.show();
    }

    public static void showTipAndLogin(final Context context, final int tipMsg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        setDialogContent(dialog, tipMsg);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                loginType = USER_LOGIN;
                LoginActivity.startAction(context);
            }
        });

        dialog.show();
    }

    public static void showTipAndDeleteUer(final Context context, final int tipMsg, final Integer position,
                                           final UserNameAdapter userNameAdapter,
                                           final List<User> userList) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        setDialogContent(dialog, tipMsg);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                userList.remove(Integer.valueOf(position).intValue());
                userNameAdapter.notifyDataSetChanged();
            }
        });

        dialog.show();
    }

    /**
     * 提醒管理员是否删除员工
     * @param context
     * @param tipMsg
     * @param position
     * @param staffInfoAdapter
     * @param staffList
     */
    public static void showTipAndDeleteStaff(final Context context, final String tipMsg, final Integer position,
                                           final StaffInfoAdapter staffInfoAdapter,
                                           final List<Staff> staffList) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        setDialogContent(dialog, tipMsg);
        dialog.setTitle("警告！！");
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Staff staff = new Staff();
                staff.setObjectId(staffList.get(position).getObjectId());
                staff.delete(context, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        staffList.remove(Integer.valueOf(position).intValue());
                        staffInfoAdapter.notifyDataSetChanged();
                        Toast.makeText(context, "成功删除员工", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context, "删除用户失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dialog.show();
    }

    /**
     * 提示管理确认删除用户
     *
     * @param context
     * @param tipMsg
     * @param position
     * @param userNameAdapter
     */
    public static void showTipToManagerAndDeleteUer(final Context context, final String tipMsg,
                                                    final Integer position, final UserNameAdapter userNameAdapter,
                                                    final List<User> list) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        setDialogContent(dialog, tipMsg);
        dialog.setTitle("警告！！");
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = new User();
                user.setObjectId(list.get(position).getObjectId());
                user.delete(context, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        list.remove(Integer.valueOf(position).intValue());
                        userNameAdapter.notifyDataSetChanged();
                        Toast.makeText(context, "成功删除用户", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context, "删除用户失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        dialog.show();
    }

    private static void setDialogContent(AlertDialog.Builder dialog, int tipMsg) {
        dialog.setTitle(R.string.tip_text);
        dialog.setMessage(tipMsg);
        dialog.setCancelable(true);
        dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
    }

    private static void setDialogContent(AlertDialog.Builder dialog, String tipMsg) {
        dialog.setTitle(R.string.tip_text);
        dialog.setMessage(tipMsg);
        dialog.setCancelable(true);
        dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
    }

    public static void showBackToLogin(final Activity context, final String tipMsg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        //setDialogContent(dialog,tipMsg);
        dialog.setTitle(R.string.tip_text);
        dialog.setMessage(tipMsg);
        dialog.setCancelable(true);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.removeActivity(context);
            }
        });
        dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        dialog.show();
    }

    public static void showBackToMyInfo(final Activity context, final String tipMsg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        //setDialogContent(dialog,tipMsg);
        dialog.setTitle(R.string.tip_text);
        dialog.setMessage(tipMsg);
        dialog.setCancelable(true);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.removeActivity(context);
            }
        });
        dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        dialog.show();
    }

    public static void showCarorderInfo(final Activity context, final String tipMsg,final CarOrder order) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        //setDialogContent(dialog,tipMsg);
        dialog.setTitle(R.string.tip_text);
        dialog.setMessage(tipMsg);
        dialog.setCancelable(true);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               order.save(context, new SaveListener() {
                   @Override
                   public void onSuccess() {
                        Toast.makeText(context, "订单提交成功", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onFailure(int i, String s) {
                       Toast.makeText(context, "订单提交失败", Toast.LENGTH_SHORT).show();

                   }
               });


            }
        });
        dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        dialog.show();
    }

    public static void showLoRegister(final Activity context, final String tipMsg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        //setDialogContent(dialog,tipMsg);
        dialog.setTitle(R.string.tip_text);
        dialog.setMessage(tipMsg);
        dialog.setCancelable(true);
        dialog.setPositiveButton(R.string.yes_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                RegisterActivity.startAction(context);
            }
        });
        dialog.setNegativeButton(R.string.cancle_bt, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });

        dialog.show();
    }

    /**
     * @param name
     * @return
     */
    public static boolean isContainOfName(String name, List<User> list) {
        for (User tem : list) {
            if (tem.getUserName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param target
     * @param len
     * @return
     */
    public static List<User> getBackRestraintLenthOfUserList(List<User> target, int len) {
        List<User> tem = new ArrayList<User>();
        int targetSize = target.size();
        if (len > targetSize) {
            len = targetSize;
        }
        for (int i = target.size() - 1; i >= targetSize - len; i--) {
            tem.add(target.get(i));
        }
        return tem;
    }

    /**
     * @param editText
     * @param hintMsg
     * @param hintSize
     */
    public static void setEditTextHintSize(EditText editText, String hintMsg, int hintSize) {
        SpannableString ss = new SpannableString(hintMsg);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(hintSize, true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss)); //
    }

    public static void deleteStaticVariables() {
        TestData.carList.clear();
        TestData.headSculpturesList.clear();
        TestData.userTestList.clear();
        //HomePageActivity.carTemps.clear();
        //GlobalVariable.currentUser = new User();
    }

    /**
     * 初始化用测试用户数据并上传到服务器、只使用一次
     *
     * @param context
     */
    public static void initChinaCars(final Context context) {


        new BmobQuery<ChinaCar>().doSQLQuery(context, SqlHelper.SEARCH_CAR_FROM_BMBO, new SQLQueryListener<ChinaCar>() {
            @Override
            public void done(BmobQueryResult<ChinaCar> bmobQueryResult, BmobException e) {
                if (e == null) {
                    TestData.chinaCars = bmobQueryResult.getResults();
                    Log.d("TAG", "" + TestData.chinaCars.size());
                    //Toast.makeText(context,""+TestData.chinaCars.size(),Toast.LENGTH_SHORT).show();
                    initUserTest(context);
                    List<BmobObject> user = new ArrayList<BmobObject>();
                    for (User tem : TestData.userTestList) {
                        user.add(tem);
                    }
                    new BmobObject().insertBatch(context, user, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(context, "添加数据成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                } else {
                    Toast.makeText(context, "没有读到数据", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * 初始化测试用户数据
     *
     * @param context
     */
    public static void initUserTest(Context context) {
        ///Utils.initChinaCars(context);

        User userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user1_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon1);
        //userNameItem.setCar(TestData.chinaCars.get(0).getCarName());
        userNameItem.setCarName(TestData.chinaCars.get(0).getCarName());
        userNameItem.setEmail("user1@qq.com");
        userNameItem.setPassword("user1");
        userNameItem.setRealName(context.getResources().getString(R.string.user1_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user1_sex));
        userNameItem.setTelNumber("15062356840");
        TestData.userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user2_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon2);
        //userNameItem.setCar(TestData.chinaCars.get(2));
        userNameItem.setCarName(TestData.chinaCars.get(2).getCarName());
        userNameItem.setEmail("user2@qq.com");
        userNameItem.setPassword("user2");
        userNameItem.setRealName(context.getResources().getString(R.string.user2_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user2_sex));
        userNameItem.setTelNumber("18665987586");
        TestData.userTestList.add(userNameItem);
        userNameItem = new User();
        userNameItem.setUserName(context.getResources().getString(R.string.user3_name));
        userNameItem.setAvatarImage(R.drawable.user_default_icon3);
        userNameItem.setCar(null);
        userNameItem.setEmail("user3@qq.com");
        userNameItem.setPassword("user3");
        userNameItem.setRealName(context.getResources().getString(R.string.user3_real_name));
        userNameItem.setSex(context.getResources().getString(R.string.user3_sex));
        userNameItem.setTelNumber("13556789564");
        TestData.userTestList.add(userNameItem);


        HeadSculpture temSculpture = new HeadSculpture(R.drawable.user_default_icon1,
                context.getResources().getString(R.string.default_cion1_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon2,
                context.getResources().getString(R.string.default_cion2_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon3,
                context.getResources().getString(R.string.default_cion3_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon4,
                context.getResources().getString(R.string.default_cion4_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon5,
                context.getResources().getString(R.string.default_cion5_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon6,
                context.getResources().getString(R.string.default_cion6_name));
        TestData.headSculpturesList.add(temSculpture);

    }

    /**
     * 初始化头像数据结构
     */
    public static void initHeadSculptures(Context context) {
        HeadSculpture temSculpture = new HeadSculpture(R.drawable.user_default_icon1,
                context.getResources().getString(R.string.default_cion1_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon2,
                context.getResources().getString(R.string.default_cion2_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon3,
                context.getResources().getString(R.string.default_cion3_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon4,
                context.getResources().getString(R.string.default_cion4_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon5,
                context.getResources().getString(R.string.default_cion5_name));
        TestData.headSculpturesList.add(temSculpture);
        temSculpture = new HeadSculpture(R.drawable.user_default_icon6,
                context.getResources().getString(R.string.default_cion6_name));
        TestData.headSculpturesList.add(temSculpture);
    }

    /**
     * 从服务器把用户数据拉下来
     */
    public static void getUserInfoFromBmob(final Context context) {


        new BmobQuery<User>().doSQLQuery(context, SqlHelper.SEARCH_USER_FROM_BMBO, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                if (e == null) {
                    TestData.userTestList.clear();
                    TestData.userTestList = bmobQueryResult.getResults();
                    //Toast.makeText(context,""+TestData.userTestList.size(),Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "没有读到数据", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * 通过汽车名字获得一个汽车对象
     */
    public static ChinaCar getCarByName(String carName, List<ChinaCar> list) {
        ChinaCar car = null;
        if (!TextUtils.isEmpty(carName)) {
            Iterator<ChinaCar> it = list.iterator();
            while (it.hasNext()) {
                ChinaCar temp = it.next();
                if (temp.getCarName().equals(carName)) {
                    car = temp;
                    break;
                }
            }
        }

        return car;

    }

    /**
     * 根据listview的子部件决定listview的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public static View getView(Context context,int layoutId){
        LayoutInflater inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(layoutId, null);
        return layout;

    }
    private static AlertDialog mAlertDialog;

    /**
     * 显示选择汽车员工界面
     * @param context
     * @param order
     * @param button
     */
    public static void showDialog(final Context context, final CarOrder order, final Button button, final CarOrderAdapter carAdapter){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = getView(context, R.layout.dialog_view);
        final List<String> staffNames = new ArrayList<>();
        final ListView listView= (ListView)dialogView.findViewById(R.id.dialog_content);
        String bql = "select * from Staff where depart='销售部'";
        new BmobQuery<Staff>().doSQLQuery(context, bql, new SQLQueryListener<Staff>() {
            @Override
            public void done(BmobQueryResult<Staff> bmobQueryResult, BmobException e) {
                if(e==null){
                    List<Staff> staffs = bmobQueryResult.getResults();
                    Iterator<Staff> it = staffs.iterator();
                    //staffNames = new ArrayList<String>();
                    while (it.hasNext()){
                        Staff staff = it.next();
                        staffNames.add(staff.getName());
                    }
                    if(staffNames!=null&&staffNames.size()>0){
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,staffNames);
                        listView.setAdapter(adapter);
                        builder.setView(dialogView);
                        mAlertDialog = builder.create();
                        mAlertDialog.show();
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order.setStaffName(staffNames.get(position));
                order.setState(OrderHP.ORDER_HANDLE_STATE);
                //Toast.makeText(context,staffNames.get(position),Toast.LENGTH_SHORT).show();
                order.update(context, order.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        button.setText(OrderHP.HANDLE_BUTTON_TEXT);
                        button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                        Toast.makeText(context,"分发任务成功",Toast.LENGTH_SHORT).show();
                        carAdapter.notifyDataSetChanged();
                        mAlertDialog.dismiss();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context,"分发任务失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    /**
     * 选择美容员工
     * @param context
     * @param order
     * @param button
     * @param beautyAdapter
     */
    public static void showDialogBeauty(final Context context, final CarBOrder order, final Button button, final CarBOrderAdapter beautyAdapter){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = getView(context, R.layout.dialog_view);
        final List<String> staffNames = new ArrayList<>();
        final ListView listView= (ListView)dialogView.findViewById(R.id.dialog_content);
        String bql = "select * from Staff where depart='美容部'";
        new BmobQuery<Staff>().doSQLQuery(context, bql, new SQLQueryListener<Staff>() {
            @Override
            public void done(BmobQueryResult<Staff> bmobQueryResult, BmobException e) {
                if(e==null){
                    List<Staff> staffs = bmobQueryResult.getResults();
                    Iterator<Staff> it = staffs.iterator();
                    while (it.hasNext()){
                        Staff staff = it.next();
                        staffNames.add(staff.getName());
                    }
                    if(staffNames!=null&&staffNames.size()>0){
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,staffNames);
                        listView.setAdapter(adapter);
                        builder.setView(dialogView);
                        mAlertDialog = builder.create();
                        mAlertDialog.show();
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order.setStaffName(staffNames.get(position));
                order.setState(OrderHP.ORDER_HANDLE_STATE);
                order.update(context, order.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        button.setText(OrderHP.HANDLE_BUTTON_TEXT);
                        button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                        Toast.makeText(context,"分发任务成功",Toast.LENGTH_SHORT).show();
                        beautyAdapter.notifyDataSetChanged();
                        mAlertDialog.dismiss();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context,"分发任务失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    public static void showDialogMaintence(final Context context, final CarMOrder order, final Button button, final CarMOrderAdapter mainAdapter){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = getView(context, R.layout.dialog_view);
        final List<String> staffNames = new ArrayList<>();
        final ListView listView= (ListView)dialogView.findViewById(R.id.dialog_content);
        String bql = "select * from Staff where depart='保养部'";
        new BmobQuery<Staff>().doSQLQuery(context, bql, new SQLQueryListener<Staff>() {
            @Override
            public void done(BmobQueryResult<Staff> bmobQueryResult, BmobException e) {
                if(e==null){
                    List<Staff> staffs = bmobQueryResult.getResults();
                    Iterator<Staff> it = staffs.iterator();
                    while (it.hasNext()){
                        Staff staff = it.next();
                        staffNames.add(staff.getName());
                    }
                    if(staffNames!=null&&staffNames.size()>0){
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,staffNames);
                        listView.setAdapter(adapter);
                        builder.setView(dialogView);
                        mAlertDialog = builder.create();
                        mAlertDialog.show();
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                order.setStaffName(staffNames.get(position));
                order.setState(OrderHP.ORDER_HANDLE_STATE);
                order.update(context, order.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        button.setText(OrderHP.HANDLE_BUTTON_TEXT);
                        button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                        Toast.makeText(context,"分发任务成功",Toast.LENGTH_SHORT).show();
                        mainAdapter.notifyDataSetChanged();
                        mAlertDialog.dismiss();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context,"分发任务失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    /**
     * 根据订单状态设置按钮的状态
     * @param context
     * @param button
     * @param order
     */
    public static void setButtonText(Context context ,Button button,CarOrder order){
        switch (order.getState()){
            case OrderHP.ORDER_SUBMIT_STATE:
                button.setText(OrderHP.SUBMIT_BUTTON_TEXT);
                break;
            case OrderHP.ORDER_HANDLE_STATE:
                button.setText(OrderHP.HANDLE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
            case OrderHP.ORDER_COMPLETE_STATE:
                button.setText(OrderHP.COMPLETE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
        }
    }
    public static void setStaffButtonText(Context context ,Button button,CarOrder order){
        switch (order.getState()){
            case OrderHP.ORDER_HANDLE_STATE:
                button.setText(OrderHP.STAFF_BUTTON_TEXT);
                //button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
            case OrderHP.ORDER_COMPLETE_STATE:
                button.setText(OrderHP.STAFF_COMPLETE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
        }
    }
    public static void setStaffButtonText(Context context ,Button button,CarBOrder order){
        switch (order.getState()){
            case OrderHP.ORDER_HANDLE_STATE:
                button.setText(OrderHP.STAFF_BUTTON_TEXT);
               // button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
            case OrderHP.ORDER_COMPLETE_STATE:
                button.setText(OrderHP.STAFF_COMPLETE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
        }
    }
    public static void setStaffButtonText(Context context ,Button button,CarMOrder order){
        switch (order.getState()){
            case OrderHP.ORDER_HANDLE_STATE:
                button.setText(OrderHP.STAFF_BUTTON_TEXT);
                //button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
            case OrderHP.ORDER_COMPLETE_STATE:
                button.setText(OrderHP.STAFF_COMPLETE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
        }
    }
    public static void setButtonText(Context context ,Button button,CarBOrder order){
        switch (order.getState()){
            case OrderHP.ORDER_SUBMIT_STATE:
                button.setText(OrderHP.SUBMIT_BUTTON_TEXT);
                break;
            case OrderHP.ORDER_HANDLE_STATE:
                button.setText(OrderHP.HANDLE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
            case OrderHP.ORDER_COMPLETE_STATE:
                button.setText(OrderHP.COMPLETE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
        }
    }
    public static void setButtonText(Context context ,Button button,CarMOrder order){
        switch (order.getState()){
            case OrderHP.ORDER_SUBMIT_STATE:
                button.setText(OrderHP.SUBMIT_BUTTON_TEXT);
                break;
            case OrderHP.ORDER_HANDLE_STATE:
                button.setText(OrderHP.HANDLE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
            case OrderHP.ORDER_COMPLETE_STATE:
                button.setText(OrderHP.COMPLETE_BUTTON_TEXT);
                button.setBackgroundColor(context.getResources().getColor(R.color.handle_button_color));
                break;
        }
    }

    /**
     * 显示具体的美容项目
     * @param context
     * @param order
     */
    public static void showDialogBeautyContent(final Context context, final CarBOrder order){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = getView(context, R.layout.dialog_view);
        final ListView listView= (ListView)dialogView.findViewById(R.id.dialog_content);
        TextView title =(TextView) dialogView.findViewById(R.id.dialog_title);
        title.setText("具体保养项目");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,order.getBeautyList());
        listView.setAdapter(adapter);
        builder.setView(dialogView);
        mAlertDialog = builder.create();
        mAlertDialog.show();

    }
    public static final int USER_DEFAULT_ICON1 = 1110;
    public static final int USER_DEFAULT_ICON2 = 1111;
    public static final int USER_DEFAULT_ICON3 = 1112;
    public static final int USER_DEFAULT_ICON4 = 1113;
    public static final int USER_DEFAULT_ICON5 = 1114;
    public static final int USER_DEFAULT_ICON6 = 1115;

    /**
     * 通过int值获得头像
     * @param icon
     * @return
     */
    public static int getUserIconByInt(Integer icon){
        switch (icon.intValue()){
            case USER_DEFAULT_ICON1:
                return R.drawable.user_default_icon1;
            case USER_DEFAULT_ICON2:
                return R.drawable.user_default_icon2;
            case USER_DEFAULT_ICON3:
                return R.drawable.user_default_icon3;
            case USER_DEFAULT_ICON4:
                return R.drawable.user_default_icon4;
            case USER_DEFAULT_ICON5:
                return R.drawable.user_default_icon5;
            case USER_DEFAULT_ICON6:
                return R.drawable.user_default_icon6;
            default:
                return R.drawable.user_default_icon1;
        }
    }
    /**
     * 根据头像获取int值
     */
    public static Integer getIntByUserIcon(int icon){
        switch (icon){
            case 0:
                return USER_DEFAULT_ICON1;
            case 1:
                return USER_DEFAULT_ICON2;
            case 2:
                return USER_DEFAULT_ICON3;
            case 3:
                return USER_DEFAULT_ICON4;
            case 4:
                return USER_DEFAULT_ICON5;
            case 5:
                return USER_DEFAULT_ICON6;
            default:
                return USER_DEFAULT_ICON1;
        }
    }
}
