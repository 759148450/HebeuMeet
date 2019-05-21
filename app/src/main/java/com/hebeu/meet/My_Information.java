package com.hebeu.meet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hebeu.meet.domain.User;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

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

    private Handler handler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_information);
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
            String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserById?userId=160210405");
            final User user = JSONUtil.toBean(res, User.class);

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
