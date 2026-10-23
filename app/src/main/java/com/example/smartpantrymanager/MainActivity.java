package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PantryDatabase database;
    private TextView pantryCountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new PantryDatabase(this);

        // Add the sample recipes if the database is empty.
        new RecipeSeeder(database).seedRecipes();

        pantryCountText = findViewById(R.id.pantryCountText);

        Button viewPantryButton =
                findViewById(R.id.viewPantryButton);

        Button suggestRecipesButton =
                findViewById(R.id.suggestRecipesButton);

        Button settingsButton =
                findViewById(R.id.settingsButton);

        // Open the pantry screen.
        viewPantryButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    PantryActivity.class
            );
            startActivity(intent);
        });

        // Open the suggested recipes screen.
        suggestRecipesButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );
            startActivity(intent);
        });

        // Open the settings screen.
        settingsButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(
                        MainActivity.this,
                        SettingsActivity.class
                );
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(
                        MainActivity.this,
                        "Settings are not available yet.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (database != null && pantryCountText != null) {
            updatePantryCount();
        }
    }

    private void updatePantryCount() {
        int count = 0;

        try (Cursor cursor = database.getAllIngredients()) {
            count = cursor.getCount();
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Could not load pantry information.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        String label = count == 1
                ? "ingredient stored"
                : "ingredients stored";

        pantryCountText.setText(
                "Your pantry\n" + count + " " + label
        );
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