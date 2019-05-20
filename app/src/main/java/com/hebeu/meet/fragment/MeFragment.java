package com.hebeu.meet.fragment;

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

import com.hebeu.meet.R;
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

    private Button button = null;
    private TextView textView =null;

    private Handler handler = null;
    public MeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = (Button) getActivity().findViewById(R.id.testButton);
        textView = getActivity().findViewById(R.id.textView);


        handler = new Handler();



        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                textView.setText("正在加载。。。");

                MyThread thread=new MyThread();
                thread.start();

            }

            class MyThread extends Thread{
                @Override
                public void run() {
                    textView = getActivity().findViewById(R.id.textView);
                    String res = HttpUtil.get("http://112.74.194.121:8888/user/getUserById?userId=160210405");
                    final User user = JSONUtil.toBean(res,User.class);

                    System.out.println(user.toString());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(user.getUserName());
                        }
                    });
                }
            }

//            Runnable runnableUi = new Runnable() {
//                @Override
//                public void run() {
//                    textView.setText("测试");
//                }
//            };
        });

    }


}
