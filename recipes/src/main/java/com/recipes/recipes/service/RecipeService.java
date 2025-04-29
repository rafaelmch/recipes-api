package com.recipes.recipes.service;

import com.recipes.recipes.model.Category;
import com.recipes.recipes.model.Ingredient;
import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.repository.CategoryRepository;
import com.recipes.recipes.repository.IngredientRepository;
import com.recipes.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepo;
    private final CategoryRepository catRepo;
    private final IngredientRepository ingRepo;

    /**
     * Creates a new Recipe, ensuring the category exists
     * and that each ingredient is either looked up or created.
     */
    public Recipe create(Recipe input) {
        // 1️⃣ Ensure the Category is valid:
        Long catId = input.getCategory().getId();
        Category category = catRepo.findById(catId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category not found: " + catId));

        // 2️⃣ Resolve ingredients: either reuse existing or insert new ones
        Set<Ingredient> resolved = input.getIngredients().stream()
                .map(i -> {
                    // assume Ingredient has unique name
                    return ingRepo.findByName(i.getName())
                            .orElseGet(() -> ingRepo.save(
                                    new Ingredient(null, i.getName())
                            ));
                })
                .collect(Collectors.toSet());

        // 3️⃣ Wire everything into a fresh Recipe instance
        Recipe toSave = new Recipe();
        toSave.setTitle(input.getTitle());
        toSave.setInstructions(input.getInstructions());
        toSave.setCategory(category);
        toSave.setIngredients(resolved);

        // 4️⃣ Persist & return
        return recipeRepo.save(toSave);
    }

    /**
     * Fetches a recipe by id or throws 404.
     */
    public Recipe getById(Long id) {
        return recipeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Recipe not found: " + id
                ));
    }

    public Recipe update(Long id, Recipe input) {
        // 1️⃣ Load existing recipe (404 if not found)
        Recipe existing = recipeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found: " + id));

        // 2️⃣ Resolve & validate category
        Long catId = input.getCategory().getId();
        Category category = catRepo.findById(catId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + catId));

        // 3️⃣ Resolve ingredients by name (same logic as create)
        Set<Ingredient> resolved = input.getIngredients().stream()
                .map(i -> ingRepo.findByName(i.getName())
                        .orElseGet(() -> ingRepo.save(new Ingredient(null, i.getName()))))
                .collect(Collectors.toSet());

        // 4️⃣ Apply updates to the loaded entity
        existing.setTitle(input.getTitle());
        existing.setInstructions(input.getInstructions());
        existing.setCategory(category);
        existing.setIngredients(resolved);

        // 5️⃣ Save and return
        return recipeRepo.save(existing);
    }

    /**
     * Delete a Recipe by id. Throws if the recipe isn't there.
     */
    public void delete(Long id) {
        Recipe existing = recipeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found: " + id));
        recipeRepo.delete(existing);
        // or simply: recipeRepo.deleteById(id);
    }

    public Page<Recipe> listAll(
            String category,
            String ingredient,
            Pageable pageable
    ) {
        if (category != null && !category.isBlank()) {
            return recipeRepo.findByCategory(category, pageable);
        }
        if (ingredient != null && !ingredient.isBlank()) {
            return recipeRepo.findByIngredientName(ingredient, pageable);
        }
        return recipeRepo.findAll(pageable);
    }
}