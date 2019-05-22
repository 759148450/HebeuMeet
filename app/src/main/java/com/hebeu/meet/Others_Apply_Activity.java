package com.hebeu.meet;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

public class Others_Apply_Activity extends AppCompatActivity {
    Handler handler = null;
    TextView textView = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.others_apply_activity);
//        textView = findViewById(R.id.others);

    }

    class MyThread extends Thread {
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}
