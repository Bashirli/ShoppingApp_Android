package com.bashirli.bashirlishop.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bashirli.bashirlishop.R;
import com.bashirli.bashirlishop.LoginRegister.LogRegActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, LogRegActivity.class);
                startActivity(intent);
                finish();
            }
        },1300);
    }
}