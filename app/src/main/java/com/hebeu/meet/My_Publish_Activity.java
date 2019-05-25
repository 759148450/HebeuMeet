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
           /* System.out.println(activityCreateUserList.get(0).getActivityContent());*/

//            for(ActivityCreateUser item : activityCreateUserList){
//                int activityId = item.getActivityId();
//                Map<String,Object> paramMap = new HashMap<>();
//                paramMap.put("activityId",activityId);
//                String res2 = HttpUtil.get("http://112.74.194.121:8889/activity/getActivityCreateUserById",paramMap);//根据activityId从activity表中查出关于本条活动的所有信息
//                final ActivityCreateUser activity = JSONUtil.toBean(res2, ActivityCreateUser.class);
//
//                System.out.println("ActivityCreateUser"+activity.toString());
//                ActivityCreateUser activityCreateUser = new ActivityCreateUser();
//
//                activityCreateUser.setActivityContent(activity.getActivityContent());//活动内容
////                userActivityView.setActivityDate(activity.getActivityDate());//活动日期
//                activityCreateUser.setActivityId(activity.getActivityId());//活动id
//                /*Vanilla*/
//                activityCreateUser.setUserActivityId(Integer.parseInt(activity.getUserId()));//发布人id
//                activityCreateUser.setUserName(activity.getUserName());//发布活动人姓名
//                userActivityView.setClassName(activity.getClassName());//发布活动人班级
//                userActivityView.setJoin_id(item.getUserId());//参加者id
//                /*----------------------*/
//                userActivityView.setActivityPlace(activity.getActivityPlace());//活动地点
//                userActivityView.setApplyState(activity.getApplyState());//活动状态（是否取消或者结束）
//                userActivityView.setPeopleLimit(activity.getPeopleLimit());//活动人数限制
//                userActivityView.setSexLimit(activity.getSexLimit());//活动性别限制
//                userActivityView.setJoinState(item.getJoinState());//加入状态（是否被拒绝）
//                userActivityView.setWords(item.getWords());//留言
//                userActivityView.setTitle(activity.getTitle());//活动标题
//                activityCreateUserList.add(userActivityView);
//            }
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
