package com.hebeu.meet.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.Details;
import com.hebeu.meet.HomeActivity;
import com.hebeu.meet.Login;
import com.hebeu.meet.MyApplyActivity;
import com.hebeu.meet.R;
import com.hebeu.meet.Register;
import com.hebeu.meet.domain.Activity;
import com.hebeu.meet.domain.JSONResult;

import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;


/**
 * zyp
 * 发布活动页
 * 2019-5-19
 */
public class LaunchFragment extends Fragment {
    private Activity activity = null;
    private TextView launch_message = null;
    private TextView title_message = null;
    //活动名称
    private EditText title = null;
    //活动分类
    private TextView type_id = null;
    //限制参加性别
    private TextView sex_limit = null;
    //限制人数
    private EditText people_limit = null;
    //活动时间年月日
    private TextView activity_date = null;
    //活动时间时分--zyp 2019-5-30
    private TextView activity_datetime = null;
    //活动地点
    private EditText activity_place = null;
    //活动内容
    private EditText add_content = null;
    //发布活动按钮
    private Button launch_activity= null;
    private  String type =null;
    private String  sex=null;
    ;
    // 时间、类型、性别选择按钮
    private ImageView date_Btn;
    private ImageView datetime_Btn;
    private ImageView type_Btn;
    private ImageView sex_Btn;


    private Handler handler = null;

    public LaunchFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_launch, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //活动名称
        title = getActivity().findViewById(R.id.title);;
        //活动分类
        type_id = getActivity().findViewById(R.id.type_id);;
        //限制参加性别
        sex_limit = getActivity().findViewById(R.id.sex_limit);;
        //限制人数
        people_limit = getActivity().findViewById(R.id.people_limit);;
        //活动时间
        activity_date = getActivity().findViewById(R.id.activity_date);;
         // 活动时间  时分
        activity_datetime = getActivity().findViewById(R.id.activity_datetime);
        //活动地点
        activity_place = getActivity().findViewById(R.id.activityPlace);;
        //活动内容
        add_content = getActivity().findViewById(R.id.add_content);;
        //发布活动按钮
        launch_activity = (Button) getActivity().findViewById(R.id.launch_activity);

        //-------zyp 选择时间picker---2019-5-25-----------

        date_Btn = getActivity().findViewById(R.id.date_btn);
        datetime_Btn = getActivity().findViewById(R.id.datetime_btn);
        type_Btn = getActivity().findViewById(R.id.type_btn);
        sex_Btn = getActivity().findViewById(R.id.sex_btn);
        //---------年月日------------
        date_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setYearDate(getActivity(), new DateListener() {
                    @Override
                    public void setYearDate(String year, String month, String day) {
                        activity_date.setText(year + "-" + month + "-" + day );
                    }
                });
            }
        });
        //时分
        datetime_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setDateTime(getActivity(), new DateTimeListener() {
                    @Override
                    public void setDateTime(String hour, String minute) {
                        activity_datetime.setText(hour + ":" + minute);
                    }
                });
            }
        });
        //   分类选择
        type_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setTypeName(getActivity(), new TypeListener() {
                    @Override
                    public void setTypeName(String typename) {
                        type_id.setText(typename);
                    }
                });
            }
        });

        //选择性别
        sex_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setSexName(getActivity(), new SexListener() {
                    @Override
                    public void setSexName(String sexname) {
                        sex_limit.setText(sexname);
                    }
                });
            }
        });
        //---end--------------


        launch_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().trim().equals("")){
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("警告").setMessage("标题不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(type_id.getText().toString().trim().equals("")){
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("警告").setMessage("类型不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(sex_limit.getText().toString().trim().equals("")){
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("警告").setMessage("性别限制不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(people_limit.getText().toString().trim().equals("")){
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("警告").setMessage("人数限制不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(activity_date.getText().toString().trim().equals("")){
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("警告").setMessage("活动日期不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(activity_datetime.getText().toString().trim().equals("")){
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("警告").setMessage("活动时间不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(activity_place.getText().toString().trim().equals("")){
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("警告").setMessage("活动地点不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(add_content.getText().toString().trim().equals("")){
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle("警告").setMessage("活动内容不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }
                else {
                    MyThread thread = new MyThread();
                    thread.start();
                }
            }

            class MyThread extends Thread{

                @Override
                public void run() {
                    Activity newActivity = new Activity();
                    type=String.valueOf(type_id.getText());
                    sex=String.valueOf(sex_limit.getText());

                    /*获取登录的用户信息*/
                    SharedPreferences sharedPre = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
                    String userId=sharedPre.getString("userId", "");
                    newActivity.setUserId(userId);
                    newActivity.setTitle(String.valueOf(title.getText()));
                    System.out.println("type"+type );
                    if(type.equals("约吃饭")){
                        newActivity.setTypeId(1);
                    } else if(type.equals("约跑步")){
                        newActivity.setTypeId(2);
                    } else if(type.equals("约逛街")){
                        newActivity.setTypeId(3);
                    } else if(type.equals("约学习")){
                        newActivity.setTypeId(4);
                    } else if(type.equals("约游戏")){
                        newActivity.setTypeId(5);
                    }else if(type.equals("约其他")){
                        newActivity.setTypeId(0);
                    }
                    System.out.println("sex"+sex );
                    if(sex.equals("男")){
                        newActivity.setSexLimit(0);
                    }else if(sex.equals("女")){
                        newActivity.setSexLimit(1);
                    }else if(sex.equals("不限")){
                        newActivity.setSexLimit(2);
                    }
                    newActivity.setActivityDate(String.valueOf(activity_date.getText())+"-"+activity_datetime.getText());
                    newActivity.setPeopleLimit(Integer.parseInt(String.valueOf(people_limit.getText())));
                    newActivity.setActivityContent(String.valueOf(add_content.getText()));
                    newActivity.setApplyState("1");
                    newActivity.setActivityPlace(String.valueOf(activity_place.getText()));
                    Looper.prepare();
                    try {
                        Map<String,Object> paramMap = BeanUtil.beanToMap(newActivity);
                        String res = HttpUtil.post("http://112.74.194.121:8889/activity/insertActivity",paramMap);
                        activity = JSONUtil.toBean(res,Activity.class);
                        System.out.println("newactivity"+activity.toString());
                        if (activity==null){
                            Toast.makeText(getActivity(),"活动添加失败",Toast.LENGTH_SHORT).show();
                        }else {
                            showDialog();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(),"活动添加失败",Toast.LENGTH_SHORT).show();
                    }

                    Looper.loop();

                }
            }
            private void showDialog(){
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(getActivity() ,R.style.MyDialogStyle);
//                            normalDialog.setIcon(R.drawable.icon_dialog);
                normalDialog.setIcon(R.drawable.success);
                normalDialog.setTitle("活动添加成功");
                normalDialog.setMessage("请选择将要前往的页面");
                normalDialog.setPositiveButton("首页",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("您点击了【首页】");
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        });
                normalDialog.setNegativeButton("活动详情",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getContext(), Details.class);
                                Bundle b = new Bundle();

                                b.putInt("activity_id", activity.getActivityId());
                                b.putString("activity_title", activity.getTitle());
                                b.putString("activity_place",activity.getActivityPlace());
                                b.putString("activity_time",activity.getActivityDate());
                                b.putString("activity_sexLimit",activity.getSexLimit().toString());
                                b.putString("activity_PeopleLimit",activity.getPeopleLimit().toString());
                                SharedPreferences sharedPre = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
                                String qq=sharedPre.getString("qq", "");
                                String phone=sharedPre.getString("phone", "");
                                String userId=sharedPre.getString("userId", "");
                                String username=sharedPre.getString("username", "");
                                String classname=sharedPre.getString("classname", "");

                                b.putString("activity_qq",qq);
                                b.putString("activity_phone",phone);
                                b.putString("activity_content",activity.getActivityContent());
                                b.putString("activity_user_id",userId);
                                b.putString("activity_user_name",username);
                                b.putString("activity_user_class",classname);//发布者专业班级
//

//                                b.putString("activity_qq","qq");
//                                b.putString("activity_phone","phone");
//                                b.putString("activity_content","content");
//                                b.putString("activity_user_id",userId);
//                                b.putString("activity_user_name","qwe");
//                                b.putString("activity_user_class","qwe");//发布者专业班级
//                                b.putString("join_state",userActivityViewList.get(position).getJoinState());
//                                b.putString("join_id", userActivityViewList.get(position).getJoin_id());//参加者id

//                                b.putString("activity_user_head",activityCreateUser.getHead());
//                                intent.putExtras(b);
                                intent.putExtras(b);
                                getContext().startActivity(intent);
                                System.out.println("您点击了 【活动详情】");
                            }
                        });
                // 显示
                normalDialog.show();
            }
        });
    }
}
