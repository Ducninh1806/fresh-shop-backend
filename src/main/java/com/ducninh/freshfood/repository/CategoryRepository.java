package com.ducninh.freshfood.repository;

import com.ducninh.freshfood.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * from Category c where c.id = :id")
    Category findByIdName(@Param("id") Long id);
}
