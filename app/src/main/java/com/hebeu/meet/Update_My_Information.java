package com.hebeu.meet;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.hebeu.meet.domain.JSONResult;
import com.hebeu.meet.domain.User;
import com.hebeu.meet.fragment.MeFragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import static com.hebeu.meet.Login.saveLoginInfo;
import static com.hebeu.meet.tools.ImageHandler.*;

/*修改个人信息
 * Vanilla
 * 5-21
 * */
/*
 * 尝试添加修改个人头像
 * lihang
 * 2019-5-24
 */

public class Update_My_Information extends AppCompatActivity {
    private TextView user_id =null;
    private TextView user_name =null;
    private TextView user_sex =null;
    private TextView user_college =null;
    private TextView user_classname =null;
    private TextView user_qq =null;
    private TextView user_phone =null;
    private TextView user_email =null;
    private Button btn_update_user = null;
    private ImageView user_head = null;

    //zyp 修改图标大小
    private TextView user_img_mess = null;
    private TextView user_id_mess =null;
    private TextView user_name_mess =null;
    private TextView user_sex_mess =null;
    private TextView user_college_mess =null;
    private TextView user_classname_mess =null;
    private TextView user_qq_mess =null;
    private TextView user_phone_mess =null;
    private TextView user_email_mess =null;
     //---------end--------------------

    //修改头像用的一些常量变量
    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    private String userHead;

    /*---------------------------------------------------------------------------------*/
    private Handler handler = null;

    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);//启动返回上一级按钮
        //申请存储权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(Update_My_Information.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(Update_My_Information.this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
        setContentView(R.layout.update_my_information);//加载视图文件

        user_head = findViewById(R.id.personalHead);
        user_id =findViewById(R.id.user_id);
        user_name = findViewById(R.id.user_name);
        user_sex = findViewById(R.id.user_sex);
        user_college = findViewById(R.id.user_college);
        user_classname = findViewById(R.id.user_classname);
        user_qq = findViewById(R.id.user_qq);
        user_phone = findViewById(R.id.user_phone);
        user_email = findViewById(R.id.user_email);

        //zyp 设置图标大小2019-5-22上午
        user_img_mess = findViewById(R.id.user_img_mess);
        user_id_mess = findViewById(R.id.user_id_mess);
        user_name_mess = findViewById(R.id.user_name_mess);
        user_sex_mess = findViewById(R.id.user_sex_mess);
        user_college_mess = findViewById(R.id.user_college_mess);
        user_classname_mess = findViewById(R.id.user_classname_mess);
        user_qq_mess = findViewById(R.id.user_qq_mess);
        user_phone_mess = findViewById(R.id.user_phone_mess);
        user_email_mess = findViewById(R.id.user_email_mess);
        Drawable user_img_img = getResources().getDrawable(R.drawable.user_img);
        Drawable user_id_img = getResources().getDrawable(R.drawable.user_id);
        Drawable user_name_img = getResources().getDrawable(R.drawable.user_name);
        Drawable user_sex_img = getResources().getDrawable(R.drawable.user_sex);
        Drawable user_college_img = getResources().getDrawable(R.drawable.user_college);
        Drawable user_classname_img = getResources().getDrawable(R.drawable.user_classname);
        Drawable user_qq_img = getResources().getDrawable(R.drawable.user_qq);
        Drawable user_phone_img = getResources().getDrawable(R.drawable.user_phone);
        Drawable user_email_img = getResources().getDrawable(R.drawable.user_email);
        user_img_img.setBounds(0,0,40,40);
        user_id_img.setBounds(0,0,50,50);
        user_name_img.setBounds(0,0,40,40);
        user_sex_img.setBounds(0,0,40,40);
        user_college_img.setBounds(0,0,40,40);
        user_classname_img.setBounds(0,0,40,40);
        user_qq_img.setBounds(0,0,40,40);
        user_phone_img.setBounds(0,0,40,40);
        user_email_img.setBounds(0,0,40,40);
        user_img_mess.setCompoundDrawables(user_img_img,null,null,null);
        user_id_mess.setCompoundDrawables(user_id_img,null,null,null);
        user_name_mess.setCompoundDrawables(user_name_img,null,null,null);
        user_sex_mess.setCompoundDrawables(user_sex_img,null,null,null);
        user_college_mess.setCompoundDrawables(user_college_img,null,null,null);
        user_classname_mess.setCompoundDrawables(user_classname_img,null,null,null);
        user_qq_mess.setCompoundDrawables(user_qq_img,null,null,null);
        user_phone_mess.setCompoundDrawables(user_phone_img,null,null,null);
        user_email_mess.setCompoundDrawables(user_email_img,null,null,null);
        //----------------------------
        handler = new Handler();
        MyThread thread=new MyThread();
        thread.start();
//        System.out.println("my_information");
        btn_update_user=findViewById(R.id.btn_update_user);
        //绑定更新按钮事件
        btn_update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread_Update_User thread_update_user=new MyThread_Update_User();
                thread_update_user.start();
            }
            class MyThread_Update_User extends Thread{
                @Override
                public void run() {
                    User update_user = new User();
                    /*获取用户信息*/
                    SharedPreferences sharedPre = getSharedPreferences("config", MODE_PRIVATE);
                    String userId=sharedPre.getString("userId", "");
                    update_user.setUserId(userId);
                    update_user.setUserName(String.valueOf(user_name.getText()));
                    update_user.setSex(Integer.parseInt(String.valueOf(user_sex.getText())));
                    update_user.setCollege(String.valueOf(user_college.getText()));
                    update_user.setClassName(String.valueOf(user_classname.getText()));
                    update_user.setQq(String.valueOf(user_qq.getText()));
                    update_user.setPhone(String.valueOf(user_phone.getText()));
                    update_user.setEmail(String.valueOf(user_email.getText()));
                    update_user.setHead(userHead);

                    Map<String,Object> paramMap = BeanUtil.beanToMap(update_user);
                    String res = HttpUtil.post("http://112.74.194.121:8889/user/updateUser",paramMap);
//                    String res = HttpUtil.post("http://192.168.1.103:8889/user/updateUser",paramMap);//李航本地测试接口
//                    System.out.println("res:"+res);
                    final JSONResult jsonResult = JSONUtil.toBean(res,JSONResult.class);
                    System.out.println(jsonResult);
                    Looper.prepare();

                    if (jsonResult.getCode() == 0){
                        //弹出对话框
                        Toast.makeText(Update_My_Information.this,"修改信息成功",Toast.LENGTH_SHORT).show();
                        /*重新保存用户信息到Sharedpreference*/
                        String res_id = HttpUtil.get("http://112.74.194.121:8889/user/getUserById?userId="+userId);
//                        String res_id = HttpUtil.get("http://192.168.1.103:8889/user/getUserById?userId="+userId);
                        final User my_user = JSONUtil.toBean(res_id, User.class);
//                        System.out.println("my_user"+my_user.toString());
                        saveLoginInfo(Update_My_Information.this, my_user);
                        /*跳转到个人中心*/
                        Intent intent = new Intent(Update_My_Information.this, My_Information.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Update_My_Information.this,"修改信息失败",Toast.LENGTH_SHORT).show();
                    }

                    Looper.loop();

                }
            }

        });

        //头像单击事件监听-->打开系统相册
        user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);//加载图片到前端
            c.close();
        }
    }

    //然后加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        Bitmap bms =  compressScale(bm);//压缩
        userHead = bitmapToString(bms);//图片转成string
        user_head.setImageBitmap(bms);//渲染头像（还没上传之前，刚选好图片的时候）
    }


    class MyThread extends Thread {
        public void run() {

            SharedPreferences sharedPre = getSharedPreferences("config", MODE_PRIVATE);
            String userId=sharedPre.getString("userId", "");
            String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserById?userId="+userId);
//            String res = HttpUtil.get("http://192.168.1.103:8889/user/getUserById?userId="+userId);
            final User user = JSONUtil.toBean(res, User.class);

//            System.out.println(user.toString());
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
                    user_head.setImageBitmap( stringToBitmap(user.getHead()));//加载信息的时候把头像渲染上去
                }
            });
        }
    }
}

