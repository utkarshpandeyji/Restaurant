package com.example.adilkhan.restraunt.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.adilkhan.restraunt.Model.OrderModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "Restaurant.db";
    private static final int DB_VER = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<OrderModel> getCarts()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"ProductId","ProductName","Quantity","Price","Discount"};
        String sqlTable = "OrderDetails" ;

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<OrderModel> result = new ArrayList<>();
        if(c.moveToFirst()) {

            do {
                result.add(new OrderModel(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))));
            }
            while (c.moveToNext());
        }
                return result;

        }

        public void addToCart(OrderModel orderModel)
        {

            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("INSERT INTO OrderDetails(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                    orderModel.getProductId(),
                    orderModel.getProductName(),
                    orderModel.getQuantity(),
                    orderModel.getPrice(),
                    orderModel.getDiscount());
            db.execSQL(query);

        }

        public void cleanCart()
        {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("DELETE FROM OrderDetails");
            db.execSQL(query);
        }

        public void addToFavorites(String foodId)
        {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("INSERT INTO Favorites(FoodId) VALUES('%s');",foodId);
            db.execSQL(query);
        }

        public void removeFromFavorites(String foodId)
        {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("DELETE FROM Favorites WHERE FoodId = '%s';",foodId);
            db.execSQL(query);
        }

        public boolean isFavorite(String foodId)
        {
            SQLiteDatabase db = getReadableDatabase();
            String query = String.format("SELECT * FROM Favorites WHERE FoodId = '%s';",foodId);

            Cursor cursor = db.rawQuery(query,null);
            if(cursor.getCount()<=0)
            {
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
        }

    }

