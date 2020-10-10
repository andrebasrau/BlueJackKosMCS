package com.example.bluejackkos;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bluejackkos.DB.DBHelper;
import com.example.bluejackkos.Model.Global;
import com.example.bluejackkos.Model.User;

import java.util.Calendar;

public class registerActivity extends AppCompatActivity {

    EditText usernameRegister;
    EditText phoneRegister;
    EditText passwordRegister;
    EditText confirmPasswordRegister;
    Button registerButton;
    RadioGroup rgGender;
    RadioButton rbMale,rbFemale;
    CheckBox agreement;
    DBHelper helper = new DBHelper(this);

    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;
    private SmsManager smsManager;
    private int smsPermission = PackageManager.PERMISSION_DENIED;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        final DBHelper helper = new DBHelper(this);

        usernameRegister = findViewById(R.id.register_username_edittext);
        phoneRegister = findViewById(R.id.register_phone_edittext);
        passwordRegister = findViewById(R.id.register_password_edittext);
        confirmPasswordRegister = findViewById(R.id.register_confirmpass_edittext);
        registerButton = findViewById(R.id.register_btn);
        rgGender = findViewById(R.id.RadioGroup);
        rbMale = findViewById(R.id.radiomale);
        rbFemale = findViewById(R.id.radiofemale);
        agreement =findViewById(R.id.register_checkbox);
        smsManager= SmsManager.getDefault();
        checkPermision();
        //Calender Set click
        etDate = findViewById(R.id.et_date);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        etDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(registerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String Date = day+"/"+month+"/"+year;
                        etDate.setText(Date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //jabarin varaible
                String username = usernameRegister.getText().toString();
                String phone = phoneRegister.getText().toString();
                String pass = passwordRegister.getText().toString();
                String conPass = confirmPasswordRegister.getText().toString();
                String date = etDate.getText().toString();


                //validasi username
                if (username.length()<=3 || username.length()>=25){
                    Toast.makeText(registerActivity.this, "User name must contain atleast 3-25 Character", Toast.LENGTH_SHORT).show();
                    return;
                }
                int numberCounter = 0;
                int charCounter = 0;
                int i;

                for (i=0;i<username.length();i+=1){
                    if(username.charAt(i)>='0'&& username.charAt(i)<='9'){
                        numberCounter+=1;
                    }else if(username.charAt(i)>='a'&&username.charAt(i)<='z'){
                        charCounter+=1;
                    }else if (username.charAt(i)>='A' && username.charAt(i)<='Z') {
                        charCounter += 1;
                    }
                }

                if(charCounter<1) {
                    Toast.makeText(registerActivity.this, "username must contain at least 1 Alphabetic Character", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(numberCounter<=0){
                    Toast.makeText(registerActivity.this, "User name must contain at least 1 digit number", Toast.LENGTH_SHORT).show();
                    return;
                }//Validasi username DB


                //validasi Password
                if(pass.length()<6){
                    Toast.makeText(registerActivity.this, "Password name must contain at least 6 Characters", Toast.LENGTH_SHORT).show();
                }
                int passNumberCounter=0;
                int passLowerCharCounter = 0;
                int passCapCharCounter=0;
                int j;

                for (j=0;j<pass.length();j+=1){
                    if(pass.charAt(j)>='0'&& pass.charAt(j)<='9'){
                        passNumberCounter+=1;
                    } if(pass.charAt(j)>='a'&&pass.charAt(j)<='z'){
                        passLowerCharCounter+=1;
                    } if (pass.charAt(j)>='A' && pass.charAt(j)<='Z'){
                        passCapCharCounter+=1;
                    }
                }

                if (passNumberCounter ==0){
                    Toast.makeText(registerActivity.this, "Password must contain at least 1 Number", Toast.LENGTH_SHORT).show();
                    return;
                } else if(passLowerCharCounter ==0){
                    Toast.makeText(registerActivity.this, "Password must contain at least 1 Lowercase Character", Toast.LENGTH_SHORT).show();
                    return;
                } else if(passCapCharCounter==0){
                    Toast.makeText(registerActivity.this, "Password must contain at least 1 Capital Character", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!pass.equals(conPass)) {
                    Toast.makeText(registerActivity.this, "Confirmation Password and Password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                //validasi phone
                if(phone.length()<10 || phone.length()>12 )   {
                    Toast.makeText(registerActivity.this, "Please Enter 10-12 digits of Your Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                //radio button validation
                int isSelected = rgGender.getCheckedRadioButtonId();
                if (isSelected == -1){
                    Toast.makeText(registerActivity.this, "must choose 1 gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton genderRadio = findViewById(isSelected);
                String gender;
                if(rgGender.getCheckedRadioButtonId() == 0) gender = "Male";
                else gender = "Female";

 ////               //validasi DOB
                if(date.isEmpty()){
                    Toast.makeText(registerActivity.this, "Please Choose your date of birth", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!agreement.isChecked()){
                    Toast.makeText(registerActivity.this, "Terms and Conditions must be checked", Toast.LENGTH_SHORT).show();
                    return;
                }
  ////
                //Generate ID
                boolean check = true;
                //masukin data ke DB
                int count = 0;
                String id;
                User user = new User();

                user.setUsername(username);
                user.setPassword(pass);
                user.setPhone(phone);
                user.setGender(gender);
                user.setDob(date);



                SQLiteDatabase db =helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + helper.TABLE_USER, null);

                cursor.moveToFirst();
                while (cursor.moveToNext()){
                    //ini buat cari username yang sama
                    if (username.equals(cursor.getString(cursor.getColumnIndex(helper.FIELD_USER_NAME)))) {
                        check = false;
                        //break;
                    }
                    //ini untuk jumlah account
                    count++;
                }

                cursor.close();
                //ini untuk inisialisasi id
                if (count < 10){
                    user.setId("US00"+cursor.getCount());
                }else if (count < 100 && count >= 10){
                    user.setId ("US0"+cursor.getCount());
                }else if (count < 1000 && count >= 100){
                    user.setId("US"+cursor.getCount());
                }




                if (check){
                    //SMS

                    if(smsPermission == PackageManager.PERMISSION_GRANTED){
                        final String message = "Thank you for Registering to BlueJack Kos App \n";
                        smsManager.sendTextMessage(phone, null,message, null, null);
                    }else{
                        checkPermision();
                    }

                    //ini untuk masukin trus balik
                    helper.insertNewUser(user);
                    Toast.makeText(registerActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else{
                    Toast.makeText(registerActivity.this, "Username Already Registered", Toast.LENGTH_SHORT).show();
                }

                //berhasil

            }

        });
    }


    public void onClickRadio(View view) {
        RadioGroup radioGroup = findViewById (R.id.RadioGroup);
        RadioButton radioButton = findViewById ( radioGroup.getCheckedRadioButtonId());


    }

    private void checkPermision(){
        smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(smsPermission != PackageManager.PERMISSION_GRANTED){
            String[] permissionMessage = new String[]{Manifest.permission.SEND_SMS};
            ActivityCompat.requestPermissions(this, permissionMessage, 1);
        }
    }


}
