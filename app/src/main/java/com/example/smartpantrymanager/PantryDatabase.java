
package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PantryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    public PantryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "expiry_date TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        // Add database upgrade logic here when needed.
    }

    public long addIngredient(String name, double quantity,
                              String unit, String expiryDate) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry_date", expiryDate);

        return db.insert("ingredients", null, values);
    }

    public Cursor getAllIngredients() {
        return getReadableDatabase().query(
                "ingredients", null, null, null,
                null, null, "name ASC"
        );
    }

    public int updateIngredient(int id, String name,
                                double quantity, String unit,
                                String expiryDate) {
        ContentValues values = new ContentValues();
        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry_date", expiryDate);

        return getWritableDatabase().update(
                "ingredients", values, "id = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public int deleteIngredient(int id) {
        return getWritableDatabase().delete(
                "ingredients", "id = ?",
                new String[]{String.valueOf(id)}
        );
    }
}
