package com.hebeu.meet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hebeu.meet.UI.CircleImageView;
import com.hebeu.meet.domain.ActivityCreateUser;
import com.hebeu.meet.tools.ImageHandler;

import java.util.List;

/**
 * 2019.5.22
 * 我参加的活动列表
 * 李航
 */
public class MyApplyActivity extends AppCompatActivity {

    private ListView listView = null;
    private List<ActivityCreateUser> activityCreateUserList = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_apply);
        listView = findViewById(R.id.myApplyList);
        activityCreateUserList = (List<ActivityCreateUser>) getIntent().getSerializableExtra("list3");
        MyApplyActivity.MyBaseAdapter baseAdapter = new MyApplyActivity.MyBaseAdapter();
        //把数组适配器加载到ListView控件中
        listView.setAdapter(baseAdapter);

    }


    class MyBaseAdapter extends BaseAdapter {
        /**
         * 列表适配器
         * @return
         */
        //获取当前items项的大小，也可以看成是数据源的大小
        @Override
        public int getCount() {
            return activityCreateUserList.size();
        }
        //根据item的下标获取到View对象
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return activityCreateUserList.get(position);
        }
        //获取到items的id
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            LayoutInflater inflater = MyApplyActivity.this.getLayoutInflater();
            view = inflater.inflate(R.layout.my_apply_item, null);


            ActivityCreateUser u = activityCreateUserList.get(position);//通过回调这个方法传过来的position参数获取到指定数据源中的对象

            TextView Plimit = view.findViewById(R.id.acPeopleLimit);
            TextView Slimit = view.findViewById(R.id.acSexLimit);
            TextView place = view.findViewById(R.id.acPlace);
            TextView state = view.findViewById(R.id.acState);
            TextView time = view.findViewById(R.id.acTime);
            TextView title = view.findViewById(R.id.acTitle);
            TextView words = view.findViewById(R.id.acWords);

            CircleImageView imageView = (CircleImageView)view.findViewById(R.id.imageView);


            LinearLayout applying = view.findViewById(R.id.applying);
            LinearLayout apply_success = view.findViewById(R.id.apply_success);
            LinearLayout apply_fail = view.findViewById(R.id.apply_fail);

            /*Vanilla 5-24*/
            Button btn_details=view.findViewById(R.id.btn_details);//详情按钮
            LinearLayout ly=view.findViewById(R.id.ly);//整个布局

            btn_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplyActivity.this, Details.class);
                    Bundle b = new Bundle();

                    b.putInt("activity_id", activityCreateUserList.get(position).getActivityId());
                    b.putString("activity_title", activityCreateUserList.get(position).getTitle());
                    b.putString("activity_place",activityCreateUserList.get(position).getActivityPlace());
                    b.putString("activity_time",activityCreateUserList.get(position).getActivityDate());//时间
                    b.putString("activity_sexLimit",activityCreateUserList.get(position).getSexLimit().toString());
                    b.putString("activity_PeopleLimit",activityCreateUserList.get(position).getPeopleLimit().toString());
                    b.putString("activity_qq",activityCreateUserList.get(position).getQq());
                    b.putString("activity_phone",activityCreateUserList.get(position).getPhone());
                    b.putString("activity_content",activityCreateUserList.get(position).getActivityContent());
                    b.putString("activity_user_id",activityCreateUserList.get(position).getUserId());
                    b.putString("activity_user_name",activityCreateUserList.get(position).getUserName());
                    b.putString("activity_user_class",activityCreateUserList.get(position).getClassName());//发布者专业班级
                    b.putString("join_state", activityCreateUserList.get(position).getJoinState());
                    b.putString("activity_user_head",activityCreateUserList.get(position).getHead());
//                    b.putString("join_id", activityCreateUserList.get(position).getJoin_id());//参加者id

                    intent.putExtras(b);
                    startActivity(intent);

                }
            });
            ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplyActivity.this, Details.class);
                    Bundle b = new Bundle();

                    b.putInt("activity_id", activityCreateUserList.get(position).getActivityId());
                    b.putString("activity_title", activityCreateUserList.get(position).getTitle());
                    b.putString("activity_place",activityCreateUserList.get(position).getActivityPlace());
                    b.putString("activity_time",activityCreateUserList.get(position).getActivityDate());//时间
                    b.putString("activity_sexLimit",activityCreateUserList.get(position).getSexLimit().toString());
                    b.putString("activity_PeopleLimit",activityCreateUserList.get(position).getPeopleLimit().toString());
                    b.putString("activity_qq",activityCreateUserList.get(position).getQq());
                    b.putString("activity_phone",activityCreateUserList.get(position).getPhone());
                    b.putString("activity_content",activityCreateUserList.get(position).getActivityContent());
                    b.putString("activity_user_id",activityCreateUserList.get(position).getUserId());
                    b.putString("activity_user_name",activityCreateUserList.get(position).getUserName());
                    b.putString("activity_user_class",activityCreateUserList.get(position).getClassName());//发布者专业班级
                    b.putString("join_state",activityCreateUserList.get(position).getJoinState());
                    b.putString("activity_user_head",activityCreateUserList.get(position).getHead());
//                    b.putString("join_id", activityCreateUserList.get(position).getJoin_id());//参加者id
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
            /*-----end 5-25-----*/
            title.setText("活动标题："+u.getTitle());
            time.setText("活动时间" + u.getActivityDate());
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
            words.setText("留言： "+u.getWords());
            if (u.getJoinState().equals("1")){
                //正在申请

                apply_fail.setVisibility(View.GONE);
                apply_success.setVisibility(View.GONE);

            }else if (u.getJoinState().equals("2")){
                //申请成功

                apply_fail.setVisibility(View.GONE);
                applying.setVisibility(View.GONE);

            }else if (u.getJoinState().equals("3")){
                //申请失败

                applying.setVisibility(View.GONE);
                apply_success.setVisibility(View.GONE);

            }

            if(u.getHead() != null){
                System.out.println("activityId为"+u.getActivityId());
                imageView.setImageBitmap(ImageHandler.stringToBitmap(u.getHead()));
            }else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.my_img));
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
