package com.hebeu.meet.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.Details;
import com.hebeu.meet.MyApplyActivity;
import com.hebeu.meet.R;

import com.hebeu.meet.UI.CircleImageView;
import com.hebeu.meet.domain.Activity;
import com.hebeu.meet.domain.ActivityCreateUser;
import com.hebeu.meet.domain.ActivityJoinUser;
import com.hebeu.meet.domain.JSONResult;


import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import static android.content.Context.MODE_PRIVATE;


/**
 * yuechunpeng
 */
public class ActivityAdapter extends ArrayAdapter {


    private List<ActivityCreateUser> activityCreateUserList;

    private final int resourceId;
    public ActivityAdapter(Context context, List<ActivityCreateUser> activityCreateUserList){
        super(context,R.layout.my_publish_activity_item,activityCreateUserList);

        this.resourceId = R.layout.my_publish_activity_item;
        this.activityCreateUserList = activityCreateUserList;
    }

    private class ViewHolder{
        //zyp 修改图标大小 2019-5-22
        private TextView activityPlace =null;
        private TextView activityTime =null;
        private TextView activitySexLimit =null;
        //    -------------------
        private TextView activityTitle = null;
        private TextView activityUser2 = null;
        private TextView activityUser = null;
        private ImageView sexImage = null;

        private Button button = null;
        private Handler handler = null;

        private HorizontalScrollView horizontalScrollView = null;
        private LinearLayout container = null;
        private Button btn_details = null;
        private LinearLayout details_ly = null;
    }


    @Override
    public View getView(int position,  View view,  ViewGroup parent) {

        ViewHolder viewHolder = null;

        final ActivityCreateUser activityCreateUser =(ActivityCreateUser) getItem(position);


        if (null == view){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);

            viewHolder = new ViewHolder();

            viewHolder.btn_details=view.findViewById(R.id.btn_details);//详情按钮
            viewHolder.details_ly=view.findViewById(R.id.details_ly);//整个布局
            viewHolder.activityPlace = view.findViewById(R.id.activityPlace);
            viewHolder.activityTime = view.findViewById(R.id.activityTime);
            viewHolder.activitySexLimit = view.findViewById(R.id.activitySexLimit);

            Drawable place = getContext().getResources().getDrawable(R.drawable.place);
            Drawable time = getContext().getResources().getDrawable(R.drawable.time);
            Drawable sex = getContext().getResources().getDrawable(R.drawable.sex);

            place.setBounds(0,0,32,35);
            time.setBounds(0,0,32,32);
            sex.setBounds(0,0,32,32);
            viewHolder.activityPlace.setCompoundDrawables(place,null,null,null);
            viewHolder.activityTime.setCompoundDrawables(time,null,null,null);
            viewHolder.activitySexLimit.setCompoundDrawables(sex,null,null,null);

            viewHolder.activityTitle = view.findViewById(R.id.activityTitle);
            viewHolder.activityUser2 = view.findViewById(R.id.activityUser2);
            viewHolder.activityUser = view.findViewById(R.id.activityUser);
            viewHolder.btn_details = view.findViewById(R.id.btn_details);
            viewHolder.sexImage = view.findViewById(R.id.sex);

            viewHolder.activityTitle.setText(activityCreateUser.getTitle());
            viewHolder.activityPlace.setText(activityCreateUser.getActivityPlace());


            if(activityCreateUser.getActivityDate() != null){
                viewHolder.activityTime.setText(activityCreateUser.getActivityDate().toString());
            }else {
                viewHolder.activityTime.setText("2019-6-1");
            }
            //活动性别要求
            switch (activityCreateUser.getSexLimit()){
                case 0 :viewHolder.activitySexLimit.setText("男"); break;
                case 1 :viewHolder.activitySexLimit.setText("女"); break;
                case 2 :viewHolder.activitySexLimit.setText("不限男女"); break;
            }

            switch (activityCreateUser.getSex()){
                case 0:
                    viewHolder.sexImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.man));
                    break;
                case 1:
                    viewHolder.sexImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.woman));
                    break;
            }

            viewHolder.activityUser2.setText(activityCreateUser.getClassName());
            viewHolder.activityUser.setText(activityCreateUser.getUserName());

            viewHolder.horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);
            viewHolder.container = (LinearLayout) view.findViewById(R.id.horizontalScrollViewItemContainer);
            viewHolder.handler = new Handler();
            MyThread myThread = new MyThread(activityCreateUser.getActivityId(),viewHolder);
            myThread.start();

            //监听器
            viewHolder.details_ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Details.class);
                    Bundle b = new Bundle();

                    b.putInt("activity_id", activityCreateUser.getActivityId());
                    b.putString("activity_title", activityCreateUser.getTitle());
                    b.putString("activity_place",activityCreateUser.getActivityPlace());
                    /*b.putString("activity_time",activityCreateUser.);*///时间
                    b.putString("activity_sexLimit",activityCreateUser.getSexLimit().toString());
                    b.putString("activity_PeopleLimit",activityCreateUser.getPeopleLimit().toString());
                    b.putString("activity_qq",activityCreateUser.getQq());
                    b.putString("activity_phone",activityCreateUser.getPhone());
                    b.putString("activity_content",activityCreateUser.getActivityContent());
                    b.putString("activity_user_id",activityCreateUser.getUserId());//发布人
                    b.putString("activity_user_name",activityCreateUser.getUserName());
                    b.putString("activity_user_class",activityCreateUser.getClassName());//发布者专业班级
                    b.putString("join_state",activityCreateUser.getJoinState());
                    b.putString("join_id", activityCreateUser.getJoin_id());//参加者id
                    intent.putExtras(b);
                    getContext().startActivity(intent);
                }
            });
            viewHolder.btn_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Details.class);
                    Bundle b = new Bundle();

                    b.putInt("activity_id", activityCreateUser.getActivityId());
                    b.putString("activity_title", activityCreateUser.getTitle());
                    b.putString("activity_place",activityCreateUser.getActivityPlace());
                    /*b.putString("activity_time",activityCreateUser.);*///时间
                    b.putString("activity_sexLimit",activityCreateUser.getSexLimit().toString());
                    b.putString("activity_PeopleLimit",activityCreateUser.getPeopleLimit().toString());
                    b.putString("activity_qq",activityCreateUser.getQq());
                    b.putString("activity_phone",activityCreateUser.getPhone());
                    b.putString("activity_content",activityCreateUser.getActivityContent());
                    b.putString("activity_user_id",activityCreateUser.getUserId());//发布人
                    b.putString("activity_user_name",activityCreateUser.getUserName());
                    b.putString("activity_user_class",activityCreateUser.getClassName());//发布者专业班级
                    b.putString("join_state",activityCreateUser.getJoinState());
                    b.putString("join_id", activityCreateUser.getJoin_id());//参加者id
                    b.putInt("user_sex",activityCreateUser.getSex());
                    intent.putExtras(b);
                    getContext().startActivity(intent);

                }
            });
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }




         return view;
    }

    class MyThread extends Thread{
        Integer activityId;
        ViewHolder viewHolder;
        public MyThread(Integer activityId,ViewHolder viewHolder){
            this.activityId = activityId;
            this.viewHolder = viewHolder;
        }
        @Override
        public void run() {

            Looper.prepare();
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("activityId",activityId);
            paramMap.put("joinState",2);
            System.out.println("activityId : " + activityId);
            String res = HttpUtil.get("http://112.74.194.121:8889/userActivity/selectActivityJoinUserByActivityIdAndJoinState",paramMap);
            JSONArray jsonArray = JSONUtil.parseArray(res);
            final List<ActivityJoinUser> activityJoinUserList = JSONUtil.toList(jsonArray,ActivityJoinUser.class);

            System.out.println("数据大小为"+activityJoinUserList.size());

            Integer myImgids[] = new Integer[]{R.drawable.man,R.drawable.woman,R.drawable.button_login,R.drawable.button_state,R.drawable.details_style,R.drawable.edittextshape};
            final ArrayList<Integer> data = new ArrayList<>();
            Collections.addAll(data,myImgids);



            viewHolder.handler.post(new Runnable() {
                @Override
                public void run() {
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams.gravity = Gravity.CENTER;
//                    layoutParams.setMargins(20, 10, 20, 10);

                    for(ActivityJoinUser activityJoinUser:activityJoinUserList){
//                        CircleImageView circleImageView = new CircleImageView(getContext());
//                        circleImageView.setImageBitmap(stringToBitmap(activityJoinUser.getHead()));
//                        circleImageView.setLayoutParams(layoutParams);

                        ImageView imageView = new ImageView(getContext());
                        imageView.setImageBitmap(stringToBitmap(activityJoinUser.getHead()));
//                        imageView.setLayoutParams(layoutParams);

                        viewHolder.container.addView(imageView);
//                        container.addView(circleImageView);
                        viewHolder.container.invalidate();
                    }
                }
            });



            Looper.loop();

        }


    }

    @Override
    public int getCount() {
        return activityCreateUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return null != activityCreateUserList?activityCreateUserList.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        return createCircleImage(bitmap);
    }

    public Bitmap createCircleImage(Bitmap source)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(50, 50, 50, paint);
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
