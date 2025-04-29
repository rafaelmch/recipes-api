package com.recipes.recipes.controller;

import com.recipes.recipes.model.Recipe;
import com.recipes.recipes.service.RecipeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    /**
     * GET /recipes?page=0&size=10
     * GET /recipes?category=Dessert
     * GET /recipes?ingredient=Sugar
     */
    @GetMapping
    public Page<Recipe> list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String ingredient,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return recipeService.listAll(category, ingredient, pageable);
    }

    /** GET /recipes/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getOne(@PathVariable Long id) {
        Recipe recipe = recipeService.getById(id);
        return ResponseEntity.ok(recipe);
    }

    /** POST /recipes */
    @PostMapping
    public ResponseEntity<Recipe> create(@Valid @RequestBody Recipe payload) {
        Recipe created = recipeService.create(payload);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /** PUT /recipes/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(
            @PathVariable Long id,
            @Valid @RequestBody Recipe payload
    ) {
        Recipe updated = recipeService.update(id, payload);
        return ResponseEntity.ok(updated);
    }

    /** DELETE /recipes/{id} */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }
}
