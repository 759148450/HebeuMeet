package com.hebeu.meet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 个人资料-空项目页面
 */
public class EmptyActivity extends AppCompatActivity {

    private Button launch_activity = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_publish_activity_empty);

        launch_activity = findViewById(R.id.launch_activity);

        launch_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), .class);
//                startActivity(intent);
            }
        });

    }

}
