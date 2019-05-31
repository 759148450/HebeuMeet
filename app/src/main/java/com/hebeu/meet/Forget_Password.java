package com.hebeu.meet;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.hebeu.meet.domain.JSONResult;
import com.hebeu.meet.domain.User;

import java.util.HashMap;
import java.util.Map;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/*
 * 忘记密码
 * tzzzzzzzzzzzzzzzzzz
 * 5-28
 * */
public class Forget_Password extends AppCompatActivity {
    private EditText et_email, et_newpwd, et_newpwd1, et_code;
    private String email, newpwd, newpwd1, code,res1,res;
    private Button btn_forget_password;
    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);//启动返回上一级按钮
        handler = new Handler();

        et_email = findViewById(R.id.et_email);
        et_newpwd = findViewById(R.id.et_newpwd);
        et_newpwd1 = findViewById(R.id.et_newpwd1);
        et_code = findViewById(R.id.et_code);

        btn_forget_password = findViewById(R.id.btn_forget_password);
        Button btn_code=findViewById(R.id.btn_code);
        handler = new Handler();


        btn_code.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                handler = new Handler();
                MyThread thread = new MyThread();
                thread.start();
            }
            class MyThread extends Thread {
                @Override
                public void run() {
                    email = et_email.getEditableText().toString();
                    code = et_code.getEditableText().toString();
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("email", email);
                    System.out.println(email+"邮箱的值");
                    res1 = HttpUtil.get("http://112.74.194.121:8889/email/sendMail", paramMap);
                    System.out.println(res1);
                    res = HttpUtil.get("http://112.74.194.121:8889/user/getUserByEmail?email="+email);
                    System.out.println("通过email找user"+res);
                    Looper.prepare();
                   try{
                       final User user = JSONUtil.toBean(res, User.class);
                   }catch (Exception e){
                       System.out.println("邮箱未被注册");
                       Toast.makeText(Forget_Password.this, "邮箱未被注册", Toast.LENGTH_SHORT).show();
                       Looper.loop();
                       return;
                      // Forget_Password.this.finish();
                   }
//
//                    if (user == null) {
//                        Toast.makeText(Forget_Password.this, "该邮箱未注册，请重新注册", Toast.LENGTH_SHORT).show();
//
//                    } else if (TextUtils.isEmpty(code)) {
//                        Toast.makeText(Forget_Password.this, "查看邮箱输入邮箱激活码", Toast.LENGTH_SHORT).show();
//
//                    }
                    Looper.loop();
                }

            }
        });

        btn_forget_password.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("点击确认");
//                handler = new Handler();
                MyThread_Pwd thread_pwd = new MyThread_Pwd();
                thread_pwd.start();
            }
            class MyThread_Pwd extends Thread {
                @Override
                public void run() {
                    System.out.println("进入重置...");
                    Looper.prepare();
                    String email = et_email.getEditableText().toString();
                     newpwd = et_newpwd.getEditableText().toString();
                     newpwd1 = et_newpwd1.getEditableText().toString();
                    code = et_code.getEditableText().toString();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(Forget_Password.this, "输入注册邮箱", Toast.LENGTH_SHORT).show();
                        System.out.println("33333333333333333333333333333333333333333");
                        Looper.loop();
                        return;
                    } else if (TextUtils.isEmpty(newpwd)) {
                        Toast.makeText(Forget_Password.this, "输入重置密码", Toast.LENGTH_SHORT).show();
                        System.out.println("重置密码");
                        Looper.loop();
                        return;
                    } else if (TextUtils.isEmpty(newpwd1)) {
                        Toast.makeText(Forget_Password.this, "再次确认密码", Toast.LENGTH_SHORT).show();
                        System.out.println("确认密码");
                        Looper.loop();
                        return;
                    } else if (!newpwd.equals(newpwd1)) {
                        Toast.makeText(Forget_Password.this, "两次密码不一样", Toast.LENGTH_SHORT).show();
                        System.out.println("密码不统一");
                        Looper.loop();
                        return;
                    }else if(code==null){
                        Toast.makeText(Forget_Password.this, "激活码输入有误", Toast.LENGTH_SHORT).show();
                        System.out.println("激活码为空");
                        Looper.loop();
                        return;
                    }
                    else if (!code.equals(res1)) {
                        System.out.println(code+"键盘输入");
                        System.out.println(res1+"后端激活码");

                        Toast.makeText(Forget_Password.this, "激活码输入有误", Toast.LENGTH_SHORT).show();
                        System.out.println("激活码不对");
                        return;
                    } else {
                        System.out.println("开始重置...");

                        String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserByEmail?email=" + email);
                        final User user1 = JSONUtil.toBean(res, User.class);
                        User user = new User();
                        user.setUserId(user1.getUserId());
                        user.setPassword(String.valueOf(et_newpwd1.getEditableText()));
                        Map<String,Object>paramMap= BeanUtil.beanToMap(user);
                        String result=HttpUtil.post("http://112.74.194.121:8889/user/updateUser",paramMap);
                        JSONResult jsonResult = JSONUtil.toBean(result,JSONResult.class);
                        if(jsonResult.getCode()==0){
                            Toast.makeText(Forget_Password.this, "重置成功", Toast.LENGTH_SHORT).show();
                            System.out.println(jsonResult.getCode());
                            System.out.println("succeed!");
                        }else{
                            Toast.makeText(Forget_Password.this, "重置失败", Toast.LENGTH_SHORT).show();
                        }
//                        System.out.println("1111111111111111111111111111111111");
//                        String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserByEmail?email=" + email);
//                        final User user = JSONUtil.toBean(res, User.class);
//                        System.out.println(user.getPassword());
//                        user.setPassword(String.valueOf(et_newpwd1.getEditableText()));
                       // Forget_Password.this.finish();
                        Intent intent = new Intent(Forget_Password.this, Login.class);
                        startActivity(intent);
                    }
                    Looper.loop();

                }
            }
        });

//
//                    Intent data=new Intent();
//                    String ema= HttpUtil.get("http://112.74.194.121:8889/user/getUserByEmail?userEmail=" + email);
//                    final User user = JSONUtil.toBean(res, User.class);
//                    data.putExtra("password",newpwd1);
//                    setResult(RESULT_OK,data);
//                    Forget_Password.this.finish();
//
//                }
//            }
//        });
    }
//    class MyThread extends Thread
//    {
//        @Override
//        public void run() {
//            email = et_email.getEditableText().toString();
//            code = et_code.getEditableText().toString();
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("email", email);
//            System.out.println(email);
//            String res1 = HttpUtil.get("http://112.74.194.121:8889/email/sendMail", paramMap);
//            String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserByEmail?userEmail=" + email);
//            final User user = JSONUtil.toBean(res, User.class);
//            Looper.prepare();
//            if (user == null) {
//                Toast.makeText(Forget_Password.this, "改邮箱未注册，请重新注册", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (TextUtils.isEmpty(code)) {
//                Toast.makeText(Forget_Password.this, "查看邮箱输入邮箱激活码", Toast.LENGTH_SHORT).show();
//                return;
//            } else if (!code.equals(res1)) {
//                Toast.makeText(Forget_Password.this, "激活码输入有误", Toast.LENGTH_SHORT).show();
//                return;
//            } else {
//                Toast.makeText(Forget_Password.this, "激活码匹配成功", Toast.LENGTH_SHORT).show();
//            }
//            email = et_email.getEditableText().toString();
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("email", email);
//            System.out.println(email);
//            String res1 = HttpUtil.get("http://112.74.194.121:8889/email/sendMail" ,paramMap);
//             }else()
       ///     Looper.loop();




//       public static void savePwdInfo(Context context, User user,String newpwd1) {
//        String res=user
//        // 获取SharedPreferences对象
//        SharedPreferences sharedPre = context.getSharedPreferences("config", Context.MODE_PRIVATE);
//        // 获取Editor对象
//        SharedPreferences.Editor editor = sharedPre.edit();
//        // 设置参数
//        editor.putString("userId", user.getUserId());
//        editor.putString("password",newpwd1);
//        editor.putString("username",user.getUserName());
//        editor.putString("sex",user.getSex().toString());
//        editor.putString("college",user.getCollege());
//        editor.putString("classname",user.getClassName());
//        editor.putString("qq",user.getQq());
//        editor.putString("phone",user.getPhone());
//        editor.putString("email",user.getEmail());
//        // 提交
//        editor.commit();

}



