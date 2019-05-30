package com.hebeu.meet;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.widget.ListView;


import com.hebeu.meet.adapter.ActivityAdapter;

import com.hebeu.meet.domain.ActivityCreateUser;
import com.hebeu.meet.domain.ActivityJoinUser;
import com.hebeu.meet.domain.UserActivityView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * 2019.5.21
 * 我发布的活动列表
 * 李航
 */
public class My_Publish_Activity extends AppCompatActivity {

    private List<ActivityCreateUser> activityCreateUserList = new ArrayList<>();
    private Handler handler = null;
    ListView listView = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_publish_activity);
        listView = findViewById(R.id.MyPub);
        handler = new Handler();
        My_Publish_Activity.MyThread thread=new MyThread();
        thread.start();


    }
    class MyThread extends Thread {
        public void run() {
            /*获取登录的用户信息*/
            SharedPreferences sharedPre = getSharedPreferences("config", MODE_PRIVATE);
            String userId=sharedPre.getString("userId", "");
            String res = HttpUtil.get("http://112.74.194.121:8889/activity/selectActivityCreateUserByUserId?userId="+userId);
            JSONArray array = JSONUtil.parseArray(res);
            activityCreateUserList = JSONUtil.toList(array, ActivityCreateUser.class);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ActivityAdapter activityAdapter = new ActivityAdapter(My_Publish_Activity.this, activityCreateUserList);
                    listView.setAdapter(activityAdapter);
                }
            });
        }
    }

}
