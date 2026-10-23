package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText quantityInput;
    private EditText unitInput;
    private EditText expiryInput;
    private PantryDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        database = new PantryDatabase(this);

        nameInput = findViewById(R.id.ingredientNameInput);
        quantityInput = findViewById(R.id.ingredientQuantityInput);
        unitInput = findViewById(R.id.ingredientUnitInput);
        expiryInput = findViewById(R.id.ingredientExpiryInput);

        Button saveButton = findViewById(R.id.saveIngredientButton);
        Button cancelButton = findViewById(R.id.cancelIngredientButton);

        saveButton.setOnClickListener(v -> saveIngredient());

        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveIngredient() {
        String name = nameInput.getText().toString().trim();
        String quantityText =
                quantityInput.getText().toString().trim();
        String unit = unitInput.getText().toString().trim();
        String expiry = expiryInput.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty()
                || unit.isEmpty()) {
            Toast.makeText(this,
                    "Please enter the name, quantity and unit.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            quantityInput.setError("Enter a valid quantity");
            return;
        }

        if (!Double.isFinite(quantity) || quantity <= 0) {
            quantityInput.setError(
                    "Quantity must be greater than zero");
            return;
        }

        if (!expiry.isEmpty()
                && !expiry.matches("\\d{4}-\\d{2}-\\d{2}")) {
            expiryInput.setError("Use YYYY-MM-DD");
            return;
        }

        long result = database.addIngredient(
                name, quantity, unit,
                expiry.isEmpty() ? null : expiry
        );

        if (result != -1) {
            Toast.makeText(this,
                    "Ingredient saved!",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,
                    "Could not save ingredient.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}