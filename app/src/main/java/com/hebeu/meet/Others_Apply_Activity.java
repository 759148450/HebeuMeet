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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hebeu.meet.domain.Activity;
import com.hebeu.meet.domain.ActivityJoinUser;
import com.hebeu.meet.domain.JSONResult;
import com.hebeu.meet.domain.User;
import com.hebeu.meet.domain.UserActivity;
import com.hebeu.meet.domain.UserActivityView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import static com.hebeu.meet.tools.ImageHandler.stringToBitmap;

/**
 * 2019.5.21
 * 别人申请我的活动的列表
 * 李航
 */
public class Others_Apply_Activity extends AppCompatActivity {
    private Handler handler = null;
    private ListView listView = null;
    private List<ActivityJoinUser> activityJoinUserList = null;
    private List<UserActivityView> userActivityViewList = null;
    private JSONResult jsonResult = null;
    private TextView Empty = null;

   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_apply_activity);
        listView = findViewById(R.id.OthersApply);
        Empty = findViewById(R.id.othersApplyIsEmpty);
        handler = new Handler();
        activityJoinUserList = new ArrayList<>();
        userActivityViewList = new ArrayList<>();
        jsonResult = new JSONResult();
        MyThread myThread = new MyThread();
        myThread.start();

    }

    class MyThread extends Thread {
        public void run() {
            Bundle c = getIntent().getExtras();
            int activityId = c.getInt("activity_id");
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("activityId",activityId);
            //根据activityId从activity-user表中查出关于本活动的信息
            String res = HttpUtil.get("http://112.74.194.121:8889/userActivity/selectActivityJoinUserByActivityId",paramMap);

            JSONArray array = JSONUtil.parseArray(res);
            activityJoinUserList = JSONUtil.toList(array, ActivityJoinUser.class);
            //判断有没有人申请这个活动
                for(ActivityJoinUser item : activityJoinUserList){

                    //显示信息用的
                    UserActivityView userActivityView = new UserActivityView();
                    userActivityView.setUserName(item.getUserName());
                    userActivityView.setClassName(item.getClassName());
                    userActivityView.setWords(item.getWords());
                    userActivityView.setUserId(item.getUserId());
                    userActivityView.setUserActivityId(item.getId());
                    userActivityView.setJoinState(item.getJoinState());
                    userActivityView.setHead(item.getHead());
                    userActivityView.setSex(item.getSex());
                    userActivityViewList.add(userActivityView);
                }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(activityJoinUserList.size()==0){//没人申请，隐藏申请列表，显示无人申请
                        Empty.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.INVISIBLE);
                    }else{
                        Others_Apply_Activity.MyBaseAdapter baseAdapter = new Others_Apply_Activity.MyBaseAdapter();
                        //把数组适配器加载到ListView控件中
                        listView.setAdapter(baseAdapter);
                    }

                }
            });
        }
    }
    class MyThread2 extends Thread{
       private UserActivityView u ;
       private String flag ;

       MyThread2(String flag,UserActivityView u){
            this.flag = flag;
            this.u = u;
       }

        public void run() {
            UserActivity userActivity = new UserActivity();
            userActivity.setId(u.getUserActivityId());
            userActivity.setJoinState(flag);
            Map<String,Object> paramMap = BeanUtil.beanToMap(userActivity);

            String res = HttpUtil.post("http://112.74.194.121:8889/userActivity/updateUserActivity",paramMap);
            jsonResult = JSONUtil.toBean(res,JSONResult.class);

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
            LayoutInflater inflater = Others_Apply_Activity.this.getLayoutInflater();
            view = inflater.inflate(R.layout.others_apply_activity_item, null);
            final UserActivityView u = userActivityViewList.get(position);//通过回调这个方法传过来的position参数获取到指定数据源中的对象
            final TextView name = view.findViewById(R.id.mc);
            TextView classname = view.findViewById(R.id.bj);
            TextView describe = view.findViewById(R.id.bz);
            ImageView PublisherHead = view.findViewById(R.id.Publisher_head);
            final Button btn_accpte = view.findViewById(R.id.tysq);
            final Button btn_refuse = view.findViewById(R.id.jjsq);
            final TextView ress = view.findViewById(R.id.othersRes);
            ImageView SexImage = view.findViewById(R.id.sex);
            final View line1 = view.findViewById(R.id.line1);
            final View line2 = view.findViewById(R.id.line2);


            //绑定发布人头像
            PublisherHead.setImageBitmap(stringToBitmap(u.getHead()));
            //绑定性别图片
            if(u.getSex() == 0){
                SexImage.setImageResource(R.drawable.man);
                SexImage.setLayoutParams(new LinearLayout.LayoutParams(200,
                        200));//控制图表大小
            }else{
                SexImage.setImageResource(R.drawable.woman);
                SexImage.setLayoutParams(new LinearLayout.LayoutParams(200,
                        200));
            }

            name.setText("姓名："+u.getUserName());
            classname.setText("专业班级："+u.getClassName());
            describe.setText("备注："+u.getWords());
            //加载申请状态
            if(u.getJoinState().equals("2")){
                ress.setText("已同意");
                ress.setVisibility(View.VISIBLE);
                btn_accpte.setVisibility(View.INVISIBLE);
                btn_refuse.setVisibility(View.INVISIBLE);
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);

            }
            if(u.getJoinState().equals("3")){
                ress.setText("已拒绝");
                ress.setVisibility(View.VISIBLE);
                btn_accpte.setVisibility(View.INVISIBLE);
                btn_refuse.setVisibility(View.INVISIBLE);
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
            }

            //同意按钮
            btn_accpte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   MyThread2 myThread2 = new MyThread2("2",u);
                   myThread2.start();
                    if ((jsonResult.getCode() == 0)){
                        System.out.println("运⾏行行成功");
                        ress.setText("已同意");
                        ress.setVisibility(View.VISIBLE);
                        btn_accpte.setVisibility(View.INVISIBLE);
                        btn_refuse.setVisibility(View.INVISIBLE);
                        line1.setVisibility(View.INVISIBLE);
                        line2.setVisibility(View.INVISIBLE);
                    } else{
                        System.out.println("运⾏行行失败");   }
                }
            });

            //拒绝按钮
            btn_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyThread2 myThread2 = new MyThread2("3",u);
                    myThread2.start();
                    if ((jsonResult.getCode() == 0)){
                        System.out.println("运⾏行行成功");
                        ress.setText("已拒绝");
                        ress.setVisibility(View.VISIBLE);
                        btn_accpte.setVisibility(View.INVISIBLE);
                        btn_refuse.setVisibility(View.INVISIBLE);
                        line1.setVisibility(View.INVISIBLE);
                        line2.setVisibility(View.INVISIBLE);
                    } else{
                        System.out.println("运⾏行行失败");   }
                }
            });

            return view;
        }

    }
}
