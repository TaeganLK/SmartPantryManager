package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PantryDatabase database;
    private TextView pantryCountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new PantryDatabase(this);

        pantryCountText = findViewById(R.id.pantryCountText);

        Button viewPantryButton =
                findViewById(R.id.viewPantryButton);
        Button suggestRecipesButton =
                findViewById(R.id.suggestRecipesButton);
        Button settingsButton =
                findViewById(R.id.settingsButton);

        viewPantryButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    PantryActivity.class
            );
            startActivity(intent);
        });

        suggestRecipesButton.setOnClickListener(v -> {
            // Recipe suggestions will be implemented next.
        });

        settingsButton.setOnClickListener(v -> {
            // Settings will be implemented next.
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

        try (android.database.Cursor cursor =
                     database.getAllIngredients()) {
            count = cursor.getCount();
        }

        String label = count == 1 ? "ingredient stored"
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