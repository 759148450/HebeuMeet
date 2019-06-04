package com.hebeu.meet;

import android.content.Intent;
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
import android.widget.Toast;

import com.hebeu.meet.domain.ActivityJoinUser;
import com.hebeu.meet.domain.JSONResult;
import com.hebeu.meet.domain.UserActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import static com.hebeu.meet.tools.ImageHandler.stringToBitmap;

/**
 * 2019.5.21
 * 别人申请我的活动的列表
 * 李航
 */
public class Others_Apply_Activity extends AppCompatActivity {
    private ListView listView = null;
    private List<ActivityJoinUser> activityJoinUserList = null;
    private JSONResult jsonResult = null;
   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_apply_activity);
//        super.onResume();
        listView = findViewById(R.id.OthersApply);
        activityJoinUserList = (ArrayList<ActivityJoinUser>) getIntent().getSerializableExtra("list");
        jsonResult = new JSONResult();
        Others_Apply_Activity.MyBaseAdapter myBaseAdapter = new Others_Apply_Activity.MyBaseAdapter();
        listView.setAdapter(myBaseAdapter);

    }

    class MyThread2 extends Thread{
       private ActivityJoinUser u ;
       private String flag ;

       MyThread2(String flag,ActivityJoinUser u){
            this.flag = flag;
            this.u = u;
       }

        public void run() {
            UserActivity userActivity = new UserActivity();
            userActivity.setId(u.getId());
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
            return activityJoinUserList.size();
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
            final ActivityJoinUser u = activityJoinUserList.get(position);//通过回调这个方法传过来的position参数获取到指定数据源中的对象
            final TextView name = view.findViewById(R.id.mc);
            TextView classname = view.findViewById(R.id.bj);
            TextView describe = view.findViewById(R.id.bz);
            ImageView PublisherHead = view.findViewById(R.id.Publisher_head);
            final Button btn_accpte = view.findViewById(R.id.tysq);
            final Button btn_refuse = view.findViewById(R.id.jjsq);
            final TextView successed = view.findViewById(R.id.success);
            final TextView failed = view.findViewById(R.id.failed);
            ImageView SexImage = view.findViewById(R.id.sex);
            final LinearLayout apply_state = view.findViewById(R.id.apply_state);//zyp-2019-5-28
            final LinearLayout apply_success = view.findViewById(R.id.apply_success);//zyp-2019-5-28
            final LinearLayout apply_fail = view.findViewById(R.id.apply_fail);//zyp-2019-5-28


            //绑定发布人头像
            PublisherHead.setImageBitmap(stringToBitmap(u.getHead()));
            //绑定性别图片
            if(u.getSex() == 0){
                SexImage.setImageResource(R.drawable.man);
            }else{
                SexImage.setImageResource(R.drawable.woman);
            }

            name.setText("姓名："+u.getUserName());
            classname.setText("专业班级："+u.getClassName());
            describe.setText("备注："+u.getWords());
            //未审核--zyp
            if(u.getJoinState().equals("1")){
                apply_success.setVisibility(View.GONE);
                apply_fail.setVisibility(View.GONE);
            }
            //加载申请状态
            else if(u.getJoinState().equals("2")){
                apply_state.setVisibility(View.GONE);//zyp
                apply_fail.setVisibility(View.GONE);
            }
            else if(u.getJoinState().equals("3")){
                apply_state.setVisibility(View.GONE);//zyp 不显示未决策状态
                apply_success.setVisibility(View.GONE);
            }

            //同意按钮
            btn_accpte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   MyThread2 myThread2 = new MyThread2("2",u);
                   myThread2.start();
                    if ((jsonResult.getCode() == 0)){
                        System.out.println("运⾏行行成功");
                        apply_state.setVisibility(View.GONE);//zyp 不显示未决策状态
                        successed.setVisibility(View.VISIBLE);
                        apply_success.setVisibility(View.VISIBLE);
                        successed.setText("已同意");
                        Toast.makeText(Others_Apply_Activity.this,"恭喜你，发布人同意了！！！",Toast.LENGTH_SHORT).show();
//                        refresh();
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
                        apply_state.setVisibility(View.GONE);//zyp 不显示未决策状态
                        failed.setVisibility(View.VISIBLE);
                        apply_fail.setVisibility(View.VISIBLE);
                        failed.setText("已拒绝");
                        Toast.makeText(Others_Apply_Activity.this,"发布人拒绝了您的请求！！！",Toast.LENGTH_SHORT).show();
//                        refresh();
                    } else{
                        System.out.println("运⾏行行失败");   }

                }
            });

            return view;
        }
    }
//    private void refresh() {
//        Intent intent = new Intent(Others_Apply_Activity.this, Others_Apply_Activity.class);
//        startActivity(intent);
//    }

}
