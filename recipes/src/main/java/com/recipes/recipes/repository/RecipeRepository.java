package com.recipes.recipes.repository;

import com.recipes.recipes.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findByCategoryName(String category, Pageable pg);
    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE i.name = :name")
    Page<Recipe> findByIngredientName(@Param("name") String name, Pageable pg);
    @Query("""
      SELECT r
        FROM Recipe r
       WHERE r.category.name = :category
    """)
    Page<Recipe> findByCategory(@Param("category") String category, Pageable pageable);

}
