package com.example.bluejackkos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookingActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bottomNavigationView.setSelectedItemId(R.id.navigation_booking);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        startActivity(new Intent(BookingActivity.this, KostListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_booking:
                        return true;
                    case  R.id.navigation_signout:
                        startActivity(new Intent(BookingActivity.this, loginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}
