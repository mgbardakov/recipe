package ru.tehnotron.recipe.services;

import ru.tehnotron.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
