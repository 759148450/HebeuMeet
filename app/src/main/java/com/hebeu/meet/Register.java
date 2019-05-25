package com.hebeu.meet;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.domain.JSONResult;
import com.hebeu.meet.domain.User;

import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
/*
* 注册
* Vanilla
* 5-22
* */
public class Register extends AppCompatActivity {
    private TextView user_id =null;
    private TextView user_name =null;
    private Integer user_sex =null;
    private TextView user_college =null;
    private TextView user_classname =null;
    private TextView user_qq =null;
    private TextView user_phone =null;
    private TextView user_email =null;
    private TextView user_password=null;
    private Button btn_register = null;
    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*隐藏标题栏
         * */
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar  即全屏



        setContentView(R.layout.register);
        handler = new Handler();
        System.out.println("register>>>>>");
        user_id=findViewById(R.id.user_id);
        user_password=findViewById(R.id.user_password);
        user_name=findViewById(R.id.user_name);
//        user_sex=findViewById(R.id.user_sex);
        user_college=findViewById(R.id.user_college);
        user_classname=findViewById(R.id.user_classname);
        user_qq=findViewById(R.id.user_qq);
        user_phone=findViewById(R.id.user_phone);
        user_email=findViewById(R.id.user_email);
        btn_register=findViewById(R.id.btn_register);
        Button imagemale=findViewById(R.id.imagemale);
        imagemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_sex=1;
                System.out.println(user_sex);
            }
        });
        Button imagefalme=findViewById(R.id.imagefamle);
        imagefalme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_sex=1;
                System.out.println(user_sex);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread thread = new MyThread();
                thread.start();
            }

            class MyThread extends Thread{
                @Override
                public void run() {
                    User new_user = new User();
                    System.out.println(123333333);
                    System.out.println(String.valueOf(user_id.getText()));
                    new_user.setUserId(String.valueOf(user_id.getText()));
                    new_user.setPassword(String.valueOf(user_password.getText()));
                    new_user.setUserName(String.valueOf(user_name.getText()));
                    new_user.setSex(user_sex);
//                    new_user.setSex(Integer.parseInt(String.valueOf(user_sex.getText())));
                    new_user.setCollege(String.valueOf(user_college.getText()));
                    new_user.setClassName(String.valueOf(user_classname.getText()));
                    new_user.setQq(String.valueOf(user_qq.getText()));
                    new_user.setPhone(String.valueOf(user_phone.getText()));
                    new_user.setEmail(String.valueOf(user_email.getText()));
                    Map<String,Object> paramMap = BeanUtil.beanToMap(new_user);
                    String res = "";
                    JSONResult jsonResult;
                    Looper.prepare();
                    try{
                        System.out.println("try");
                        res = HttpUtil.post("http://112.74.194.121:8889/user/insertUser",paramMap);
                        jsonResult = JSONUtil.toBean(res,JSONResult.class);
                        System.out.println("jsonResult.getCode()"+jsonResult.getCode());
                        if (jsonResult.getCode() == 0){
                            //弹出对话框
                            Toast.makeText(Register.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this.getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(Register.this,"注册失败！",Toast.LENGTH_SHORT).show();
                        }
                        System.out.println("jsonResult"+jsonResult);
                    }catch (Exception e){
//                        Looper.prepare();
                        Toast.makeText(Register.this,"用户已存在",Toast.LENGTH_SHORT).show();
//
//                        Looper.loop();

                    }
                        Looper.loop();



                }
            }

        });
    }

}

