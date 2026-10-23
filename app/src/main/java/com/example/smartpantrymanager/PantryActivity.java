package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity {

    private PantryDatabase database;
    private ListView ingredientListView;
    private TextView emptyPantryText;
    private ArrayAdapter<String> adapter;
    private final ArrayList<String> ingredients = new ArrayList<>();

    private ActivityResultLauncher<Intent> addIngredientLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        database = new PantryDatabase(this);

        ingredientListView = findViewById(R.id.ingredientListView);
        emptyPantryText = findViewById(R.id.emptyPantryText);
        Button addIngredientButton =
                findViewById(R.id.addIngredientButton);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                ingredients
        );

        ingredientListView.setAdapter(adapter);

        addIngredientLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> loadIngredients()
        );

        addIngredientButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    AddIngredientActivity.class
            );
            addIngredientLauncher.launch(intent);
        });

        ingredientListView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    Toast.makeText(
                            this,
                            "Ingredient: " + ingredients.get(position),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        loadIngredients();
    }

    private void loadIngredients() {
        ingredients.clear();

        try (Cursor cursor = database.getAllIngredients()) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name"));

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity"));

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit"));

                ingredients.add(
                        name + " — " + quantity + " " + unit
                );
            }
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Could not load pantry ingredients.",
                    Toast.LENGTH_SHORT
            ).show();
        }

        adapter.notifyDataSetChanged();

        boolean isEmpty = ingredients.isEmpty();
        emptyPantryText.setVisibility(
                isEmpty ? View.VISIBLE : View.GONE);
        ingredientListView.setVisibility(
                isEmpty ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}