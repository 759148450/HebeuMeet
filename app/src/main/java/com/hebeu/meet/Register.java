package com.hebeu.meet;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.domain.JSONResult;
import com.hebeu.meet.domain.User;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.egl.EGLDisplay;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import static com.hebeu.meet.tools.ImageHandler.bitmapToString;
import static com.hebeu.meet.tools.ImageHandler.compressScale;

/*
 * 注册
 * Vanilla
 * 5-22
 * */
public class Register extends AppCompatActivity {
    private EditText user_id =null;
    private EditText user_name =null;
    private Integer user_sex =null;
    private EditText user_college =null;
    private EditText user_key=null;
    private EditText user_classname =null;
    private EditText user_qq =null;
    private EditText user_phone =null;
    private EditText user_email =null;
    private EditText user_password=null;
    private EditText email_key=null;
    private Button btn_register = null;
    private Handler handler = null;
    private String email=null,res1,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*隐藏标题栏
         * */
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
        handler = new Handler();
        System.out.println("register>>>>>");
        user_id=findViewById(R.id.user_id);

        user_password=findViewById(R.id.user_password_mess);
        user_name=findViewById(R.id.user_name_mess);
//        user_sex=findViewById(R.id.user_sex);
        user_college=findViewById(R.id.user_college_mess);
        user_phone=findViewById(R.id.user_phone_mess);
        user_classname=findViewById(R.id.user_classname_mess);
        user_qq=findViewById(R.id.user_qq_mess);
        user_email=findViewById(R.id.user_email_mess);
        btn_register=findViewById(R.id.btn_register);
        user_key=findViewById(R.id.email_key);

        System.out.println("register.................................");
        Button imagemale=findViewById(R.id.imagemale);
        imagemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_sex=0;
                System.out.println("已选性别为男"+user_sex);
            }
        });
        Button imagefalme=findViewById(R.id.imagefamle);
        imagefalme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_sex=1;
                System.out.println("已选性别为女"+user_sex);
            }
        });
        Button btn_email_key=findViewById(R.id.btn_email);
        btn_email_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("邮箱点击事件发生，先获取验证码");
                handler = new Handler();
                MyThread_email thread_pwd = new MyThread_email();
                thread_pwd.start();
            }
            class MyThread_email extends Thread{
                @Override
                public void run(){
                    email = user_email.getEditableText().toString();
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("email", email);
                    Looper.prepare();
                    if("".equals(email)){
                        Toast.makeText(Register.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        return;
                    }
                    System.out.println(email);
                    String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserByEmail?email="+email);
                    System.out.println("通过email找user"+res);
                    try{
                        final User user = JSONUtil.toBean(res, User.class);
                        Toast.makeText(Register.this,"该邮箱已被注册",Toast.LENGTH_SHORT).show();
                        System.out.println("邮箱已注册");
                        Looper.loop();
                        return;
                    }catch (Exception e){
                        res1 = HttpUtil.get("http://112.74.194.121:8889/email/sendMail", paramMap);
                        Toast.makeText(Register.this,"验证码已发送请到邮箱查验",Toast.LENGTH_SHORT).show();
                        System.out.println(res1);
                        Looper.loop();

                    }

                }
            }

        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id.getText().toString().trim().equals("")){

                    Toast.makeText(Register.this,"学号不能为空！！！",Toast.LENGTH_SHORT).show();

                    return;
                }if(user_password.getText().toString().trim().equals("")){

                    Toast.makeText(Register.this,"密码不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }if(user_name.getText().toString().trim().equals("")){

                    Toast.makeText(Register.this,"用户名不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }if(user_classname.getText().toString().trim().equals("")){

                    Toast.makeText(Register.this,"班级不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }if(user_college.getText().toString().trim().equals("")){

                    Toast.makeText(Register.this,"学院不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }if(user_phone.getText().toString().trim().equals("")){

                    Toast.makeText(Register.this,"电话不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }if(user_qq.getText().toString().trim().equals("")){

                    Toast.makeText(Register.this,"qq不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }if(user_email.getText().toString().trim().equals("")){

                    Toast.makeText(Register.this,"邮箱不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }if(user_key.getText().toString().trim().equals("")){
                    Toast.makeText(Register.this,"验证码不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    MyThread thread = new MyThread();
                    thread.start();
                }
            }

            class MyThread extends Thread{
                @Override
                public void run() {
                    email = user_email.getEditableText().toString();
                    String pwd=user_password.getEditableText().toString();
                    String id=user_id.getEditableText().toString();
                    System.out.println(email);
                    User new_user = new User();
                    new_user.setUserId(String.valueOf(user_id.getEditableText()));
                    System.out.println(user_id.getText());
                    new_user.setPassword(String.valueOf(user_password.getEditableText()));
                    System.out.println(user_password.getText());
                    new_user.setUserName(String.valueOf(user_name.getEditableText()));
                    System.out.println(user_name.getText());
                    new_user.setSex(user_sex);
                    System.out.println(user_sex);
                    new_user.setCollege(String.valueOf(user_college.getEditableText()));
                    new_user.setClassName(String.valueOf(user_classname.getEditableText()));
                    new_user.setQq(String.valueOf(user_qq.getEditableText()));
                    new_user.setPhone(String.valueOf(user_phone.getEditableText()));
                    new_user.setEmail(String.valueOf(user_email.getEditableText()));
                    Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.my_img);
                    Bitmap bms =  compressScale(bmp);//压缩
                    new_user.setHead(bitmapToString(bms));
                    System.out.println(user_email.getText()+"邮箱");
                    System.out.println(user_key.getText()+"激活码");
                    String code=user_key.getEditableText().toString();
                    Map<String,Object> paramMap = BeanUtil.beanToMap(new_user);
                    String res = "";
                    JSONResult jsonResult;
                    Looper.prepare();
                    try{
                        email = user_email.getEditableText().toString();
                        System.out.println("try");
                        if ("".equals(id)){
                            System.out.println(user_email);
                            Toast.makeText(Register.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            return;
                            //弹出对话框

                        }else if ("".equals(email)){
                            Toast.makeText(Register.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            return;
                        }else if("".equals(pwd)){
                            Toast.makeText(Register.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            return;
                        }
                        else if (!code.equals(res1)) {
                            System.out.println(user_email);
                            System.out.println("激活码"+code);
                            Toast.makeText(Register.this,"邮箱验证码错误！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            return;
                        }else {
                            res = HttpUtil.post("http://112.74.194.121:8889/user/insertUser",paramMap);
                            //                           jsonResult = JSONUtil.toBean(res,JSONResult.class);
                            Toast.makeText(Register.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this.getApplicationContext(), Login.class);
                            startActivity(intent);
                            Looper.loop();
                        }
//                        else{
//                            Toast.makeText(Register.this,"注册失败！",Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                            return;
//                        }
//            //            System.out.println("jsonResult"+jsonResult);
                    }catch (Exception e){

                        Toast.makeText(Register.this,"用户已存在",Toast.LENGTH_SHORT).show();
//
                        Looper.loop();

                    }
                    Looper.loop();



                }
            }

        });
    }

}

