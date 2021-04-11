package ru.tehnotron.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.tehnotron.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
