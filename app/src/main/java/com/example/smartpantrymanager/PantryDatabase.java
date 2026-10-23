package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PantryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

    public PantryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createIngredientsTable(db);
        createRecipesTables(db);
    }

    private void createIngredientsTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "expiry_date TEXT)"
        );
    }

    private void createRecipesTables(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE recipes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "description TEXT, " +
                        "instructions TEXT NOT NULL)"
        );

        db.execSQL(
                "CREATE TABLE recipe_ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "ingredient_name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "FOREIGN KEY(recipe_id) REFERENCES recipes(id) " +
                        "ON DELETE CASCADE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            createRecipesTables(db);
        }
    }

    // Add an ingredient to the pantry.
    public long addIngredient(String name, double quantity,
                              String unit, String expiryDate) {
        ContentValues values = new ContentValues();
        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry_date", expiryDate);

        return getWritableDatabase().insert(
                "ingredients", null, values);
    }

    // Get all pantry ingredients.
    public Cursor getAllIngredients() {
        return getReadableDatabase().query(
                "ingredients", null, null, null,
                null, null, "name ASC");
    }

    // Update an existing ingredient.
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
                new String[]{String.valueOf(id)});
    }

    // Delete an ingredient.
    public int deleteIngredient(int id) {
        return getWritableDatabase().delete(
                "ingredients", "id = ?",
                new String[]{String.valueOf(id)});
    }

    // Add a recipe.
    public long addRecipe(String name, String description,
                          String instructions) {
        ContentValues values = new ContentValues();
        values.put("name", name.trim());
        values.put("description", description);
        values.put("instructions", instructions);

        return getWritableDatabase().insert(
                "recipes", null, values);
    }

    // Add one required ingredient to a recipe.
    public long addRecipeIngredient(long recipeId,
                                    String ingredientName,
                                    double quantity,
                                    String unit) {
        ContentValues values = new ContentValues();
        values.put("recipe_id", recipeId);
        values.put("ingredient_name",
                ingredientName.trim().toLowerCase());
        values.put("quantity", quantity);
        values.put("unit", unit.trim().toLowerCase());

        return getWritableDatabase().insert(
                "recipe_ingredients", null, values);
    }

    // Return all recipes.
    public Cursor getAllRecipes() {
        return getReadableDatabase().query(
                "recipes", null, null, null,
                null, null, "name ASC");
    }

    // Return the required ingredients for one recipe.
    public Cursor getRecipeIngredients(long recipeId) {
        return getReadableDatabase().query(
                "recipe_ingredients", null,
                "recipe_id = ?",
                new String[]{String.valueOf(recipeId)},
                null, null, "ingredient_name ASC");
    }

    // Find recipes whose every required ingredient is available.
    public Cursor getMatchingRecipes() {
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT r.* FROM recipes r " +
                        "WHERE EXISTS (" +
                        "  SELECT 1 FROM recipe_ingredients ri " +
                        "  WHERE ri.recipe_id = r.id" +
                        ") " +
                        "AND NOT EXISTS (" +
                        "  SELECT 1 FROM recipe_ingredients ri " +
                        "  WHERE ri.recipe_id = r.id " +
                        "  AND NOT EXISTS (" +
                        "    SELECT 1 FROM ingredients i " +
                        "    WHERE LOWER(TRIM(i.name)) = " +
                        "          LOWER(TRIM(ri.ingredient_name)) " +
                        "    AND LOWER(TRIM(i.unit)) = " +
                        "          LOWER(TRIM(ri.unit)) " +
                        "    AND i.quantity >= ri.quantity" +
                        "  )" +
                        ") " +
                        "ORDER BY r.name ASC",
                null);
    }
}