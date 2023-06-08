package com.example.facebookui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PRICE + " REAL)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
            onCreate(db);
        }
    }

    public long addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());

        long newRowId = db.insert(TABLE_PRODUCTS, null, values);

        db.close();

        return newRowId;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int priceColumnIndex = cursor.getColumnIndex(COLUMN_PRICE);

            do {
                int id = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String description = cursor.getString(descriptionColumnIndex);
                double price = cursor.getDouble(priceColumnIndex);

                if (id != -1 && name != null && description != null && price >= 0) {
                    Product product = new Product(id, name, description, price);
                    productList.add(product);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productList;
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());

        int rowsAffected = db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});

        db.close();

        return rowsAffected;
    }

    public int deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsAffected = db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(productId)});

        db.close();

        return rowsAffected;
    }
}
