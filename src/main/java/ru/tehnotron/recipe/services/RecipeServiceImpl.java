package ru.tehnotron.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tehnotron.recipe.domain.Recipe;
import ru.tehnotron.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        var rslSet = new HashSet<Recipe>();
        recipeRepository.findAll().forEach(rslSet::add);
        return rslSet;
    }
}
