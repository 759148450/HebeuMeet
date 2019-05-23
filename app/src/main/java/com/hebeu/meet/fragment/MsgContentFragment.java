package com.hebeu.meet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hebeu.meet.R;
import com.hebeu.meet.adapter.ActivityAdapter;
import com.hebeu.meet.domain.Activity;
import com.hebeu.meet.domain.JSONResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * zyp
 * 消息内容页
 * 2019-5-19
 */
public class MsgContentFragment extends Fragment {
    @BindView(R.id.listView)
    ListView listView;

    private String name;

    private Handler handler = null;
    // 模拟数据
    private List<Activity> activityList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        name = bundle.getString("name");
        if (name == null) {
            name = "参数非法";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg_content, container, false);
        ButterKnife.bind(this, view);
        handler = new Handler();
        MyThread thread=new MyThread();
        thread.start();


        return view;
    }
    class MyThread extends Thread{
        @Override
        public void run() {

            Map<String, Object> paramMap = new HashMap<>();
            Integer typeId = 0;
            switch (name){
                case "推荐":
                    typeId = 0;
                    break;
                case "约吃饭":
                    typeId = 1;
                    break;
                case "约学习":
                    typeId = 2;
                    break;
                case "约跑步":
                    typeId = 3;
                    break;
                case "约逛街":
                    typeId = 4;
                    break;
                case "约其他":
                    typeId = 5;
                    break;
            }
            paramMap.put("typeId",typeId);
            String res = HttpUtil.get("112.74.194.121:8889/activity/selectActivityByTypeId",paramMap);
            JSONArray jsonArray = JSONUtil.parseArray(res);
            activityList = JSONUtil.toList(jsonArray,Activity.class);

            System.out.println(activityList);


            handler.post(new Runnable() {
                @Override
                public void run() {
                    ActivityAdapter activityAdapter = new ActivityAdapter(getActivity(), activityList);

                    listView.setAdapter(activityAdapter);
                }
            });


        }
    }


}
