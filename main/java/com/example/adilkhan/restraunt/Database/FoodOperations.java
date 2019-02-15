package com.example.adilkhan.restraunt.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodOperations {

    public static final String LOGTAG = "Rest_Mng_Sys";

    public static SQLiteOpenHelper dbhandler;
    private final Context context;
    SQLiteDatabase database;
    DatabaseNew databaseNew;
    private static final String[] allColumns =
            {
                    DatabaseNew.ProductId,
                    DatabaseNew.ProductName,
                    DatabaseNew.Price,
                    DatabaseNew.Quantity,
                    DatabaseNew.Discount
            };

    public FoodOperations(Context context) {
        this.context = context;
        databaseNew = new DatabaseNew(context);
        dbhandler = new DatabaseNew(context);
    }
}
