package com.hebeu.meet;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.hebeu.meet.domain.JSONResult;
import com.hebeu.meet.domain.User;


import java.util.HashMap;
import java.util.Map;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/*用户登陆
 * Vanilla
 * 5-22晚
 * */
public class Login extends AppCompatActivity {
    private TextView user_id =null;
    private TextView user_password=null;
    private Button btn_login=null;
    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        handler = new Handler();
        System.out.println("login>>>>>");

        user_id=findViewById(R.id.user_id);
        user_password=findViewById(R.id.user_password);
        btn_login=findViewById(R.id.btn_login);
        System.out.println("btn_login>>>>>>");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id.getText().toString().trim().equals("")||user_password.getText().toString().trim().equals("")){
                    Toast.makeText(Login.this,"学号和密码不可为空，请重新输入！！！",Toast.LENGTH_SHORT).show();
                    return;
                }
               else{
                    MyThread thread = new MyThread();
                    thread.start();
                }
            }
            class MyThread extends Thread{
                @Override
                public void run() {
                    Looper.prepare();


                    System.out.println(123333333);
                    System.out.println(String.valueOf(user_id.getText()));
                    String my_user_id=String.valueOf(user_id.getText());
                    String my_user_password=String.valueOf(user_password.getText());


                    System.out.println("try");
                    Map<String,Object> paramMap = new HashMap<>();
                    paramMap.put("userId",my_user_id);
                    paramMap.put("password",my_user_password);
                    try{
                        String res = HttpUtil.post("http://112.74.194.121:8889/user/login",paramMap);
                        System.out.println("res"+res);
                        final JSONResult jsonResult = JSONUtil.toBean(res,JSONResult.class);
                        System.out.println("jsonResult.getCode()"+jsonResult.getCode());
                        if (jsonResult.getCode() == 0){
                            String res_id = HttpUtil.get("http://112.74.194.121:8889/user/getUserById?userId="+my_user_id);
                            final User user = JSONUtil.toBean(res_id, User.class);
                            System.out.println("User"+user.toString());
                            saveLoginInfo(Login.this, user);
                            Toast.makeText(Login.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this.getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(Login.this,jsonResult.getMsg(),Toast.LENGTH_SHORT).show();
    //                            Intent intent = new Intent(Login.this.getApplicationContext(), Login.class);
    //                            startActivity(intent);
                        }
                        System.out.println("jsonResult"+jsonResult);
                    }catch (Exception e){
                        Toast.makeText(Login.this,"登录失败！",Toast.LENGTH_SHORT).show();
                    }


                    Looper.loop();

                }
            }

        });
        Button btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        Button btn_forget_pwd=findViewById(R.id.btn_forget_password);
        btn_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Forget_Password.class);
                startActivity(intent);
            }
        });
    }
    //}
    /*保存用户登录信息*/
    public static void saveLoginInfo(Context context, User user) {
        // 获取SharedPreferences对象
        SharedPreferences sharedPre = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        // 获取Editor对象
        SharedPreferences.Editor editor = sharedPre.edit();
        // 设置参数
        editor.putString("userId", user.getUserId());
        editor.putString("password", user.getPassword());
        editor.putString("username",user.getUserName());
        editor.putString("sex",user.getSex().toString());
        editor.putString("college",user.getCollege());
        editor.putString("classname",user.getClassName());
        editor.putString("qq",user.getQq());
        editor.putString("phone",user.getPhone());
        editor.putString("email",user.getEmail());
        // 提交
        editor.commit();
    }

}
