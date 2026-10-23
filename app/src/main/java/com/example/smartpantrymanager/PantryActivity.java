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
import android.app.AlertDialog;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity {

    private PantryDatabase database;
    private ListView ingredientListView;
    private TextView emptyPantryText;

    private final ArrayList<String> ingredients = new ArrayList<>();
    private final ArrayList<Integer> ingredientIds = new ArrayList<>();
    private final ArrayList<String> ingredientNames = new ArrayList<>();
    private final ArrayList<Double> ingredientQuantities = new ArrayList<>();
    private final ArrayList<String> ingredientUnits = new ArrayList<>();
    private final ArrayList<String> ingredientExpiries = new ArrayList<>();

    private ArrayAdapter<String> adapter;
    private ActivityResultLauncher<Intent> editIngredientLauncher;
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

        editIngredientLauncher = registerForActivityResult(
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
                (parent, view, position, id) ->
                        editIngredient(position)
        );

        ingredientListView.setOnItemLongClickListener(
                (parent, view, position, id) -> {
                    confirmDelete(position);
                    return true;
                }
        );

        loadIngredients();
    }

    private void loadIngredients() {
        ingredients.clear();
        ingredientIds.clear();
        ingredientNames.clear();
        ingredientQuantities.clear();
        ingredientUnits.clear();
        ingredientExpiries.clear();

        try (Cursor cursor = database.getAllIngredients()) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name"));
                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity"));
                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit"));

                int expiryIndex =
                        cursor.getColumnIndexOrThrow("expiry_date");
                String expiry = cursor.isNull(expiryIndex)
                        ? "" : cursor.getString(expiryIndex);

                ingredientIds.add(id);
                ingredientNames.add(name);
                ingredientQuantities.add(quantity);
                ingredientUnits.add(unit);
                ingredientExpiries.add(expiry);

                String display = name + " — " + quantity + " " + unit;

                if (!expiry.isEmpty()) {
                    display += "\nExpires: " + expiry;
                }

                ingredients.add(display);
            }
        } catch (Exception e) {
            Toast.makeText(this,
                    "Could not load pantry ingredients.",
                    Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();

        boolean isEmpty = ingredients.isEmpty();
        emptyPantryText.setVisibility(
                isEmpty ? View.VISIBLE : View.GONE);
        ingredientListView.setVisibility(
                isEmpty ? View.GONE : View.VISIBLE);
    }

    private void editIngredient(int position) {
        Intent intent = new Intent(
                PantryActivity.this,
                EditIngredientActivity.class
        );

        intent.putExtra(EditIngredientActivity.EXTRA_ID,
                ingredientIds.get(position));
        intent.putExtra(EditIngredientActivity.EXTRA_NAME,
                ingredientNames.get(position));
        intent.putExtra(EditIngredientActivity.EXTRA_QUANTITY,
                ingredientQuantities.get(position));
        intent.putExtra(EditIngredientActivity.EXTRA_UNIT,
                ingredientUnits.get(position));
        intent.putExtra(EditIngredientActivity.EXTRA_EXPIRY,
                ingredientExpiries.get(position));

        editIngredientLauncher.launch(intent);
    }

    private void confirmDelete(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete ingredient")
                .setMessage("Remove "
                        + ingredientNames.get(position)
                        + " from your pantry?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    int id = ingredientIds.get(position);
                    int rowsDeleted = database.deleteIngredient(id);

                    if (rowsDeleted > 0) {
                        Toast.makeText(this,
                                "Ingredient deleted.",
                                Toast.LENGTH_SHORT).show();
                        loadIngredients();
                    } else {
                        Toast.makeText(this,
                                "Could not delete ingredient.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (database != null) {
            database.close();
        }
    }
}