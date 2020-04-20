package com.moyuchen.hook.hookStartActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moyuchen.hook.R;

public class StubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stub);
    }
}
