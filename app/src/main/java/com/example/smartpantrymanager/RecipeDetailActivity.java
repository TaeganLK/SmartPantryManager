package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    private PantryDatabase database;

    private TextView recipeNameText;
    private TextView recipeDescriptionText;
    private TextView ingredientsText;
    private TextView instructionsText;

    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeNameText = findViewById(R.id.recipeNameText);
        recipeDescriptionText =
                findViewById(R.id.recipeDescriptionText);
        ingredientsText = findViewById(R.id.ingredientsText);
        instructionsText = findViewById(R.id.instructionsText);

        Button backButton = findViewById(R.id.backButton);

        database = new PantryDatabase(this);

        recipeId = getIntent().getIntExtra(
                EXTRA_RECIPE_ID, -1);

        backButton.setOnClickListener(v -> finish());

        if (recipeId == -1) {
            Toast.makeText(
                    this,
                    "Recipe could not be found.",
                    Toast.LENGTH_SHORT
            ).show();
            finish();
            return;
        }

        loadRecipeDetails();
    }

    private void loadRecipeDetails() {
        boolean found = false;

        try (Cursor cursor = database.getAllRecipes()) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id"));

                if (id == recipeId) {
                    String name = cursor.getString(
                            cursor.getColumnIndexOrThrow("name"));

                    String description = cursor.getString(
                            cursor.getColumnIndexOrThrow("description"));

                    String instructions = cursor.getString(
                            cursor.getColumnIndexOrThrow("instructions"));

                    recipeNameText.setText(name);
                    recipeDescriptionText.setText(
                            description == null
                                    ? ""
                                    : description);

                    instructionsText.setText(
                            instructions == null
                                    || instructions.trim().isEmpty()
                                    ? "No preparation instructions available."
                                    : instructions);

                    found = true;
                    break;
                }
            }
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Could not load recipe details.",
                    Toast.LENGTH_SHORT
            ).show();
            finish();
            return;
        }

        if (!found) {
            Toast.makeText(
                    this,
                    "Recipe could not be found.",
                    Toast.LENGTH_SHORT
            ).show();
            finish();
            return;
        }

        loadRecipeIngredients();
    }

    private void loadRecipeIngredients() {
        StringBuilder ingredients = new StringBuilder();

        try (Cursor cursor =
                     database.getRecipeIngredients(recipeId)) {

            while (cursor.moveToNext()) {
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                "ingredient_name"));

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(
                                "quantity"));

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit"));

                if (ingredients.length() > 0) {
                    ingredients.append("\n");
                }

                ingredients.append("• ")
                        .append(formatQuantity(quantity))
                        .append(" ")
                        .append(unit)
                        .append(" ")
                        .append(name);
            }
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Could not load recipe ingredients.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        if (ingredients.length() == 0) {
            ingredientsText.setText(
                    "No ingredients listed.");
        } else {
            ingredientsText.setText(ingredients.toString());
        }
    }

    private String formatQuantity(double quantity) {
        if (quantity == Math.floor(quantity)) {
            return String.valueOf((long) quantity);
        }

        return String.valueOf(quantity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (database != null) {
            database.close();
            database = null;
        }
    }
}
