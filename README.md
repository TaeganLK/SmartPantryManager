# Smart Pantry Manager

Smart Pantry Manager is an Android mobile application developed in Java for the Mobile App Development 700 assignment at Richfield. It helps users manage pantry ingredients and find recipes based on the ingredients they have available.

## Features

- Pantry management: Add, view, edit and delete ingredients.
- Ingredient tracking: Store ingredient names, quantities, units and optional expiry dates.
- Recipe suggestions: Display recipes only when all required ingredients are available in sufficient quantities.
- Recipe details: View recipe descriptions, required ingredients and preparation instructions.
- Settings: Save a profile name and pantry notes.
- Local data storage: Use SQLite to store pantry ingredients and recipes.
- Data persistence: Keep saved pantry information and settings when the app is closed and reopened.
- Input validation: Validate ingredient details before saving them.

## Technologies Used

- Java
- Android Studio
- Android SDK
- SQLite
- XML layouts
- Git and GitHub

## How Recipe Matching Works

The application compares each recipe's required ingredients with the ingredients stored in the user's pantry. A recipe is suggested only if every required ingredient is available in the required quantity.

Ingredient names and units must match, and the pantry quantity must be equal to or greater than the recipe's requirement. Recipes that do not meet these conditions are excluded from the suggested recipes list.

## Main Screens

1. Home screen
2. Pantry list
3. Add and edit ingredient screens
4. Suggested recipes screen
5. Recipe detail screen
6. Settings screen

## Installation and Setup

1. Download or clone this repository.
2. Open the project in Android Studio.
3. Allow Android Studio to sync the Gradle project and install any required SDK components.
4. Connect an Android device with USB debugging enabled or start an Android emulator.
5. Build and run the application.

## Database

The application uses a local SQLite database named `smart_pantry.db`. It stores ingredients, recipes and the ingredients associated with each recipe. Profile information and pantry notes are saved using Android SharedPreferences.

## Testing

The following functionality has been tested on an Android device:

- Opening recipe details from the suggested recipes list.
- Excluding a recipe when the pantry contains an insufficient quantity of an ingredient.
- Saving and reopening settings.
- Retaining pantry data after closing and reopening the application.

## Project Repository

GitHub: https://github.com/TaeganLK/SmartPantryManager

## Author

Developed as an individual academic project for Richfield's Mobile App Development 700 module by Taegan Kapoock.
