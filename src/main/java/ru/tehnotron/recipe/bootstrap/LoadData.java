package ru.tehnotron.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.tehnotron.recipe.domain.Difficulty;
import ru.tehnotron.recipe.domain.Ingredient;
import ru.tehnotron.recipe.domain.Notes;
import ru.tehnotron.recipe.domain.Recipe;
import ru.tehnotron.recipe.repositories.CategoryRepository;
import ru.tehnotron.recipe.repositories.RecipeRepository;
import ru.tehnotron.recipe.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class LoadData implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public LoadData(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        var list = getRecipes();
        recipeRepository.saveAll(list);
    }

    private <T> T getOrThrow(Optional<T> object) {
        return object.orElseThrow(() -> new RuntimeException("Excepted element not found"));
    }

    private List<Recipe> getRecipes() {
        List<Recipe> rslList = new ArrayList<>(2);
        //defining units of measure
        var sep = System.lineSeparator();

        var each = getOrThrow(unitOfMeasureRepository.findByDescription("Each"));
        var teaspoon = getOrThrow(unitOfMeasureRepository.findByDescription("Teaspoon"));
        var tablespoon = getOrThrow(unitOfMeasureRepository.findByDescription("Tablespoon"));
        var cup = getOrThrow(unitOfMeasureRepository.findByDescription("Cup"));
        var gramme = getOrThrow(unitOfMeasureRepository.findByDescription("Gramme"));
        var dash = getOrThrow(unitOfMeasureRepository.findByDescription("Dash"));

        //defining categories
        var americanCategory = getOrThrow(categoryRepository.findByDescription("American"));
        var italianCategory = getOrThrow(categoryRepository.findByDescription("Italian"));
        var russianCategory = getOrThrow(categoryRepository.findByDescription("Russian"));
        var japaneseCategory = getOrThrow(categoryRepository.findByDescription("Japanese"));
        var mexicanCategory = getOrThrow(categoryRepository.findByDescription("Mexican"));

        var guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole Recipe");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setServings(2);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections("1. Cut the avocado, remove flesh:" + sep +
                "Cut the avocados in half." + sep +
                "Remove the pit. Score the inside of the avocado with a blunt knife and scoop out" + sep +
                "the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." + sep +
                "2. Mash with a fork:" + sep +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" + sep +
                "3. Add salt, lime juice, and the rest:" + sep +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance " + sep +
                "to the richness of the avocado and will help delay the avocados from turning brown." + sep +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness." + sep +
                "So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness." + sep +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." + sep +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        var guacNotes = new Notes();
        guacNotes.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
        guacamoleRecipe.setNotes(guacNotes);
        guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), each))
                       .addIngredient(new Ingredient("salt", new BigDecimal(".5"), teaspoon))
                       .addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoon))
                       .addIngredient(new Ingredient("minced red onion or thinly sliced red onion", new BigDecimal(2), tablespoon))
                       .addIngredient(new Ingredient("serrano chilles, seeds and stems removed, minced", new BigDecimal(2), each))
                       .addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoon))
                       .addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dash))
                       .addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), each));
        guacamoleRecipe.getCategories().add(mexicanCategory);

        rslList.add(guacamoleRecipe);

        var thinPanCakesRecipe = new Recipe();
        thinPanCakesRecipe.setDescription("Russian pancakes recipe");
        thinPanCakesRecipe.setPrepTime(15);
        thinPanCakesRecipe.setCookTime(10);
        thinPanCakesRecipe.setServings(2);
        thinPanCakesRecipe.setDifficulty(Difficulty.EASY);
        thinPanCakesRecipe.setDirections("1. Prepare ingredients" + sep +
                "2. Mix eggs, sugar and salt" + sep +
                "3. Add to the mixture cup half of a milk, mix" + sep +
                "4. Add flour to the mixture cup, mix" + sep +
                "5. Add all milk and butter to the mixture cup, mix" + sep +
                "6. Fry on the pan");
        var pancakeNotes = new Notes();
        pancakeNotes.setRecipeNotes("Use wisely");
        thinPanCakesRecipe.setNotes(pancakeNotes);
        thinPanCakesRecipe.addIngredient(new Ingredient("Milk", new BigDecimal(2), cup))
                          .addIngredient(new Ingredient("Egg", new BigDecimal(3), each))
                          .addIngredient(new Ingredient("Flour", new BigDecimal(200), gramme))
                          .addIngredient(new Ingredient("Butter", new BigDecimal(2), tablespoon))
                          .addIngredient(new Ingredient("Sugar", new BigDecimal(2), tablespoon))
                          .addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoon));
        thinPanCakesRecipe.getCategories().add(russianCategory);

        rslList.add(thinPanCakesRecipe);

        return rslList;
    }
}

