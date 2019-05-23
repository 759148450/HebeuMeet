package com.hebeu.meet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hebeu.meet.domain.User;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/*查看个人信息
* Vanilla
* 5-21
*
* */
public class My_Information extends AppCompatActivity {
    private TextView user_id =null;
    private TextView user_name =null;
    private TextView user_sex =null;
    private TextView user_college =null;
    private TextView user_classname =null;
    private TextView user_qq =null;
    private TextView user_phone =null;
    private TextView user_email =null;
    private Button btn_update_user = null;

    //zyp 修改图标大小
    private TextView user_id_mess =null;
    private TextView user_name_mess =null;
    private TextView user_sex_mess =null;
    private TextView user_college_mess =null;
    private TextView user_classname_mess =null;
    private TextView user_qq_mess =null;
    private TextView user_phone_mess =null;
    private TextView user_email_mess =null;
    //-------------
    /*读取的文件的字段SharedPreferences */
    private String userId;
    private String password;

    private Handler handler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_information);
        /*组件名*/
        user_name = findViewById(R.id.user_name);
        handler = new Handler();
        MyThread thread = new MyThread();
        thread.start();
        System.out.println("my_information");

        btn_update_user=findViewById(R.id.btn_update_user);
        btn_update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_Information.this, Update_My_Information.class);
                startActivity(intent);
            }
       });

        //zyp 设置图标大小2019-5-22上午
        user_id_mess = findViewById(R.id.user_id_mess);
        user_name_mess = findViewById(R.id.user_name_mess);
        user_sex_mess = findViewById(R.id.user_sex_mess);
        user_college_mess = findViewById(R.id.user_college_mess);
        user_classname_mess = findViewById(R.id.user_classname_mess);
        user_qq_mess = findViewById(R.id.user_qq_mess);
        user_phone_mess = findViewById(R.id.user_phone_mess);
        user_email_mess = findViewById(R.id.user_email_mess);
        Drawable update = getResources().getDrawable(R.drawable.update);
        Drawable user_id = getResources().getDrawable(R.drawable.user_id);
        Drawable user_name = getResources().getDrawable(R.drawable.user_name);
        Drawable user_sex = getResources().getDrawable(R.drawable.user_sex);
        Drawable user_college = getResources().getDrawable(R.drawable.user_college);
        Drawable user_classname = getResources().getDrawable(R.drawable.user_classname);
        Drawable user_qq = getResources().getDrawable(R.drawable.user_qq);
        Drawable user_phone = getResources().getDrawable(R.drawable.user_phone);
        Drawable user_email = getResources().getDrawable(R.drawable.user_email);
        update.setBounds(0,0,50,50);
        user_id.setBounds(0,0,50,50);
        user_name.setBounds(0,0,40,40);
        user_sex.setBounds(0,0,40,40);
        user_college.setBounds(0,0,40,40);
        user_classname.setBounds(0,0,40,40);
        user_qq.setBounds(0,0,40,40);
        user_phone.setBounds(0,0,40,40);
        user_email.setBounds(0,0,40,40);
        btn_update_user.setCompoundDrawables(null,update,null,null);
        user_id_mess.setCompoundDrawables(user_id,null,null,null);
        user_name_mess.setCompoundDrawables(user_name,null,null,null);
        user_sex_mess.setCompoundDrawables(user_sex,null,null,null);
        user_college_mess.setCompoundDrawables(user_college,null,null,null);
        user_classname_mess.setCompoundDrawables(user_classname,null,null,null);
        user_qq_mess.setCompoundDrawables(user_qq,null,null,null);
        user_phone_mess.setCompoundDrawables(user_phone,null,null,null);
        user_email_mess.setCompoundDrawables(user_email,null,null,null);
        //----------------------------
    }
    class MyThread extends Thread {
        public void run() {
            user_id = findViewById(R.id.user_id);
            user_name = findViewById(R.id.user_name);
            user_sex = findViewById(R.id.user_sex);
            user_college = findViewById(R.id.user_college);
            user_classname = findViewById(R.id.user_classname);
            user_qq = findViewById(R.id.user_qq);
            user_phone = findViewById(R.id.user_phone);
            user_email = findViewById(R.id.user_email);
            /*获取用户信息*/
            SharedPreferences sharedPre = getSharedPreferences("config", MODE_PRIVATE);

                userId=sharedPre.getString("userId", "");
                password=sharedPre.getString("password", "");
                System.out.println("用户id:"+userId+"用户密码"+password);

            /*---------------------------------------------------------------------------------*/
            String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserById?userId="+userId);
            final   User user = JSONUtil.toBean(res, User.class);

            System.out.println(user.toString());

            handler.post(new Runnable() {
                @Override
                public void run() {
                    user_id.setText(user.getUserId());
                    user_name.setText(user.getUserName());
                    user_sex.setText(user.getSex().toString());
                    user_college.setText(user.getCollege());
                    user_classname.setText(user.getClassName());
                    user_qq.setText(user.getQq());
                    user_phone.setText(user.getPhone());
                    user_email.setText(user.getEmail());
                }
            });
        }
    }




}
