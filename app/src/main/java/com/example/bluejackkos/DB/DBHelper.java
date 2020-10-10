package com.example.bluejackkos.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bluejackkos.Model.Global;
import com.example.bluejackkos.Model.User;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public String idNow;
    private static final String DB_NAME = "BluejackKos";
    private static final int DB_VERSION = 10;
    public static final String TABLE_USER = "MsUser";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_USER_NAME = "userName";
    public static final String FIELD_USER_PASSWORD = "userPassword" ;
    public static final String FIELD_USER_PHONE = "userPhone" ;
    public static final String FIELD_USER_GENDER = "userGender";
    public static final String FIELD_USER_DOB = "userDOB";
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(" +
            FIELD_USER_ID + " TEXT PRIMARY KEY, " +
            FIELD_USER_NAME + " TEXT, " +
            FIELD_USER_PASSWORD + " TEXT, " +
            FIELD_USER_PHONE + " TEXT, " +
            FIELD_USER_GENDER + " TEXT, " +
            FIELD_USER_DOB + " TEXT);";

    private static final String DROP_TABLE_INVENTORY = "DROP TABLE IF EXISTS " + TABLE_USER + ";";

    public static final String TABLE_KOS = "MsKos";
    public static final String FIELD_KOS_ID = "kosId";
    public static final String FIELD_KOS_NAME = "kosName";
    public static final String FIELD_KOS_PRICE = "kosPrice" ;
    public static final String FIELD_KOS_FACILITIES = "kosFacilities" ;
    public static final String FIELD_KOS_IMAGE = "kosImage";
    public static final String FIELD_KOS_ADDRESS = "kosAddress";
    public static final String FIELD_KOS_LAT = "kosLat";
    public static final String FIELD_KOS_LNG = "kosLng";
    private static final String CREATE_TABLE_KOS = "CREATE TABLE IF NOT EXISTS " + TABLE_KOS + "(" +
            FIELD_KOS_ID + " TEXT PRIMARY KEY, " +
            FIELD_KOS_NAME + " TEXT, " +
            FIELD_KOS_PRICE + " TEXT, " +
            FIELD_KOS_FACILITIES + " TEXT, " +
            FIELD_KOS_IMAGE + " NUMERIC, " +
            FIELD_KOS_ADDRESS + " TEXT, " +
            FIELD_KOS_LAT + " NUMERIC, " +
            FIELD_KOS_LNG + " NUMERIC);";

    private static final String DROP_TABLE_KOS = "DROP TABLE IF EXISTS " + TABLE_KOS + ";";

    public static final String TABLE_BOOKING = "TrBooking";
    public static final String FIELD_BOOKING_ID = "bookingId";
    public static final String FIELD_USERBOOK_ID = "userId";
    public static final String FIELD_BOOKDATE = "bookingDate";
    public static final String FIELD_STATUS = "statusBooking";
    //    public static final String FIELD_KOSBOOK_ID = "kosId" ;
    private static final String CREATE_TABLE_BOOKING = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKING + "(" +
            FIELD_BOOKING_ID + " TEXT PRIMARY KEY, " +
            FIELD_USERBOOK_ID + " TEXT REFERENCES "+TABLE_USER+" (userId) , " +
            FIELD_KOS_NAME + " TEXT  , " +
            FIELD_KOS_PRICE + " TEXT , " +
            FIELD_KOS_FACILITIES + " TEXT , " +
            FIELD_KOS_IMAGE + " TEXT , " +
            FIELD_KOS_ADDRESS + " TEXT , " +
            FIELD_BOOKDATE + " TEXT ," +
            FIELD_STATUS + " TEXT , " +
            FIELD_KOS_LAT + " NUMERIC , " +
            FIELD_KOS_LNG + " NUMERIC );";

    private static final String DROP_TABLE_BOOKING = "DROP TABLE IF EXISTS " + TABLE_BOOKING + ";";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USER);

        db.execSQL(CREATE_TABLE_BOOKING);

//        db.execSQL(CREATE_TABLE_KOS);
    }

    public void insertNewUser(User user){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String querry ="select * from MsUser";
        Cursor cursor = db.rawQuery(querry, null);

//        int position = cursor.getCount();

        values.put(FIELD_USER_ID, user.getId());
        values.put(FIELD_USER_NAME, user.getUsername());
        values.put(FIELD_USER_PASSWORD, user.getPassword());
        values.put(FIELD_USER_PHONE, user.getPhone());
        values.put(FIELD_USER_GENDER, user.getGender());
        values.put(FIELD_USER_DOB, user.getDob());

        db.insert(TABLE_USER, null,values);
        db.close();
    }
//ini gk kepake w pindahin ke depan
    public String searchPass(String username){
        db = this.getReadableDatabase();
//        String query = "select "+FIELD_USER_NAME+", "+ FIELD_USER_PASSWORD+" from "+TABLE_USER;
//        Cursor cursor = db.rawQuery(query, null);
        //perbaikin inisialisasi cursor
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        String usernamedb, passworddb;
        passworddb = "not found";
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            usernamedb = cursor.getString(cursor.getColumnIndex(FIELD_USER_NAME));
            if(username.equals(usernamedb)){
                passworddb = cursor.getString(cursor.getColumnIndex(FIELD_USER_PASSWORD));
                //disini w ngeset yang global tadi agar bisa di akses ke semua class

                Global.useridNow = cursor.getString(cursor.getColumnIndex(FIELD_USER_ID));

                break;
            }
        }

        return passworddb;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROP THE EXISTING TABLES AND RECREATE AGAIN
        db.execSQL(DROP_TABLE_BOOKING);
        db.execSQL(DROP_TABLE_INVENTORY);
        onCreate(db);
    }




}
