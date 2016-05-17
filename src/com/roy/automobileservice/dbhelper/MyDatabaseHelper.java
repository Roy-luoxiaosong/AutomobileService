package com.roy.automobileservice.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Roy on 2016/5/9.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{
    public static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "userName text, "
            + "avatarImage integer"
            + "telNumber text, "
            + "email text, "
            + "sex text, "
            + "realName text, "
            + "carName text, "
            + "address text, "
            + "repairHi text, "
            + "beautyHi text, "
            + "password text)";

    public static final String CREATE_CAR = "create table Car ("
            + "id integer primary key autoincrement, "
            + "carName text, "
            + "imageIds text, "
            + "price text, "
            + "heat integer, "
            + "config text)";
/*
	public static final String CREATE_REPAIR_HISTORY = "create table RepairHi ("
			+ "id integer primary key autoincrement, "
			+ "time text, "
			+ "shopName text, "
			+ "post real, "
			+ "staffName integer, "
			+ "content text)";*/

/*	public static final String CREATE_BEAUT_HISTORY = "create table BeautyHi ("
			+ "id integer primary key autoincrement, "
			+ "time text, "
			+ "shopName text, "
			+ "post real, "
			+ "staffName integer, "
			+ "content text)";*/

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    private Context mContext;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_CAR);
//		db.execSQL(CREATE_REPAIR_HISTORY);
//		db.execSQL(CREATE_BEAUT_HISTORY);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
