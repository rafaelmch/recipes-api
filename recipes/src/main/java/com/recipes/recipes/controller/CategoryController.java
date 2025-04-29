package com.recipes.recipes.controller;

import com.recipes.recipes.model.Category;
import com.recipes.recipes.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService catSvc;

    @GetMapping
    public Page<Category> list(@PageableDefault(size=10) Pageable pg) {
        return catSvc.list(pg);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@Valid @RequestBody Category payload) {
        return catSvc.create(payload);
    }

    @GetMapping("/{id}")
    public Category get(@PathVariable Long id) {
        return catSvc.getById(id);
    }
}

