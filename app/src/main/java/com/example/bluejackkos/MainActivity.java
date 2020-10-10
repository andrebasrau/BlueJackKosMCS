package com.example.bluejackkos;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable runable;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img);
        img.animate().alpha(4000).setDuration(0);
        handler = new Handler();
        handler.postDelayed (new Runnable(){

            @Override
            public void run() {
                Intent intent = new Intent (MainActivity.this, loginActivity.class);
                startActivity (intent);
                finish();

            }
        }, 4000);
    }

}
