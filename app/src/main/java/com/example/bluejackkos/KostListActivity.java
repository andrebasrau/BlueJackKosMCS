package com.example.bluejackkos;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bluejackkos.DB.BookingAdapter;
import com.example.bluejackkos.DB.DBHelper;
import com.example.bluejackkos.DB.KostAdapter;
import com.example.bluejackkos.Model.Booking;
import com.example.bluejackkos.Model.Global;
import com.example.bluejackkos.Model.Kost;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.VISIBLE;
import static com.example.bluejackkos.DB.DBHelper.FIELD_BOOKING_ID;
import static com.example.bluejackkos.DB.DBHelper.FIELD_STATUS;
import static com.example.bluejackkos.DB.DBHelper.TABLE_BOOKING;


//import static com.example.bluejackkos.Model.KosArray.kosts;


public class KostListActivity extends AppCompatActivity implements DialogBook.DialogBookListener{

    RecyclerView recyclerView;
    private final  String URL = "https://raw.githubusercontent.com/dnzrx/SLC-REPO/master/MOBI6006/E202-MOBI6006-KR01-00.json";
    ArrayList<Kost> kosts;
    ArrayList <Booking> BookingList;
    KostAdapter kostAdapter;
    TextView ket;
    BottomNavigationView bottomNavigationView;
    BookingAdapter bookingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_list);
        ket = findViewById(R.id.keterangan);

        recyclerView = findViewById (R.id.rv_item);
        kosts = new ArrayList<>();
        Log.d("useridnow", "onCreate: "+ Global.useridNow);
        bottomNavigationView = findViewById(R.id.bottomNavBar);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:

                        ArrayList <Kost> KostRemove;
                        KostRemove = kosts;
                        kosts.removeAll(KostRemove);
                        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                if(response != null &&response.length() >0){
                                    for (int i = 0; i<response.length();i++){
                                        try {
                                            JSONObject object = response.getJSONObject(i);
                                            String kostName, kostPrice, kostFacilities, kostAddress;
                                            String kostImageSrc;
                                            Integer kostId;
                                            double lat;
                                            double lng;

                                            kostId=object.getInt("id");
                                            kostName =object.getString("name");
                                            kostPrice=object.getString("price");
                                            kostFacilities=object.getString("facilities");
                                            kostAddress= object.getString("address");
                                            kostImageSrc=object.getString("image");
                                            lat = object.getDouble("LAT");
                                            lng = object.getDouble("LNG");

                                            final Kost kost = new Kost(kostId,kostName,kostPrice,kostFacilities,kostImageSrc,kostAddress,lat,lng);
                                            kosts.add(kost);
                                            recyclerView.setHasFixedSize(true);
                                            //disini tadi w tambahin kostListActivity.this sebagai context di adapter untuk ngeglide
                                            kostAdapter = new KostAdapter(KostListActivity.this , kosts);

                                            recyclerView.setLayoutManager(new LinearLayoutManager(KostListActivity.this));
                                            recyclerView.setAdapter(kostAdapter);
                                            kostAdapter.setOnClickItemClickListener(new KostAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(int position) {
                                                    Intent intent = new Intent(KostListActivity.this, KostDetailActivity.class);
                                                    //ini untuk ngepast semua data kos ke class kosDetailActivity
                                                    intent.putExtra("id", kosts.get(position).getKostname());
                                                    intent.putExtra("name", kosts.get(position).getKostname());
                                                    intent.putExtra("price", kosts.get(position).getKostPrice());
                                                    intent.putExtra("facilities", kosts.get(position).getKostFacilities());
                                                    intent.putExtra("address", kosts.get(position).getKostAddress());
                                                    intent.putExtra ("image", kosts.get(position).getKostImageSrc());
                                                    intent.putExtra("lat", kosts.get(position).getLat());
                                                    intent.putExtra("lng", kosts.get(position).getLng());
                                                    startActivity (intent);


                                                }
                                            });


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }
                            }
                        };

                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(KostListActivity.this, "Failed Load Kost Data", Toast.LENGTH_SHORT).show();
                            }
                        };

                        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
                        Network network = new BasicNetwork(new HurlStack());
                        RequestQueue requestQueue = new RequestQueue(cache, network);
                        requestQueue.start();

                        JsonArrayRequest request = new JsonArrayRequest(URL, listener, errorListener);
                        requestQueue.add(request);

                        ket.setVisibility(View.GONE);
                        return true;
                    case R.id.navigation_booking:

                        BookingList = new ArrayList<>();
                        ArrayList <Booking> bookRemove = new ArrayList<>();

                        bookRemove = BookingList;

                        BookingList.removeAll(bookRemove);
                        String id;
                        String name;
                        String price;
                        String facilities;
                        String address;
                        String image;
                        String bookdate;
                        Double lat;
                        Double lng;


                        recyclerView.setHasFixedSize(true);
                        DBHelper helper = new DBHelper(KostListActivity.this);
                        SQLiteDatabase db = helper.getReadableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKING +" WHERE "+helper.FIELD_USERBOOK_ID +" LIKE '"+Global.useridNow+"' AND "+helper.FIELD_STATUS +" LIKE 'Active'", null);

                        if (cursor.moveToFirst() != false){
                            cursor.moveToFirst();

                            id = cursor.getString(cursor.getColumnIndex (helper.FIELD_BOOKING_ID));
                            name = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_NAME));
                            price = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_PRICE));
                            facilities = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_FACILITIES));
                            address = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_ADDRESS));
                            image = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_IMAGE));
                            bookdate = cursor.getString(cursor.getColumnIndex (helper.FIELD_BOOKDATE));
                            lat = cursor.getDouble(cursor.getColumnIndex(helper.FIELD_KOS_LAT));
                            lng = cursor.getDouble (cursor.getColumnIndex (helper.FIELD_KOS_LNG));
                            com.example.bluejackkos.Model.Booking booking = new Booking(id, name, price, facilities, address, image, bookdate, lat , lng);
                            BookingList.add(booking);
                        }

                        while (cursor.moveToNext()){

                            id = cursor.getString(cursor.getColumnIndex (helper.FIELD_BOOKING_ID));
                            name = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_NAME));
                            price = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_PRICE));
                            facilities = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_FACILITIES));
                            address = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_ADDRESS));
                            image = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_IMAGE));
                            bookdate = cursor.getString(cursor.getColumnIndex (helper.FIELD_BOOKDATE));
                            lat = cursor.getDouble(cursor.getColumnIndex(helper.FIELD_KOS_LAT));
                            lng = cursor.getDouble (cursor.getColumnIndex (helper.FIELD_KOS_LNG));
                            com.example.bluejackkos.Model.Booking book1 = new Booking(id, name, price, facilities, address, image, bookdate, lat , lng);
                            BookingList.add(book1);


                        }
                        if  (BookingList.isEmpty()){
                            ket.setVisibility(VISIBLE);
                            ket.setText("no transaction");
                        }else{
                            ket.setVisibility(View.GONE);
                        }
                        bookingAdapter = new BookingAdapter(KostListActivity.this, BookingList);

                        recyclerView.setLayoutManager(new LinearLayoutManager(KostListActivity.this));
                        recyclerView.setAdapter(bookingAdapter);
                        bookingAdapter.setOnClickItemClickListener(new KostAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                openDialog(position);


                            }
                        });



                        return true;
                    case  R.id.navigation_signout:
                        finish();
                }
                return false;
            }
        });

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null &&response.length() >0){
                    for (int i = 0; i<response.length();i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            String kostName, kostPrice, kostFacilities, kostAddress;
                            String kostImageSrc;
                            Integer kostId;
                            double lat;
                            double lng;

                            kostId=object.getInt("id");
                            kostName =object.getString("name");
                            kostPrice=object.getString("price");
                            kostFacilities=object.getString("facilities");
                            kostAddress= object.getString("address");
                            kostImageSrc=object.getString("image");
                            lat = object.getDouble("LAT");
                            lng = object.getDouble("LNG");

                            final Kost kost = new Kost(kostId,kostName,kostPrice,kostFacilities,kostImageSrc,kostAddress,lat,lng);
                            kosts.add(kost);
                            recyclerView.setHasFixedSize(true);
                            //disini tadi w tambahin kostListActivity.this sebagai context di adapter untuk ngeglide
                            kostAdapter = new KostAdapter(KostListActivity.this , kosts);

                            recyclerView.setLayoutManager(new LinearLayoutManager(KostListActivity.this));
                            recyclerView.setAdapter(kostAdapter);
                            kostAdapter.setOnClickItemClickListener(new KostAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Intent intent = new Intent(KostListActivity.this, KostDetailActivity.class);
                                    //ini untuk ngepast semua data kos ke class kosDetailActivity
                                    intent.putExtra("id", kosts.get(position).getKostname());
                                    intent.putExtra("name", kosts.get(position).getKostname());
                                    intent.putExtra("price", kosts.get(position).getKostPrice());
                                    intent.putExtra("facilities", kosts.get(position).getKostFacilities());
                                    intent.putExtra("address", kosts.get(position).getKostAddress());
                                    intent.putExtra ("image", kosts.get(position).getKostImageSrc());
                                    intent.putExtra("lat", kosts.get(position).getLat());
                                    intent.putExtra("lng", kosts.get(position).getLng());
                                    startActivity (intent);


                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KostListActivity.this, "Failed Load Kost Data", Toast.LENGTH_SHORT).show();
            }
        };

        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        JsonArrayRequest request = new JsonArrayRequest(URL, listener, errorListener);
        requestQueue.add(request);


    }
    public void openDialog(int position1){
        DialogBook dialog = new DialogBook(position1);
        dialog.show (getSupportFragmentManager(), "Example dialog");


    }

    @Override
    public void onYesClicked(int position) {
        DBHelper helper = new DBHelper(KostListActivity.this);
        SQLiteDatabase db1 = helper.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(FIELD_STATUS, "canceled");
        db1.update(helper.TABLE_BOOKING, cv, FIELD_BOOKING_ID+" = ? AND "+helper.FIELD_USERBOOK_ID+" = ?", new String [] {BookingList.get(position).getId(), Global.useridNow});
        db1.close();





        ArrayList <Booking> bookRemove;

        bookRemove = BookingList;

        BookingList.removeAll(bookRemove);
        String id;
        String name;
        String price;
        String facilities;
        String address;
        String image;
        String bookdate;
        Double lat;
        Double lng;


        recyclerView.setHasFixedSize(true);

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKING +" WHERE "+helper.FIELD_USERBOOK_ID +" LIKE '"+Global.useridNow+"' AND "+helper.FIELD_STATUS +" LIKE 'Active'", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){

            id = cursor.getString(cursor.getColumnIndex (helper.FIELD_BOOKING_ID));
            name = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_NAME));
            price = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_PRICE));
            facilities = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_FACILITIES));
            address = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_ADDRESS));
            image = cursor.getString(cursor.getColumnIndex(helper.FIELD_KOS_IMAGE));
            bookdate = cursor.getString(cursor.getColumnIndex (helper.FIELD_BOOKDATE));
            lat = cursor.getDouble(cursor.getColumnIndex(helper.FIELD_KOS_LAT));
            lng = cursor.getDouble (cursor.getColumnIndex (helper.FIELD_KOS_LNG));
            com.example.bluejackkos.Model.Booking booking = new Booking(id, name, price, facilities, address, image, bookdate, lat , lng);
            BookingList.add(booking);


        }
        bookingAdapter = new BookingAdapter(KostListActivity.this, BookingList);
        if  (BookingList.isEmpty()){
            ket.setVisibility(VISIBLE);
            ket.setText("no transaction");
        }else{
            ket.setVisibility(View.GONE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(KostListActivity.this));
        recyclerView.setAdapter(bookingAdapter);
        bookingAdapter.setOnClickItemClickListener(new KostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openDialog(position);


            }
        });

    }
}
