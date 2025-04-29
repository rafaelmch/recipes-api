package com.recipes.recipes.service;

import com.recipes.recipes.model.Category;
import com.recipes.recipes.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository catRepo;

    public Category create(Category c) {
        return catRepo.save(c);
    }

    public Page<Category> list(Pageable pg) {
        return catRepo.findAll(pg);
    }

    public Category getById(Long id) {
        return catRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));
    }
}


