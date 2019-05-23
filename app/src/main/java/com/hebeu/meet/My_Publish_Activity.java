package com.hebeu.meet;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hebeu.meet.adapter.ActivityAdapter;
import com.hebeu.meet.domain.Activity;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * 2019.5.21
 * 我发布的活动列表
 * 李航
 */
public class My_Publish_Activity extends AppCompatActivity {

    private List<Activity> activityList = new ArrayList<>();
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
            String res = HttpUtil.get("http://112.74.194.121:8889/activity/selectActivityByUserId?userId=160210405");
            JSONArray array = JSONUtil.parseArray(res);
            activityList = JSONUtil.toList(array, Activity.class);
            System.out.println(activityList.get(0).getActivityContent());

            handler.post(new Runnable() {
                @Override
                public void run() {

                    ActivityAdapter activityAdapter = new ActivityAdapter(My_Publish_Activity.this, activityList);

                    listView.setAdapter(activityAdapter);
                }
            });
        }
    }

}
