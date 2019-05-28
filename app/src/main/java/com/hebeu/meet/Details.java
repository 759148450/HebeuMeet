package com.hebeu.meet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.domain.ActivityJoinUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;



/*活动详情页
* Vanilla
* 5-24
*
* lihang
* 5-27
* 绑定“查看申请信息”按钮功能
**/

public class Details extends AppCompatActivity {
    private TextView activity_title=null;
    private TextView activity_place=null;
    private TextView activity_time=null;
    private TextView activity_sexLimit=null;
    private TextView activity_PeopleLimit=null;
    private TextView activity_qq=null;
    private TextView activity_phone=null;
    private TextView activity_content=null;
    private TextView activity_user_name=null;
    private TextView activity_user_class=null;
    private LinearLayout applying = null;
    private LinearLayout apply_success = null;
    private LinearLayout apply_fail = null;
    private LinearLayout create_user = null;
    private LinearLayout apply_join = null;
    private int activityId;
    private int length;
    private ImageView sexImage = null;
    private Handler handler = null;
    private Button apply_join_btn=null;
    private Button show_apply = null;//查看申请信息按钮
    private Button show_contact = null;//查看联系方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        show_contact = findViewById(R.id.show_contact);
        activity_title=findViewById(R.id.activity_title);
        activity_place=findViewById(R.id.activity_place);
        activity_time=findViewById(R.id.activity_time);
        activity_sexLimit=findViewById(R.id.activity_sexLimit);
        activity_PeopleLimit=findViewById(R.id.activity_PeopleLimit);
        activity_qq=findViewById(R.id.activity_qq);
        activity_phone=findViewById(R.id.activity_phone);
        activity_content=findViewById(R.id.activity_content);
        activity_user_name=findViewById(R.id.activity_user_name);
        activity_user_class=findViewById(R.id.activity_user_class);
        /*LinearLayout*/
        create_user=findViewById(R.id.create_user);
        applying = findViewById(R.id.applying);
        apply_success = findViewById(R.id.apply_success);
        apply_fail = findViewById(R.id.apply_fail);
        apply_join = findViewById(R.id.apply_join);
        /*button*/
       apply_join_btn=findViewById(R.id.apply_join_btn);
        show_apply = findViewById(R.id.show_apply);
        handler = new Handler();
        sexImage = findViewById(R.id.sex);

        //-------------zyp-设置图标大小----------------------
        Drawable place = getResources().getDrawable(R.drawable.place);
        Drawable time = getResources().getDrawable(R.drawable.time);
        Drawable sex = getResources().getDrawable(R.drawable.sex);
        Drawable personnum = getResources().getDrawable(R.drawable.personnum);
        Drawable details_qq = getResources().getDrawable(R.drawable.details_qq);
        Drawable phone = getResources().getDrawable(R.drawable.phone);

        place.setBounds(0,0,32,35);
        time.setBounds(0,0,32,32);
        sex.setBounds(0,0,32,32);
        personnum.setBounds(0,0,32,32);
        details_qq.setBounds(0,0,32,32);
        phone.setBounds(0,0,32,32);

        activity_place.setCompoundDrawables(place,null,null,null);
        activity_time.setCompoundDrawables(time,null,null,null);
        activity_sexLimit.setCompoundDrawables(sex,null,null,null);
        activity_PeopleLimit.setCompoundDrawables(personnum,null,null,null);
        activity_qq.setCompoundDrawables(details_qq,null,null,null);
        activity_phone.setCompoundDrawables(phone,null,null,null);


        // ---------------end---------------------------------

        /*--------*/

        MyThread thread=new MyThread();
        thread.start();

        show_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转到申请列表
                MyThread2 myThread2 = new MyThread2();
                myThread2.start();


            }
        });
        apply_join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = getIntent().getExtras();
                int activity_id = b.getInt("activity_id");
                Intent intent = new Intent(Details.this.getApplicationContext(), ApplyJoin.class);
                intent.putExtra("activity_id",activity_id);
                startActivity(intent);
            }

        });
        show_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread3 mythread3 = new MyThread3();
                mythread3.start();
            }
        });
    }

    class MyThread extends Thread {
        public void run() {

            /*Vanilla*/
            Bundle b = getIntent().getExtras();
            int activity_id = b.getInt("activity_id");
            activityId = activity_id;
            final String activity_title1 = b.getString("activity_title");
            final String activity_place1=b.getString("activity_place");
           /* final String activity_time1=b.getString("activity_time");*/
            final String activity_sexLimit1=b.getString("activity_sexLimit");
            final String activity_PeopleLimit1=b.getString("activity_PeopleLimit");
            final String activity_qq1=b.getString("activity_qq");
            final String activity_phone1=b.getString("activity_phone");
            final String activity_content1=b.getString("activity_content");
            final String activity_user_id1=b.getString("activity_user_id");
            final String activity_user_name1=b.getString("activity_user_name");
            final String activity_user_class1=b.getString("activity_user_class");
            final Integer user_sex=b.getInt("user_sex");
            final String join_state1=b.getString("join_state");
            final String join_id1=b.getString("join_id");

           /* String res = HttpUtil.get("http://112.74.194.121:8889/activity/getActivityById?activityId=");
            final Activity activity = JSONUtil.toBean(res, Activity.class);

            System.out.println(activity.toString());*/

            handler.post(new Runnable() {
                @Override
                public void run() {
                    activity_title.setText(activity_title1);
                    activity_place.setText(activity_place1);
//                    activity_time1
                    if(activity_sexLimit1.equals("0")){
                        activity_sexLimit.setText("活动性别限制：男");
                    }else{
                        activity_sexLimit.setText("活动性别限制：女");
                    }

                    switch (user_sex){
                        case 0:
                            sexImage.setImageDrawable(getResources().getDrawable(R.drawable.man));
                            break;
                        case 1:
                            sexImage.setImageDrawable(getResources().getDrawable(R.drawable.woman));
                            break;
                    }

                    activity_PeopleLimit.setText(activity_PeopleLimit1);


                    activity_content.setText(activity_content1);
                    activity_user_name.setText(activity_user_name1);
                    activity_user_class.setText(activity_user_class1);

                    /*获取登录的用户信息*/
                    SharedPreferences sharedPre = getSharedPreferences("config", MODE_PRIVATE);
                    String userId=sharedPre.getString("userId", "");

                    /*判断是否为活动发布者*/
                    System.out.println("活动发布者id"+activity_user_id1);
                    System.out.println("参加者id："+join_id1);
                    System.out.println("参加者状态："+join_state1);
//                    apply_join.setVisibility(View.GONE);
                    if(userId.equals(activity_user_id1)){
                        activity_qq.setText(activity_qq1);
                        activity_phone.setText(activity_phone1);
                        applying.setVisibility(View.GONE);
                        apply_success.setVisibility(View.GONE);
                        apply_fail.setVisibility(View.GONE);
                        apply_join.setVisibility(View.GONE);

                    }
                    else {
                        if (join_id1!=null&&join_id1.equals(userId)) {

//                            判断申请者的状态
                            System.out.println("");
                            if (join_state1.equals("0")) {
                                //正在申请
                                create_user.setVisibility(View.GONE);
                                apply_fail.setVisibility(View.GONE);
                                apply_success.setVisibility(View.GONE);
                                apply_join.setVisibility(View.GONE);

                            } else if (join_state1.equals("1")) {
                                //申请成功
                                  activity_qq.setText(activity_qq1);
                                  activity_phone.setText(activity_phone1);
                                create_user.setVisibility(View.GONE);
                                apply_fail.setVisibility(View.GONE);
                                applying.setVisibility(View.GONE);
                                apply_join.setVisibility(View.GONE);


                            } else  if(join_state1.equals("2")){
                                //申请失败
                                create_user.setVisibility(View.GONE);
                                applying.setVisibility(View.GONE);
                                apply_success.setVisibility(View.GONE);
                                apply_join.setVisibility(View.GONE);

                            }
                        } else {
                            create_user.setVisibility(View.GONE);
                            applying.setVisibility(View.GONE);
                            apply_success.setVisibility(View.GONE);
                            apply_fail.setVisibility(View.GONE);
                        }
                    }


                }
            });
        }
    }

    class MyThread2 extends Thread{
        public void run(){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("activityId",activityId);
            String res =HttpUtil.get("http://112.74.194.121:8889/userActivity/selectActivityJoinUserByActivityId",paramMap);
            JSONArray array = JSONUtil.parseArray(res);
            length = (JSONUtil.toList(array, ActivityJoinUser.class)).size();//获取申请列表长度
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(length>0){
                        Intent intent = new Intent(Details.this, Others_Apply_Activity.class);
                        Bundle c = new Bundle();
                        c.putInt("activity_id",activityId);
                        intent.putExtras(c);
                        startActivity(intent);
                    }
                    else{
                        Toast toast = Toast.makeText(Details.this,"暂无人申请",Toast.LENGTH_SHORT);
                        toast.show();//显示消息
                    }
                }
            });
        }
    }

    class MyThread3 extends Thread{
        public void run(){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("activityId",activityId);
            String res =HttpUtil.get("http://112.74.194.121:8889/userActivity/selectActivityJoinUserByActivityId",paramMap);
            JSONArray array = JSONUtil.parseArray(res);
            List<ActivityJoinUser> list = JSONUtil.toList(array, ActivityJoinUser.class);
            for(ActivityJoinUser item : list){
                if(item.getJoinState().equals("2")){
                    length++;
                }
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(length >0){
                        Intent intent = new Intent(Details.this, ContactList.class);
                        Bundle c = new Bundle();
                        c.putInt("activity_id",activityId);
                        intent.putExtras(c);
                        startActivity(intent);
                    }
                    else{
                        Toast toast = Toast.makeText(Details.this,"暂无人参加",Toast.LENGTH_SHORT);
                        toast.show();//显示消息
                    }
                }
            });
        }
    }
}
