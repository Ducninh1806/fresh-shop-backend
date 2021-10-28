package com.ducninh.freshfood.service;

import com.ducninh.freshfood.dto.ProductDTO;
import com.ducninh.freshfood.exception.LogicException;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findByAll();
    ProductDTO findById(Long id) throws LogicException;
    void create(ProductDTO productDTO) throws LogicException;
    void update(ProductDTO productDTO) throws LogicException;
    void delete(Long id) throws LogicException;
}
