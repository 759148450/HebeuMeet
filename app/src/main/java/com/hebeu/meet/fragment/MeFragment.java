package com.hebeu.meet.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hebeu.meet.EmptyActivity;
import com.hebeu.meet.MyApplyActivity;
import com.hebeu.meet.MyApplyEmpty;
import com.hebeu.meet.My_Information;
import com.hebeu.meet.My_Publish_Activity;
import com.hebeu.meet.R;
import com.hebeu.meet.Register;
import com.hebeu.meet.domain.ActivityCreateUser;
import com.hebeu.meet.domain.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.hebeu.meet.tools.ImageHandler.stringToBitmap;

/**
 * zyp
 * 个人中心
 * 2019-5-19
 *
 * lihang
 * 绑定用户头像
 * 2019-5-27
 */
public class MeFragment extends Fragment {

    private Button my_information = null;
    /* private TextView textView =null;
     private Handler handler = null;*/
    private TextView user_name=null;
    private Button button_toMyPublish = null;
    private Button button_toMyApply = null;
    private Button btn_register=null;
    private ImageView user_head = null;
    private Handler handler = null;
    /*读取的文件的字段SharedPreferences */
    private String username;
    private List<ActivityCreateUser> activityCreateUserList = null;

    private String userId;

    public MeFragment() {
    }
    /*自动刷新*/
    @Override
    public void onResume() {

        super.onResume();

        onActivityCreated(null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateUserList = new ArrayList<>();
        SharedPreferences sharedPre = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        userId=sharedPre.getString("userId","");
        username=sharedPre.getString("username", "");

        return inflater.inflate(R.layout.fragment_me, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user_head = getActivity().findViewById(R.id.personalHead3);
        handler = new Handler();
        MyTread myTread = new MyTread();
        myTread.start();
        /*---------------------------------------------------------------------------------*/

        my_information = (Button) getActivity().findViewById(R.id.my_information);
        button_toMyPublish = getActivity().findViewById(R.id.toMyPublish);
        button_toMyApply = getActivity().findViewById(R.id.toMyApply);
        btn_register = getActivity().findViewById(R.id.btn_register);
        button_toMyApply = getActivity().findViewById(R.id.toMyApply);
//        textView = getActivity().findViewById(R.id.textView);
//        handler = new Handler();
        //zyp 设置图标大小2019-5-22上午
        button_toMyPublish = getActivity().findViewById(R.id.toMyPublish);
        button_toMyApply = getActivity().findViewById(R.id.toMyApply);
        Drawable my_xinxi = getResources().getDrawable(R.drawable.my_xinxi);
        Drawable launch = getResources().getDrawable(R.drawable.launch);
        Drawable join = getResources().getDrawable(R.drawable.join);
        Drawable right = getResources().getDrawable(R.drawable.right);
        my_xinxi.setBounds(0,0,40,40);
        launch.setBounds(0,0,40,40);
        join.setBounds(0,0,40,40);
        right.setBounds(0,0,40,40);
        my_information.setCompoundDrawables(my_xinxi,null,right,null);
        button_toMyPublish.setCompoundDrawables(launch,null,right,null);
        button_toMyApply.setCompoundDrawables(join,null,right,null);
        //------------------------------------
        /*获取用户信息*/
        user_name=getActivity().findViewById(R.id.user_name);

        user_name.setText(username);

        button_toMyPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了进入个人发布按钮");
                ToMyPublishThread toMyPublishThread = new ToMyPublishThread();
                toMyPublishThread.start();
//                Intent intent = new Intent(getActivity().getApplicationContext(), My_Publish_Activity.class);
//                startActivity(intent);
            }
        });
        //跳到我参加的页面
        button_toMyApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToMyApplyThread toMyApplyThread = new ToMyApplyThread();
                toMyApplyThread.start();
            }
        });

        /*Vanilla 5-21*/
        my_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), My_Information.class);
                startActivity(intent);

            }

        });
    }
    private class ToMyPublishThread extends Thread{
        @Override
        public void run() {
            Map<String,Object> paramMap = new HashMap<>();

            paramMap.put("userId",userId);
            String result = HttpUtil.get("112.74.194.121:8889/activity/getActivityCountByUserId",paramMap);
            Long count = Long.parseLong(result);
            System.out.println("数量为："+count);
            if (0 == count){
                Intent intent = new Intent(getActivity().getApplicationContext(), EmptyActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(getActivity().getApplicationContext(), My_Publish_Activity.class);
                startActivity(intent);
            }
        }
    }
    private class MyTread extends Thread{
        public void run(){


            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId",userId);
            System.out.println(userId);
            String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserById",paramMap);
            System.out.println(res);
            final User user = JSONUtil.toBean(res,User.class);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    user_head.setImageBitmap(stringToBitmap(user.getHead()));
                }
            });
        }

    }
    private class ToMyApplyThread extends Thread{
        public void run(){
            String res = HttpUtil.get("http://112.74.194.121:8889/activity/getActivityCreateUserByJoinUserId?joinUserId="+userId);//根据userId从activity-user表中查出关于user的所有信息
            JSONArray array = JSONUtil.parseArray(res);
            activityCreateUserList = JSONUtil.toList(array, ActivityCreateUser.class);

                if(activityCreateUserList.size()>0){
                    Intent intent = new Intent(getActivity().getApplicationContext(), MyApplyActivity.class);
                    intent.putExtra("list3", (Serializable) activityCreateUserList);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MyApplyEmpty.class);
                    startActivity(intent);
            }
        }
    }

}
