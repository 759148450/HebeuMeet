package com.hebeu.meet;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
    private Button btn_register = null;
    private Handler handler = null;
    private String email,res1,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*隐藏标题栏
         * */
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
        handler = new Handler();
        System.out.println("register>>>>>");
        user_id=findViewById(R.id.user_id);
        System.out.println(user_id+"00000000");
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
                user_sex=1;
                System.out.println(user_sex);
            }
        });
        Button imagefalme=findViewById(R.id.imagefamle);
        imagefalme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_sex=2;
                System.out.println(user_sex);
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
                    System.out.println(email);
                    String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserByEmail?email="+email);
                    System.out.println("通过email找user"+res);
                    Looper.prepare();
                    try{
                        final User user = JSONUtil.toBean(res, User.class);
                        Toast.makeText(Register.this,"该邮箱已被注册",Toast.LENGTH_SHORT).show();
                        System.out.println("用户已注册");
                        Looper.loop();
                        return;
                    }catch (Exception e){
                        res1 = HttpUtil.get("http://112.74.194.121:8889/email/sendMail", paramMap);
                        System.out.println(res1);
                    }

                }
            }

        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(Register.this)
                            .setTitle("警告").setMessage("学号不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(user_password.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(Register.this)
                            .setTitle("警告").setMessage("密码不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(user_name.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(Register.this)
                            .setTitle("警告").setMessage("用户名不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(user_classname.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(Register.this)
                            .setTitle("警告").setMessage("班级不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(user_college.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(Register.this)
                            .setTitle("警告").setMessage("学院不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(user_phone.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(Register.this)
                            .setTitle("警告").setMessage("电话不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(user_qq.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(Register.this)
                            .setTitle("警告").setMessage("qq不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
                    return;
                }if(user_email.getText().toString().trim().equals("")){
                    new AlertDialog.Builder(Register.this)
                            .setTitle("警告").setMessage("邮箱不可为空，请重新输入！！！")
                            .setPositiveButton("确定", null).show();
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
                  //  key=user_key.getEditableText().toString();
                    User new_user = new User();
                    new_user.setUserId(String.valueOf(user_id.getEditableText()));
                    System.out.println(user_id.getText());
                    new_user.setPassword(String.valueOf(user_password.getEditableText()));
                    System.out.println(user_password.getText());
                    new_user.setUserName(String.valueOf(user_name.getEditableText()));
                    System.out.println(user_name.getText());
                    new_user.setSex(user_sex);
                    System.out.println(user_sex);
//                   new_user.setSex(Integer.parseInt(String.valueOf(user_sex.getText())));
                    new_user.setCollege(String.valueOf(user_college.getEditableText()));
//                    System.out.println(user_college.getText());
                    new_user.setClassName(String.valueOf(user_classname.getEditableText()));
                    new_user.setQq(String.valueOf(user_qq.getEditableText()));
                    new_user.setPhone(String.valueOf(user_phone.getEditableText()));
                    new_user.setEmail(String.valueOf(user_email.getEditableText()));
                    Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.my_img);
                    Bitmap bms =  compressScale(bmp);//压缩
                    new_user.setHead(bitmapToString(bms));
                    System.out.println(user_key.getText()+"激活码");
                    String code=user_key.getEditableText().toString();
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
                            Intent intent = new Intent(Register.this.getApplicationContext(), Login.class);
                            startActivity(intent);

                        }else if (!code.equals(res1)) {
                            Toast.makeText(Register.this,"邮箱验证码错误！",Toast.LENGTH_SHORT).show();
                            return;
                        } else{
                            Toast.makeText(Register.this,"注册失败！",Toast.LENGTH_SHORT).show();
                        }
            //            System.out.println("jsonResult"+jsonResult);
                    }catch (Exception e){
//                        Looper.prepare();
                        Toast.makeText(Register.this,"用户已存在",Toast.LENGTH_SHORT).show();
//
                        Looper.loop();

                    }
                    Looper.loop();



                }
            }

        });
    }
  public Boolean Warning(TextView tv){
      if(tv.getText().toString().trim().equals("")){
          new AlertDialog.Builder(Register.this)
                  .setTitle("警告").setMessage("输入框不可为空，请重新输入！！！")
                  .setPositiveButton("确定", null).show();
          return true;
      }
      else{
          return false;
      }
  }

}

