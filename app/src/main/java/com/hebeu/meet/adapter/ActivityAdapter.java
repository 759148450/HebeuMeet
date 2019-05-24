package com.hebeu.meet.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hebeu.meet.R;
import com.hebeu.meet.domain.Activity;
import com.hebeu.meet.domain.ActivityCreateUser;
import com.hebeu.meet.domain.User;
import com.hebeu.meet.domain.UserActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * yuechunpeng
 */
public class ActivityAdapter extends ArrayAdapter {


    //zyp 修改图标大小 2019-5-22
    private TextView activityPlace =null;
    private TextView activityTime =null;
    private TextView activitySexLimit =null;
//    -------------------
    private TextView activityTitle = null;
    private TextView activityUser2 = null;
    private ImageView sexImage = null;

    private Button button = null;
    private Handler handler = null;

    List<ActivityCreateUser> activityCreateUserList = new ArrayList<>();

    private final int resourceId;
    public ActivityAdapter(Context context, List<ActivityCreateUser> activityCreateUserList){
        super(context,R.layout.my_publish_activity_item,activityCreateUserList);
        this.resourceId = R.layout.my_publish_activity_item;
        this.activityCreateUserList = activityCreateUserList;
    }

  @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        final ActivityCreateUser activityCreateUser =(ActivityCreateUser) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);



        //zyp 设置图标大小2019-5-22 晚
        //此处李航上面写的content，如要修改，需统一
         activityPlace = view.findViewById(R.id.activityPlace);
         activityTime = view.findViewById(R.id.activityTime);
         activitySexLimit = view.findViewById(R.id.activitySexLimit);

         Drawable place = getContext().getResources().getDrawable(R.drawable.place);
         Drawable time = getContext().getResources().getDrawable(R.drawable.time);
         Drawable sex = getContext().getResources().getDrawable(R.drawable.sex);

         place.setBounds(0,0,32,35);
         time.setBounds(0,0,32,32);
         sex.setBounds(0,0,32,32);
         activityPlace.setCompoundDrawables(place,null,null,null);
         activityTime.setCompoundDrawables(time,null,null,null);
         activitySexLimit.setCompoundDrawables(sex,null,null,null);
         //------------over------------------

          //数据显示
          //yuechunpeng

        activityTitle = view.findViewById(R.id.activityTitle);
        activityUser2 = view.findViewById(R.id.activityUser2);
        button = view.findViewById(R.id.button);
        sexImage = view.findViewById(R.id.sex);

        activityTitle.setText(activityCreateUser.getTitle());
        activityPlace.setText(activityCreateUser.getActivityPlace());
        if(activityCreateUser.getActivityDate() != null){
            activityTime.setText(activityCreateUser.getActivityDate().toString());
        }else {
            activityTime.setText("2019-6-1");
        }
        switch (activityCreateUser.getSexLimit()){
            case 0 :activitySexLimit.setText("男"); break;
            case 1 :activitySexLimit.setText("女"); break;
            case 2 :activitySexLimit.setText("不限男女"); break;
        }

        activityUser2.setText(activityCreateUser.getUserName());
        handler = new Handler();
//        MyThread1 myThread1 = new MyThread1(activity.getUserId());
//        myThread1.start();


         return view;
    }
//    class MyThread extends Thread{
//        Integer activityId;
//        public MyThread(Integer activityId){
//            this.activityId = activityId;
//        }
//        @Override
//        public void run() {
//            Map<String,Object> paramMap = new HashMap<>();
//
//            paramMap.put("activityId",activityId);
//            String result = HttpUtil.get("112.74.194.121:8889/userActivity/selectUserActivityByActivityId",paramMap);
//            JSONArray jsonArray = JSONUtil.parseArray(result);
//            List<UserActivity> userActivityList = JSONUtil.toList(jsonArray, UserActivity.class);
//            System.out.println("这里是关联表");
//            System.out.println(userActivityList.toString());
//
//
//        }
//    }
//    class MyThread1 extends Thread{
//        String userId;
//        public MyThread1(String userId){
//            this.userId = userId;
//        }
//        @Override
//        public void run() {
//            synchronized (this){
//                Map<String,Object> paramMap = new HashMap<>();
//                paramMap.put("userId",userId);
//                System.out.println("学号为："+userId);
//                String result = null;
//                try {
//                    result = HttpUtil.get("112.74.194.121:8889/user/getUserById",paramMap);
//                    final User user = JSONUtil.toBean(result, User.class);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            activityUser2.setText(user.getUserName());
//                        }
//                    });
//                }catch (Exception e){
//                    System.out.println(e);
//                }
//            }
////            System.out.println(result);
//
//
//        }
//    }

    @Override
    public int getCount() {
        return activityCreateUserList.size();
    }
}
