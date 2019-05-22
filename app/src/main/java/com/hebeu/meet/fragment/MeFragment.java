package com.hebeu.meet.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.MyApplyActivity;
import com.hebeu.meet.My_Information;
import com.hebeu.meet.My_Publish_Activity;
import com.hebeu.meet.Others_Apply_Activity;
import com.hebeu.meet.R;
import com.hebeu.meet.Register;
import com.hebeu.meet.domain.User;

import java.util.Map;

import butterknife.BindView;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

/**
 * zyp
 * 个人中心
 * 2019-5-19
 */
public class MeFragment extends Fragment {

    private Button my_information = null;
    /* private TextView textView =null;
     private Handler handler = null;*/
    private Button button_toMyPublish = null;
    private Button button_toOtherApply = null;
    private Button btn_register=null;
    public MeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        my_information = (Button) getActivity().findViewById(R.id.my_information);
        button_toMyPublish = getActivity().findViewById(R.id.toMyPublish);
        button_toOtherApply = getActivity().findViewById(R.id.toOtherApply);
        btn_register = getActivity().findViewById(R.id.btn_register);
        button_toOtherApply = getActivity().findViewById(R.id.toMyApply);
//        textView = getActivity().findViewById(R.id.textView);
//        handler = new Handler();
        //zyp 设置图标大小2019-5-22上午
        button_toMyPublish = getActivity().findViewById(R.id.toMyPublish);
        button_toOtherApply = getActivity().findViewById(R.id.toOtherApply);
        Drawable my_xinxi = getResources().getDrawable(R.drawable.my_xinxi);
        Drawable launch = getResources().getDrawable(R.drawable.launch);
        Drawable join = getResources().getDrawable(R.drawable.join);
        Drawable right = getResources().getDrawable(R.drawable.right);
        my_xinxi.setBounds(0,0,40,40);
        launch.setBounds(0,0,40,40);
        join.setBounds(0,0,40,40);
        right.setBounds(0,0,40,40);
        my_information.setCompoundDrawables(my_xinxi,null,right,null);
        button_toMyPublish.setCompoundDrawables(launch,null,right,null);
        button_toOtherApply.setCompoundDrawables(join,null,right,null);
        //------------------------------------

        /*Vanilla 5-21*/
        my_information.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), My_Information.class);
                startActivity(intent);
//                textView.setText("正在加载。。。");
//
//                MyThread thread=new MyThread();
//                thread.start();

            }

//            class MyThread extends Thread{
//                @Override
//                public void run() {
//                    textView = getActivity().findViewById(R.id.textView);
//                    String res = HttpUtil.get("http://112.74.194.121:8889/user/getUserById?userId=160210405");
//                    final User user = JSONUtil.toBean(res,User.class);
//
//                    System.out.println(user.toString());
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText(user.getUserName());
//                        }
//                    });
//                }
//            }

//            Runnable runnableUi = new Runnable() {
//                @Override
//                public void run() {
//                    textView.setText("测试");
//                }
//            };
        });
        button_toMyPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), My_Publish_Activity.class);
                startActivity(intent);
            }
        });

        button_toOtherApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MyApplyActivity.class);
                startActivity(intent);
            }
        });
        /*Vanilla 5-22*/
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

}
