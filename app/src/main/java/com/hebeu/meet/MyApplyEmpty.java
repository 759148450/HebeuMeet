package com.hebeu.meet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MyApplyEmpty extends AppCompatActivity {
    Button button = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_apply_empty);

        button=findViewById(R.id.to_home_activity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyApplyEmpty.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
