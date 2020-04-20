package com.moyuchen.hook.hookStartActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.moyuchen.hook.MainActivity;
import com.moyuchen.hook.R;

import java.lang.reflect.InvocationTargetException;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.test).setOnClickListener(l->{
            startActivity(new Intent(TestActivity.this,Test2Activity.class));
        });
        findViewById(R.id.second).setOnClickListener(l->{
            getApplicationContext().startActivity(new Intent(TestActivity.this, MainActivity.class));
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            HookHelper.hookAMN();
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
