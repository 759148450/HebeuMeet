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

    //zyp 修改图标大小 2019-5-22
    private TextView activityPlace =null;
    private TextView activityTime =null;
    private TextView activitySexLimit =null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_publish_activity);
        listView = findViewById(R.id.MyPub);
        handler = new Handler();
        My_Publish_Activity.MyThread thread=new MyThread();
        thread.start();
//        SystemClock.sleep(1500);//主线程睡眠1s，等待新线程读取数据


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
                    MyBaseAdapter baseAdapter = new MyBaseAdapter();
                    //把数组适配器加载到ListView控件中
                    listView.setAdapter(baseAdapter);
                }
            });
        }
    }

    class MyBaseAdapter extends BaseAdapter{
        //获取当前items项的大小，也可以看成是数据源的大小
        @Override
        public int getCount() {
            return activityList.size();
        }
        //根据item的下标获取到View对象
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }
        //获取到items的id
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            LayoutInflater inflater = My_Publish_Activity.this.getLayoutInflater();
            view = inflater.inflate(R.layout.my_publish_activity_item, null);
            Activity u = activityList.get(position);//通过回调这个方法传过来的position参数获取到指定数据源中的对象
            TextView content = view.findViewById(R.id.activityPlace);
            TextView title = view.findViewById(R.id.activityTitle);
             title.setText(u.getTitle());
            content.setText(u.getActivityContent());

            //zyp 设置图标大小2019-5-22 晚
            //此处李航上面写的content，如要修改，需统一
            activityPlace = view.findViewById(R.id.activityPlace);
            activityTime = view.findViewById(R.id.activityTime);
            activitySexLimit = view.findViewById(R.id.activitySexLimit);

            Drawable place = getResources().getDrawable(R.drawable.place);
            Drawable time = getResources().getDrawable(R.drawable.time);
            Drawable sex = getResources().getDrawable(R.drawable.sex);

            place.setBounds(0,0,32,35);
            time.setBounds(0,0,32,32);
            sex.setBounds(0,0,32,32);
            activityPlace.setCompoundDrawables(place,null,null,null);
            activityTime.setCompoundDrawables(time,null,null,null);
            activitySexLimit.setCompoundDrawables(sex,null,null,null);
            //------------over------------------
            return view;
        }

    }
}
