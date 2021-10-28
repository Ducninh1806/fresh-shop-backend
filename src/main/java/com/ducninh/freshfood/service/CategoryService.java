package com.ducninh.freshfood.service;

import com.ducninh.freshfood.dto.CategoryDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findByAll();
    CategoryDTO findById(Long id) throws LogicException;
    void create(CategoryDTO categoryDTO) throws LogicException;
    void update(CategoryDTO categoryDTO) throws LogicException;
    void delete(Long id) throws LogicException;
}
