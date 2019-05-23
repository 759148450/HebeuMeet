package com.hebeu.meet.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hebeu.meet.R;
import com.hebeu.meet.domain.Activity;
import com.hebeu.meet.domain.User;
import com.hebeu.meet.domain.UserActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpUtil;
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


    List<Activity> activityList = new ArrayList<>();

    private final int resourceId;
    public ActivityAdapter(Context context, List<Activity> activityList){
        super(context,R.layout.my_publish_activity_item,activityList);
        this.resourceId = R.layout.my_publish_activity_item;
        this.activityList = activityList;
    }

  @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        final Activity activity =(Activity) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

//      title = view.findViewById(R.id.title);
//      activity_date = view.findViewById(R.id.activity_date);
//      activity_content = view.findViewById(R.id.activity_content);
//      activity_place = view.findViewById(R.id.activity_place);
//      people_actual = view.findViewById(R.id.people_actual);
//      people_limit = view.findViewById(R.id.people_limit);
//
//      Button details_button = view.findViewById(R.id.details_button);


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

//      title.setText(activity.getTitle());
//      activity_date.setText("假装有时间");
//      activity_content.setText(activity.getActivityContent());
//      activity_place.setText(activity.getActivityPlace());
//      people_actual.setText("18");
//      people_limit.setText("5");
//
//      MyThread thread=new MyThread(activity.getActivityId());
//      thread.start();
//
//      details_button.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//
//              System.out.println("当前的活动内容是：-"+activity.getActivityContent());
//          }
//      });



      return view;
    }
    class MyThread extends Thread{
        Integer activityId;
        public MyThread(Integer activityId){
            this.activityId = activityId;
        }
        @Override
        public void run() {
            Map<String,Object> paramMap = new HashMap<>();

            paramMap.put("activityId",activityId);
            String result = HttpUtil.get("112.74.194.121:8889/userActivity/selectUserActivityByActivityId",paramMap);
            JSONArray jsonArray = JSONUtil.parseArray(result);
            List<UserActivity> userActivityList = JSONUtil.toList(jsonArray, UserActivity.class);
            System.out.println("这里是关联表");
            System.out.println(userActivityList.toString());



        }
    }

    @Override
    public int getCount() {
        return activityList.size();
    }
}
