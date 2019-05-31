package com.hebeu.meet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.UI.CircleImageView;
import com.hebeu.meet.domain.ActivityJoinUser;
import com.hebeu.meet.domain.UserActivity;
import com.hebeu.meet.tools.ImageHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import static com.hebeu.meet.tools.ImageHandler.stringToBitmap;



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
    private ImageView sexImage = null;
    private int activityId;
    private Handler handler = null;
    private Button apply_join_btn=null;
    private Button show_apply = null;//查看申请信息按钮
    private Button show_contact = null;//查看联系方式
    private ImageView Details_Publisher_head=null;
    private HorizontalScrollView horizontalScrollView = null;
    private LinearLayout container = null;
    private CircleImageView imageView = null;
    List<ActivityJoinUser> activityJoinUserList=null;

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
        imageView = findViewById(R.id.imageView);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        container = (LinearLayout) findViewById(R.id.horizontalScrollViewItemContainer);




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

        public UserActivity user_activity=null;
        public void run() {

            /*Vanilla*/
            /*获取登录的用户信息*/
            SharedPreferences sharedPre = getSharedPreferences("config", MODE_PRIVATE);
            final String userId=sharedPre.getString("userId", "");
            final Bundle b = getIntent().getExtras();
            final int activity_id = b.getInt("activity_id");
            activityId = activity_id;
            final String activity_title1 = b.getString("activity_title");
            final String activity_place1=b.getString("activity_place");
            final String activity_time1=b.getString("activity_time");
            final String activity_sexLimit1=b.getString("activity_sexLimit");
            final String activity_PeopleLimit1=b.getString("activity_PeopleLimit");
            final String activity_qq1=b.getString("activity_qq");
            final String activity_phone1=b.getString("activity_phone");
            final String activity_content1=b.getString("activity_content");
            final String activity_user_id1=b.getString("activity_user_id");
            final String activity_user_name1=b.getString("activity_user_name");
            final String activity_user_class1=b.getString("activity_user_class");
            final String activity_user_head=b.getString("activity_user_head");
            final Integer user_sex=b.getInt("user_sex");
            final String join_state1=b.getString("join_state");
            final String join_id1=b.getString("join_id");
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("activityId",activity_id);
            paramMap.put("userId",userId);
            System.out.println("activityId"+activity_id);
            System.out.println("userID"+userId);

            try {
                final String res = HttpUtil.get("http://112.74.194.121:8889/userActivity/getUserActivityByActivityIdAndUserId",paramMap);
//                System.out.println("res"+res.toString());
                user_activity= JSONUtil.toBean(res, UserActivity.class);
//                System.out.println(user_activity.toString());
            }catch (Exception e){
                System.out.println("空");
            }

            try {
                System.out.println("当前活动id = " + activityId);
                Map<String,Object> paramMap1 = new HashMap<>();
                paramMap1.put("activityId",activityId);
                paramMap1.put("joinState","2");
                String res1 = HttpUtil.get("http://112.74.194.121:8889/userActivity/selectActivityJoinUserByActivityIdAndJoinState",paramMap1);

                JSONArray jsonArray = JSONUtil.parseArray(res1);
                activityJoinUserList = JSONUtil.toList(jsonArray,ActivityJoinUser.class);
                System.out.println("成功申请者数量为"+activityJoinUserList.size());
            }catch (Exception e){
                System.out.println("查询申请者失败");
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    //若用户头像信息不为空，则设置为用户自定义头像
                    if(activity_user_head != null){
                        imageView.setImageBitmap(ImageHandler.stringToBitmap(activity_user_head));
                    }else {
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.my_img));
                    }
                    activity_title.setText(activity_title1);
                    activity_place.setText(activity_place1);
                   activity_time.setText(activity_time1);
                    if(activity_sexLimit1.equals("0")){
                        activity_sexLimit.setText("活动性别限制：男");
                    }else if(activity_sexLimit1.equals("1")){
                        activity_sexLimit.setText("活动性别限制：女");
                    }else{
                        activity_sexLimit.setText("不限男女");
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
                        if (user_activity!=null) {
//                            判断申请者的状态
                            System.out.println("");
                            if (user_activity.getJoinState().equals("1")) {
                                //正在申请
                                create_user.setVisibility(View.GONE);
                                apply_fail.setVisibility(View.GONE);
                                apply_success.setVisibility(View.GONE);
                                apply_join.setVisibility(View.GONE);
                            } else if (user_activity.getJoinState().equals("2")) {
                                //申请成功显示qq和phone
                                activity_qq.setText(activity_qq1);
                                activity_phone.setText(activity_phone1);
                                create_user.setVisibility(View.GONE);
                                apply_fail.setVisibility(View.GONE);
                                applying.setVisibility(View.GONE);
                                apply_join.setVisibility(View.GONE);
                            } else  if(user_activity.getJoinState().equals("3")){
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
                    //加载参加者头像
                    for(ActivityJoinUser activityJoinUser:activityJoinUserList){
                        ImageView imageView = new ImageView(Details.this.getApplicationContext());
                        imageView.setImageBitmap(stringToBitmap(activityJoinUser.getHead()));
                        container.addView(imageView);
                        container.invalidate();
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
            final ArrayList<ActivityJoinUser>  activityJoinUserList = (ArrayList<ActivityJoinUser>) JSONUtil.toList(array, ActivityJoinUser.class);//获取申请列表长度
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(activityJoinUserList.size()>0){
                        Intent intent = new Intent();
                        intent.setClass(Details.this, Others_Apply_Activity.class);
                        intent.putExtra("list",(Serializable)activityJoinUserList);
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
            final ArrayList<ActivityJoinUser> list = (ArrayList<ActivityJoinUser>) JSONUtil.toList(array, ActivityJoinUser.class);
            int length = 0;
            for(ActivityJoinUser item : list){
                if(item.getJoinState().equals("2")){
                    length++;
                }
            }
            final int finalLength = length;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(finalLength >0){
                        Intent intent = new Intent();
                        intent.setClass(Details.this, ContactList.class);
                        intent.putExtra("list2", list);
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

    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createCircleImage(ChangeSize(bitmap));
    }
    //缩放图片大小
    public Bitmap ChangeSize(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
        int newWidth = 140;
        int newHeight = 140;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
    public Bitmap createCircleImage(Bitmap source)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(160, 160, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        int radius =  source.getWidth()>source.getHeight()?source.getHeight()/2:source.getWidth()/2;
        canvas.drawCircle(source.getWidth()/2, source.getHeight()/2, radius, paint);//圆心的横坐标，纵坐标，圆的半径
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
