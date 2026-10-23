package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "ingredient_id";
    public static final String EXTRA_NAME = "ingredient_name";
    public static final String EXTRA_QUANTITY = "ingredient_quantity";
    public static final String EXTRA_UNIT = "ingredient_unit";
    public static final String EXTRA_EXPIRY = "ingredient_expiry";

    private PantryDatabase database;
    private EditText nameInput;
    private EditText quantityInput;
    private EditText unitInput;
    private EditText expiryInput;
    private int ingredientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient);

        database = new PantryDatabase(this);

        nameInput = findViewById(R.id.editIngredientNameInput);
        quantityInput = findViewById(R.id.editIngredientQuantityInput);
        unitInput = findViewById(R.id.editIngredientUnitInput);
        expiryInput = findViewById(R.id.editIngredientExpiryInput);

        ingredientId = getIntent().getIntExtra(EXTRA_ID, -1);

        if (ingredientId == -1) {
            Toast.makeText(this, "Ingredient not found.",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        nameInput.setText(getIntent().getStringExtra(EXTRA_NAME));

        double quantity = getIntent().getDoubleExtra(
                EXTRA_QUANTITY, 0);
        quantityInput.setText(String.valueOf(quantity));

        unitInput.setText(getIntent().getStringExtra(EXTRA_UNIT));

        String expiry = getIntent().getStringExtra(EXTRA_EXPIRY);
        expiryInput.setText(expiry == null ? "" : expiry);

        Button updateButton =
                findViewById(R.id.updateIngredientButton);
        Button cancelButton =
                findViewById(R.id.cancelEditButton);

        updateButton.setOnClickListener(v -> updateIngredient());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void updateIngredient() {
        String name = nameInput.getText().toString().trim();
        String quantityText =
                quantityInput.getText().toString().trim();
        String unit = unitInput.getText().toString().trim();
        String expiry = expiryInput.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty()
                || unit.isEmpty()) {
            Toast.makeText(this,
                    "Name, quantity and unit are required.",
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

        int rowsUpdated = database.updateIngredient(
                ingredientId, name, quantity, unit,
                expiry.isEmpty() ? null : expiry
        );

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Ingredient updated!",
                    Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Could not update ingredient.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}