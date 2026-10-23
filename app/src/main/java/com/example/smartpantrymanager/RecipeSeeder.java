package com.example.smartpantrymanager;

import android.database.Cursor;

public class RecipeSeeder {

    private final PantryDatabase database;

    public RecipeSeeder(PantryDatabase database) {
        this.database = database;
    }

    public void seedRecipes() {
        Cursor cursor = database.getAllRecipes();

        try {
            if (cursor.getCount() > 0) {
                return;
            }
        } finally {
            cursor.close();
        }

        // 1
        add("Tomato Omelette",
                "A quick egg and tomato breakfast.",
                "1. Beat the eggs. 2. Chop the tomato. " +
                        "3. Cook together in a pan until the eggs set.",
                new String[]{"eggs", "tomato"},
                new double[]{2, 1},
                new String[]{"pieces", "pieces"});

        // 2
        add("Cheese Toast",
                "Toasted bread topped with cheese.",
                "1. Place cheese on bread. 2. Toast in a pan " +
                        "or toaster oven until the cheese melts.",
                new String[]{"bread", "cheese"},
                new double[]{2, 50},
                new String[]{"slices", "grams"});

        // 3
        add("Banana Pancakes",
                "Simple pancakes with banana.",
                "1. Mash the banana. 2. Mix with eggs and flour. " +
                        "3. Cook small pancakes in a lightly oiled pan.",
                new String[]{"banana", "eggs", "flour"},
                new double[]{1, 2, 100},
                new String[]{"pieces", "pieces", "grams"});

        // 4
        add("Garlic Pasta",
                "Pasta with garlic and olive oil.",
                "1. Boil the pasta. 2. Chop and gently cook the garlic " +
                        "in olive oil. 3. Mix with the drained pasta.",
                new String[]{"pasta", "garlic", "olive oil"},
                new double[]{200, 2, 15},
                new String[]{"grams", "cloves", "ml"});

        // 5
        add("Tomato Pasta",
                "Pasta with a simple tomato sauce.",
                "1. Boil the pasta. 2. Chop and cook the tomatoes. " +
                        "3. Combine and simmer for five minutes.",
                new String[]{"pasta", "tomato", "onion"},
                new double[]{200, 2, 1},
                new String[]{"grams", "pieces", "pieces"});

        // 6
        add("Potato Mash",
                "Creamy mashed potatoes.",
                "1. Peel and chop the potatoes. 2. Boil until soft. " +
                        "3. Mash with milk and butter.",
                new String[]{"potato", "milk", "butter"},
                new double[]{300, 50, 20},
                new String[]{"grams", "ml", "grams"});

        // 7
        add("Fried Rice",
                "A quick rice and vegetable dish.",
                "1. Cook the rice. 2. Chop the carrot and onion. " +
                        "3. Stir-fry the vegetables and rice until hot.",
                new String[]{"rice", "carrot", "onion"},
                new double[]{200, 1, 1},
                new String[]{"grams", "pieces", "pieces"});

        // 8
        add("Apple Oatmeal",
                "Warm oats topped with apple.",
                "1. Simmer oats in milk until soft. " +
                        "2. Chop the apple. 3. Serve together.",
                new String[]{"oats", "milk", "apple"},
                new double[]{50, 200, 1},
                new String[]{"grams", "ml", "pieces"});

        // 9
        add("Peanut Butter Toast",
                "A quick toast with peanut butter.",
                "1. Toast the bread. 2. Spread peanut butter evenly.",
                new String[]{"bread", "peanut butter"},
                new double[]{2, 30},
                new String[]{"slices", "grams"});

        // 10
        add("Chicken Rice Bowl",
                "Chicken served with rice and vegetables.",
                "1. Cook the rice. 2. Cut and cook the chicken thoroughly. " +
                        "3. Cook the carrot and combine.",
                new String[]{"chicken", "rice", "carrot"},
                new double[]{200, 150, 1},
                new String[]{"grams", "grams", "pieces"});

        // 11
        add("Cheese Omelette",
                "Eggs folded around melted cheese.",
                "1. Beat the eggs. 2. Cook in a pan. " +
                        "3. Add cheese and fold the omelette.",
                new String[]{"eggs", "cheese"},
                new double[]{2, 30},
                new String[]{"pieces", "grams"});

        // 12
        add("Carrot Soup",
                "A simple blended carrot soup.",
                "1. Chop carrots and onion. 2. Simmer in water " +
                        "until tender. 3. Blend until smooth.",
                new String[]{"carrot", "onion"},
                new double[]{300, 1},
                new String[]{"grams", "pieces"});

        // 13
        add("Garlic Bread",
                "Bread flavoured with garlic and butter.",
                "1. Mix crushed garlic with softened butter. " +
                        "2. Spread over bread. 3. Bake until golden.",
                new String[]{"bread", "garlic", "butter"},
                new double[]{4, 2, 30},
                new String[]{"slices", "cloves", "grams"});

        // 14
        add("Yogurt Banana Bowl",
                "Banana served with yogurt.",
                "1. Slice the banana. 2. Place in a bowl. " +
                        "3. Add the yogurt and serve.",
                new String[]{"banana", "yogurt"},
                new double[]{1, 150},
                new String[]{"pieces", "grams"});

        // 15
        add("Tuna Sandwich",
                "A simple tuna sandwich.",
                "1. Drain the tuna. 2. Mix with mayonnaise. " +
                        "3. Place between slices of bread.",
                new String[]{"tuna", "bread", "mayonnaise"},
                new double[]{100, 2, 15},
                new String[]{"grams", "slices", "grams"});

        // 16
        add("Tomato Cheese Sandwich",
                "A fresh sandwich with tomato and cheese.",
                "1. Slice the tomato. 2. Layer tomato and cheese " +
                        "between the bread slices. 3. Serve.",
                new String[]{"bread", "tomato", "cheese"},
                new double[]{2, 1, 30},
                new String[]{"slices", "pieces", "grams"});

        // 17
        add("Spinach Omelette",
                "An omelette with cooked spinach.",
                "1. Beat the eggs. 2. Cook the spinach briefly. " +
                        "3. Add eggs and cook until set.",
                new String[]{"eggs", "spinach"},
                new double[]{2, 50},
                new String[]{"pieces", "grams"});

        // 18
        add("Bean Rice Bowl",
                "Rice served with beans and onion.",
                "1. Cook the rice. 2. Cook the onion until soft. " +
                        "3. Add drained beans and heat through. Serve with rice.",
                new String[]{"rice", "beans", "onion"},
                new double[]{150, 150, 1},
                new String[]{"grams", "grams", "pieces"});

        // 19
        add("Apple Yogurt Bowl",
                "Chopped apple served with yogurt and oats.",
                "1. Chop the apple. 2. Spoon yogurt into a bowl. " +
                        "3. Add apple and oats.",
                new String[]{"apple", "yogurt", "oats"},
                new double[]{1, 100, 20},
                new String[]{"pieces", "grams", "grams"});

        // 20
        add("Creamy Mushroom Pasta",
                "Pasta in a creamy mushroom sauce.",
                "1. Boil the pasta. 2. Cook sliced mushrooms. " +
                        "3. Add cream and simmer gently. " +
                        "4. Mix with the drained pasta.",
                new String[]{"pasta", "mushrooms", "cream"},
                new double[]{200, 150, 100},
                new String[]{"grams", "grams", "ml"});
    }

    private void add(String name, String description,
                     String instructions,
                     String[] ingredientNames,
                     double[] quantities,
                     String[] units) {

        long recipeId = database.addRecipe(
                name, description, instructions);

        if (recipeId == -1) {
            return;
        }

        for (int i = 0; i < ingredientNames.length; i++) {
            database.addRecipeIngredient(
                    recipeId,
                    ingredientNames[i],
                    quantities[i],
                    units[i]
            );
        }
    }
}