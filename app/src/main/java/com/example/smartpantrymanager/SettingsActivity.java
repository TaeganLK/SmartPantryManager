
package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SmartPantrySettings";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_NOTES = "notes";

    private EditText usernameInput;
    private EditText notesInput;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences(
                PREFS_NAME, MODE_PRIVATE);

        TextView settingsTitle = findViewById(R.id.settingsTitle);
        usernameInput = findViewById(R.id.usernameInput);
        notesInput = findViewById(R.id.notesInput);

        Button saveButton = findViewById(R.id.saveSettingsButton);
        Button backButton = findViewById(R.id.backButton);

        settingsTitle.setText("Settings & Profile");

        // Load previously saved settings.
        usernameInput.setText(
                preferences.getString(KEY_USERNAME, ""));
        notesInput.setText(
                preferences.getString(KEY_NOTES, ""));

        saveButton.setOnClickListener(v -> saveSettings());

        backButton.setOnClickListener(v -> finish());
    }

    private void saveSettings() {
        String username = usernameInput.getText()
                .toString().trim();

        String notes = notesInput.getText()
                .toString().trim();

        boolean saved = preferences.edit()
                .putString(KEY_USERNAME, username)
                .putString(KEY_NOTES, notes)
                .commit();

        if (saved) {
            Toast.makeText(
                    this,
                    "Settings saved successfully.",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(
                    this,
                    "Could not save settings.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
