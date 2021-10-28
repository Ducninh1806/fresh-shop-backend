package com.ducninh.freshfood.repository;

import com.ducninh.freshfood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = "SELECT p.* FROM Product p WHERE p.name = :name")
    Optional<Product> findByName (@Param("name") String name);
}
