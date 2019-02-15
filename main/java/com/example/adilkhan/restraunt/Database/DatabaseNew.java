package com.example.adilkhan.restraunt.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseNew extends SQLiteOpenHelper {

    private static final String DB_NAME = "Restaurant.db";
    private static final int DB_VER = 1;

    public static final String TableFood = "OrderDetails";
    public static final String ProductId = "ProductId";
    public static final String ProductName = "ProductName";
    public static final String Price = "Price";
    public static final String Quantity = "Quantity";
    public static final String Discount = "Discount";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TableFood + " (" +
                    ProductId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProductName + " TEXT, " +
                    Price + " TEXT, " +
                    Quantity + " TEXT, " +
                    Discount + " TEXT, " +
                    ")";


    public DatabaseNew(Context context){
        super(context, DB_NAME, null, DB_VER);

        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TableFood);
        db.execSQL(TABLE_CREATE);
    }
}
