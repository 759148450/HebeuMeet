package com.hebeu.meet.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.R;
import com.hebeu.meet.domain.Activity;
import com.hebeu.meet.domain.JSONResult;

import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import static android.content.Context.MODE_PRIVATE;


/**
 * zyp
 * 发布活动页
 * 2019-5-19
 */
public class LaunchFragment extends Fragment {

    private TextView launch_message = null;
    private TextView title_message = null;
    //活动名称
    private EditText title = null;
    //活动分类
    private EditText type_id = null;
    //限制参加性别
    private EditText sex_limit = null;
    //限制人数
    private EditText people_limit = null;
    //活动时间
    private EditText activity_date = null;
    //活动地点
    private EditText activity_place = null;
    //活动内容
    private EditText add_content = null;
    //发布活动按钮
    private Button launch_activity= null;

    private Handler handler = null;

    public LaunchFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_launch, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        //活动名称
        title = getActivity().findViewById(R.id.title);;
        //活动分类
        type_id = getActivity().findViewById(R.id.type_id);;
        //限制参加性别
        sex_limit = getActivity().findViewById(R.id.sex_limit);;
        //限制人数
        people_limit = getActivity().findViewById(R.id.people_limit);;
        //活动时间
        activity_date = getActivity().findViewById(R.id.activity_date);;
        //活动地点
        activity_place = getActivity().findViewById(R.id.activityPlace);;
        //活动内容
        add_content = getActivity().findViewById(R.id.add_content);;
        //发布活动按钮
        launch_activity = (Button) getActivity().findViewById(R.id.launch_activity);

        launch_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyThread thread=new MyThread();
                thread.start();
            }

            class MyThread extends Thread{
                @Override
                public void run() {
                    Activity newActivity = new Activity();
                    /*获取登录的用户信息*/
                    SharedPreferences sharedPre = getActivity().getSharedPreferences("config", MODE_PRIVATE);
                    String userId=sharedPre.getString("userId", "");
                    newActivity.setUserId(userId);
                    newActivity.setTitle(String.valueOf(title.getText()));
                    newActivity.setTypeId(Integer.parseInt(String.valueOf(type_id.getText())));
                    newActivity.setSexLimit(Integer.parseInt(String.valueOf(sex_limit.getText())));
                    newActivity.setPeopleLimit(Integer.parseInt(String.valueOf(people_limit.getText())));
                    newActivity.setActivityContent(String.valueOf(add_content.getText()));
                    newActivity.setApplyState("1");
                    newActivity.setActivityPlace(String.valueOf(activity_place.getText()));

                    Map<String,Object> paramMap = BeanUtil.beanToMap(newActivity);
                    String res = HttpUtil.post("http://112.74.194.121:8889/activity/insertActivity",paramMap);
                    final JSONResult jsonResult = JSONUtil.toBean(res,JSONResult.class);

                    System.out.println(jsonResult);
                    Looper.prepare();

                    if (jsonResult.getCode() == 0){
                        //弹出对话框
                        showDialog();
                    }else {
                        Toast.makeText(getActivity(),"活动添加失败",Toast.LENGTH_SHORT).show();
                    }


                    Looper.loop();

                }
            }
            private void showDialog(){
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(getActivity() ,R.style.MyDialogStyle);
//                            normalDialog.setIcon(R.drawable.icon_dialog);
                normalDialog.setIcon(R.drawable.success);
                normalDialog.setTitle("活动添加成功");
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
