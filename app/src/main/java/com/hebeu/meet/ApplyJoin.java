package com.hebeu.meet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.domain.ActivityJoinUser;
import com.hebeu.meet.domain.JSONResult;

import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/*申请参加
* Vanilla
* 5-27
* */
public class ApplyJoin extends AppCompatActivity {
    private TextView words=null;
    private Button btn_send_apply=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_join);
        words=findViewById(R.id.words);
        btn_send_apply=findViewById(R.id.btn_send_apply);
        btn_send_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread thread=new MyThread();
                thread.start();
            }
            class MyThread extends Thread{
                @Override
                public void run() {
                    ActivityJoinUser userjoin = new ActivityJoinUser();
                    /*获取登录的用户信息*/
                    SharedPreferences sharedPre = getSharedPreferences("config", Context.MODE_PRIVATE);
                    String userId=sharedPre.getString("userId", "");
                    /*获取活动id*/
                    Intent intent = getIntent();
                    int activity_id=intent.getIntExtra("activity_id",0);
                    userjoin.setUserId(userId);
                    userjoin.setActivityId(activity_id);
                    userjoin.setWords(String.valueOf(words.getText()));
                    userjoin.setJoinState("1");


                    Map<String,Object> paramMap = BeanUtil.beanToMap(userjoin);
                    String res = HttpUtil.post("http://112.74.194.121:8889/userActivity/insertUserActivity",paramMap);

                    final JSONResult jsonResult = JSONUtil.toBean(res,JSONResult.class);

                    System.out.println(jsonResult);
                    Looper.prepare();

                    if (jsonResult.getCode() == 0){
                        //弹出对话框
                        showDialog();
                    }else {
                        Toast.makeText(ApplyJoin.this,"发送申请失败",Toast.LENGTH_SHORT).show();
                    }


                    Looper.loop();

                }
            }
            private void showDialog(){
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(ApplyJoin.this ,R.style.MyDialogStyle);
//                            normalDialog.setIcon(R.drawable.icon_dialog);
                normalDialog.setIcon(R.drawable.success);
                normalDialog.setTitle("发送申请成功");
                normalDialog.setMessage("请选择将要前往的页面");
                normalDialog.setPositiveButton("首页",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("您点击了【首页】");
                            }
                        });
                normalDialog.setNegativeButton("活动详情",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("您点击了 【活动详情】");
                            }
                        });
                // 显示
                normalDialog.show();
            }
        });

    }
}
