package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private PantryDatabase database;
    private ListView recipesList;
    private TextView noRecipesText;

    private final ArrayList<String> recipeNames = new ArrayList<>();
    private final ArrayList<Integer> recipeIds = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        database = new PantryDatabase(this);

        recipesList = findViewById(R.id.suggestedRecipesList);
        noRecipesText = findViewById(R.id.noRecipesText);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                recipeNames
        );

        recipesList.setAdapter(adapter);

        recipesList.setOnItemClickListener((parent, view, position, id) -> {
            int selectedRecipeId = recipeIds.get(position);

            android.content.Intent intent =
                    new android.content.Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

            intent.putExtra(
                    RecipeDetailActivity.EXTRA_RECIPE_ID,
                    selectedRecipeId
            );

            startActivity(intent);
        });

        loadMatchingRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (database != null && adapter != null) {
            loadMatchingRecipes();
        }
    }

    private void loadMatchingRecipes() {
        recipeNames.clear();
        recipeIds.clear();

        try (Cursor cursor = database.getMatchingRecipes()) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id"));

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name"));

                String description = cursor.getString(
                        cursor.getColumnIndexOrThrow("description"));

                recipeIds.add(id);

                if (description != null && !description.isEmpty()) {
                    recipeNames.add(name + "\n" + description);
                } else {
                    recipeNames.add(name);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this,
                    "Could not load matching recipes.",
                    Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();

        boolean empty = recipeNames.isEmpty();

        noRecipesText.setVisibility(
                empty ? View.VISIBLE : View.GONE);

        recipesList.setVisibility(
                empty ? View.GONE : View.VISIBLE);
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