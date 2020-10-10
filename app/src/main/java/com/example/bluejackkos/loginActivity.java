package com.example.bluejackkos;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bluejackkos.DB.DBHelper;

public class loginActivity extends AppCompatActivity {

    DBHelper helper = new DBHelper(this);

    EditText userName;
    EditText userPassword;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.login_Username_edittext);
        userPassword = findViewById(R.id.login_Password_edittext);
        loginButton = findViewById(R.id.login_btn);
        registerButton = findViewById(R.id.register_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String pass = userPassword.getText().toString();

//                startActivity(new Intent(getApplicationContext(), KostListActivity.class));

//                validasi
                if (username.isEmpty()){
                    Toast.makeText(loginActivity.this, "Please input your Username", Toast.LENGTH_SHORT).show();
                    return;
                }if(pass.isEmpty()){
                    Toast.makeText(loginActivity.this, "Please input your Password", Toast.LENGTH_SHORT).show();
                    return;
                }

//                validasi DB
                String password = helper.searchPass(username);
                if(pass.equals(password)){
                    startActivity(new Intent(getApplicationContext(),KostListActivity.class));


                }else{
                    Toast.makeText(loginActivity.this, "Username and Password don't match", Toast.LENGTH_SHORT).show();
                }

//                Sucess



            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), registerActivity.class));
            }
        });

    }
    public void regis(View view){
        Intent intent = new Intent(this, registerActivity.class);
        startActivity(intent);
    }

}
