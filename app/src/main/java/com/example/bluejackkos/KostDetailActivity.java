package com.example.bluejackkos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bluejackkos.DB.DBHelper;
import com.example.bluejackkos.Maps.MapsActivity;
import com.example.bluejackkos.Model.Global;

import java.util.Calendar;

import static com.example.bluejackkos.DB.DBHelper.FIELD_USER_DOB;
import static com.example.bluejackkos.DB.DBHelper.FIELD_USER_GENDER;
import static com.example.bluejackkos.DB.DBHelper.FIELD_USER_PASSWORD;
import static com.example.bluejackkos.DB.DBHelper.FIELD_USER_PHONE;
import static com.example.bluejackkos.DB.DBHelper.TABLE_USER;


public class KostDetailActivity extends AppCompatActivity {

    ImageView kostImgDetail;
    TextView kostNameDetail, kostPriceDetail, kostFacilitiesDetail, kostDescriptionDetail,kostLatDetail, kostLngDetail;
    Button book_btn, map_detail, date_btn;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private int yearNow;
    private int monthNow;
    private int dayNow;
    private int year1;
    private int month1;
    private int day1;
    DBHelper helper = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_detail);
        kostImgDetail = findViewById(R.id.detail_kostImg_tv);
        kostNameDetail=findViewById(R.id.detail_kostName_tv);
        kostDescriptionDetail=findViewById(R.id.detail_kostDescription_tv);
        kostFacilitiesDetail=findViewById(R.id.detail_kostFacility_tv);
        kostPriceDetail=findViewById(R.id.detailKostPrice_tv);
        kostLatDetail=findViewById(R.id.detail_kostLat_tv);
        kostLngDetail=findViewById(R.id.detail_kostLng_tv);
        book_btn = findViewById(R.id.book_btn);
        date_btn = findViewById(R.id.viewBookingDate_btn);
        map_detail=findViewById(R.id.viewLoc_btn);

        Intent intent = getIntent();
        final String image = intent.getStringExtra("image");
        final String name = intent.getStringExtra("name");
        final String id = intent.getStringExtra("id");
        final String facilities = intent.getStringExtra("facilities");
        final String address = intent.getStringExtra ("address");
        final Double lat = intent.getDoubleExtra("lat", -1);
        final Double lng = intent.getDoubleExtra("lng", -1);
        final String price = intent.getStringExtra("price");
        //masukin ke dalam kelas dari data yang di pass dari class sebelumny
        Glide.with(this).load(image).into(kostImgDetail);
        kostNameDetail.setText(name);
        kostDescriptionDetail.setText(address);
        kostFacilitiesDetail.setText(facilities);
        kostPriceDetail.setText(String.format("Rp. %s", price));
        kostLatDetail.setText(lat.toString());
        kostLngDetail.setText(lng.toString());
        //untuk menampilkan setting waktu di set datepickerdialog di tampilan
        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                //untuk dapetinn tanggal sekarang
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(KostDetailActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener
                        , year, month, day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });
        //untuk setelah nentuin tanggal
        onDateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                month1 = month;
                year1 = year;
                day1 = dayOfMonth;
                String date = month + "/" + dayOfMonth + "/" + year;

                date_btn.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        monthNow = cal.get(Calendar.MONTH);
        dayNow = cal.get(Calendar.DAY_OF_MONTH);
        yearNow = cal.get(Calendar.YEAR);

        map_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (KostDetailActivity.this, MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });



        book_btn.setOnClickListener(new View.OnClickListener() {
            //validasi waktu dari date
            @Override
            public void onClick(View v) {
                if (year1< yearNow){
                    Toast.makeText(KostDetailActivity.this, "Day of booking at least today", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (month1< monthNow){
                    Toast.makeText(KostDetailActivity.this, "Day of booking at least today", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (day1 < dayNow){
                    Toast.makeText(KostDetailActivity.this, "Day of booking at least today", Toast.LENGTH_SHORT).show();
                    return;
                }
                String date = month1 + "/" + day1 + "/" + year1;
                boolean test = true;

                //untuk ngecek ada yang sama atau tidak
                //karena di soal gk boleh ad yang sama
                SQLiteDatabase db =helper.getReadableDatabase();
                //disini set cursornya sesuai dengan user id yang sekarang

                Cursor cursor = db.rawQuery("SELECT * FROM " + helper.TABLE_BOOKING +" WHERE "+helper.FIELD_USERBOOK_ID +" LIKE '"+Global.useridNow+"'", null);
//                if(cursor == null){
                if (cursor.moveToFirst() != false){
                    cursor.moveToFirst();
                    if (!name.equals(cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_NAME))) || !cursor.getString(cursor.getColumnIndex(helper.FIELD_STATUS)).equals("Active")) {

                        while (cursor.moveToNext()){
                            //ini buat cari username yang sama
                            if (name.equals(cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_NAME))) && cursor.getString(cursor.getColumnIndex(helper.FIELD_STATUS)).equals("Active")) {
                                test = false;
                                break;
                            }
                            //ini untuk jumlah account
//                    count++;
                        }
                    }else{
                        test = false;
                    }
                }


//                }

                //kalau data tidak ad yang sama maka inssert
                if (test == true){
                    db = helper.getWritableDatabase();
                    SQLiteDatabase db1 = helper.getReadableDatabase();
                    Cursor cursor2 = db1.rawQuery("SELECT * FROM " + helper.TABLE_BOOKING , null);
                    ContentValues values = new ContentValues();
                    cursor = db.rawQuery("SELECT * FROM " + helper.TABLE_BOOKING +" WHERE "+helper.FIELD_USERBOOK_ID +" LIKE '"+Global.useridNow+"'", null);
                    int position = cursor2.getCount();
                    String id = "BK"+position+position+position;
                    values.put(helper.FIELD_BOOKING_ID, id);
                    values.put(helper.FIELD_USERBOOK_ID, Global.useridNow);
                    values.put(helper.FIELD_KOS_NAME, name);
                    values.put(helper.FIELD_KOS_PRICE, price);
                    values.put(helper.FIELD_KOS_FACILITIES, facilities);
                    values.put(helper.FIELD_KOS_IMAGE, image);
                    values.put(helper.FIELD_KOS_ADDRESS, address);
                    values.put(helper.FIELD_BOOKDATE, date);
                    values.put(helper.FIELD_STATUS, "Active");
                    values.put(helper.FIELD_KOS_LAT, lat);
                    values.put(helper.FIELD_KOS_LNG, lng);

                    db.insert(helper.TABLE_BOOKING, null, values);
                    db.close();
                    cursor.close();
                    cursor2.close();
                    Toast.makeText(KostDetailActivity.this, "You Have Successfully Book", Toast.LENGTH_SHORT).show();
                    finish();
                }
                //kalau ad yang sama maka gagal
                else if (test == false){
                    Toast.makeText(KostDetailActivity.this, "You HaveAlready Book It", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
