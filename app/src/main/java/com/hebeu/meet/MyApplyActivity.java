package com.hebeu.meet;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hebeu.meet.domain.Activity;
import com.hebeu.meet.domain.ActivityCreateUser;
import com.hebeu.meet.domain.ActivityJoinUser;
import com.hebeu.meet.domain.User;
import com.hebeu.meet.domain.UserActivity;
import com.hebeu.meet.domain.UserActivityView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * 2019.5.22
 * 我申请的活动列表
 * 李航
 */
public class MyApplyActivity extends AppCompatActivity {

    private ListView listView = null;
    private Handler handler = null;
    private List<ActivityJoinUser> userActivityList = null;
    private List<UserActivityView> userActivityViewList= null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_apply);
        listView = findViewById(R.id.myApplyList);
        handler = new Handler();
        userActivityList = new ArrayList<>();
        userActivityViewList = new ArrayList<>();

        MyThread thread = new MyThread();
        thread.start();
    }

    class MyThread extends Thread{

        public void run(){
            String userId = "160210405";
            String res = HttpUtil.get("http://112.74.194.121:8889/userActivity/selectActivityJoinUserByUserId?userId="+userId);//根据userId从activity-user表中查出关于user的所有信息
            JSONArray array = JSONUtil.parseArray(res);
            userActivityList = JSONUtil.toList(array, ActivityJoinUser.class);
            System.out.println(userActivityList.toString());
            int activityId;
            for(ActivityJoinUser item : userActivityList){
                activityId = item.getActivityId();
                Map<String,Object> paramMap = new HashMap<>();
                paramMap.put("activityId",activityId);
                String res2 = HttpUtil.get("http://112.74.194.121:8889/activity/getActivityCreateUserById",paramMap);//根据activityId从activity表中查出关于本条活动的所有信息
                final ActivityCreateUser activity = JSONUtil.toBean(res2, ActivityCreateUser.class);
                UserActivityView userActivityView = new UserActivityView();
                userActivityView.setActivityContent(activity.getActivityContent());//活动内容
//                userActivityView.setActivityDate(activity.getActivityDate());//活动日期
                userActivityView.setActivityId(activity.getActivityId());//活动id
                userActivityView.setActivityPlace(activity.getActivityPlace());//活动地点
                userActivityView.setApplyState(activity.getApplyState());//活动状态（是否取消或者结束）
                userActivityView.setPeopleLimit(activity.getPeopleLimit());//活动人数限制
                userActivityView.setSexLimit(activity.getSexLimit());//活动性别限制
                userActivityView.setJoinState(item.getJoinState());//加入状态（是否被拒绝）
                userActivityView.setWords(item.getWords());//留言
                userActivityView.setTitle(activity.getTitle());//活动标题
                userActivityViewList.add(userActivityView);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    MyApplyActivity.MyBaseAdapter baseAdapter = new MyApplyActivity.MyBaseAdapter();
                    //把数组适配器加载到ListView控件中
                    listView.setAdapter(baseAdapter);
                }
            });

        }
    }

    class MyBaseAdapter extends BaseAdapter {
        /**
         * 列表适配器
         * @return
         */
        //获取当前items项的大小，也可以看成是数据源的大小
        @Override
        public int getCount() {
            return userActivityViewList.size();
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
            LayoutInflater inflater = MyApplyActivity.this.getLayoutInflater();
            view = inflater.inflate(R.layout.my_apply_item, null);
            UserActivityView u = userActivityViewList.get(position);//通过回调这个方法传过来的position参数获取到指定数据源中的对象
            TextView Plimit = view.findViewById(R.id.acPeopleLimit);
            TextView Slimit = view.findViewById(R.id.acSexLimit);
            TextView place = view.findViewById(R.id.acPlace);
            TextView state = view.findViewById(R.id.acState);
            TextView time = view.findViewById(R.id.acTime);
            TextView title = view.findViewById(R.id.acTitle);
            TextView words = view.findViewById(R.id.acWords);

//            TextView status = view.findViewById(R.id.applyStatus);
//            TextView status2 = view.findViewById(R.id.applyStatus2);
//            TextView status3 = view.findViewById(R.id.applyStatus3);

            LinearLayout applying = view.findViewById(R.id.applying);
            LinearLayout apply_success = view.findViewById(R.id.apply_success);
            LinearLayout apply_fail = view.findViewById(R.id.apply_fail);


            title.setText("活动标题："+u.getTitle());
            Plimit.setText("活动人数限制："+u.getPeopleLimit().toString());
            if(u.getSexLimit().equals("0")){
                Slimit.setText("活动性别限制：男");
            }else{
                Slimit.setText("活动性别限制：女");
            }
            place.setText("活动地点："+u.getActivityPlace());



            switch (u.getApplyState()){
                case "0":{
                    u.setApplyState("正在进行");break;

                }
                case "1":{
                    u.setApplyState("活动结束");break;
                }
                case "2":{
                    u.setApplyState("活动取消");break;
                }
            }
            state.setText("活动进展："+u.getApplyState());
//            DateFormat bf = new SimpleDateFormat("yyyy-MM-dd E a HH:mm:ss");//创建格式化工具
//            String format = bf.format(u.getActivityDate());//格式化 bf.format(date);
//            time.setText(format);
            words.setText("留言： "+u.getWords());
//            switch (u.getJoinState()){
//                case "1":u.setJoinState("正在申请");break;
//                case "3":u.setJoinState("拒绝申请");break;
//                case "2":u.setJoinState("申请通过");break;
//            }

            if (u.getJoinState().equals("0")){
                //正在申请

                apply_fail.setVisibility(View.GONE);
                apply_success.setVisibility(View.GONE);

            }else if (u.getJoinState().equals("1")){
                //申请成功

                apply_fail.setVisibility(View.GONE);
                applying.setVisibility(View.GONE);

            }else if (u.getJoinState().equals("2")){
                //申请失败

                applying.setVisibility(View.GONE);
                apply_success.setVisibility(View.GONE);

            }

            // zyp 设置图标大小  2019-5-23上午----------------

            Drawable place_img = getResources().getDrawable(R.drawable.place);
            Drawable time_img = getResources().getDrawable(R.drawable.time);
            Drawable sex_img = getResources().getDrawable(R.drawable.sex);

            place_img.setBounds(0,0,32,35);
            time_img.setBounds(0,0,32,32);
            sex_img.setBounds(0,0,32,32);

            place.setCompoundDrawables(place_img,null,null,null);
            time.setCompoundDrawables(time_img,null,null,null);
            Slimit.setCompoundDrawables(sex_img,null,null,null);

            //-------------end--------------------------------

            return view;
        }

    }
}
