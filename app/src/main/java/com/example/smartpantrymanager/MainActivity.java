
package com.example.smartpantrymanager;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button viewPantryButton;
    private Button suggestRecipesButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPantryButton = findViewById(R.id.viewPantryButton);
        suggestRecipesButton = findViewById(R.id.suggestRecipesButton);
        settingsButton = findViewById(R.id.settingsButton);

        viewPantryButton.setOnClickListener(v ->
                Toast.makeText(this,
                        "Pantry screen coming next!",
                        Toast.LENGTH_SHORT).show());

        suggestRecipesButton.setOnClickListener(v ->
                Toast.makeText(this,
                        "Recipe suggestions coming next!",
                        Toast.LENGTH_SHORT).show());

        settingsButton.setOnClickListener(v ->
                Toast.makeText(this,
                        "Settings screen coming next!",
                        Toast.LENGTH_SHORT).show());
    }
}
