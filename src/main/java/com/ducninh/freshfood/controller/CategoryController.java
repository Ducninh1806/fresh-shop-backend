package com.ducninh.freshfood.controller;

import com.ducninh.freshfood.dto.CategoryDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.Category;
import com.ducninh.freshfood.service.impl.CategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findByAll(){
        logger.info("Controller info about findAll category");
        return new ResponseEntity<>(categoryService.findByAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about get detail category :{}", id);
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CategoryDTO categoryDTO) throws LogicException {
        logger.info("Controller info about create category :{}", categoryDTO);

        categoryService.create(categoryDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody CategoryDTO categoryDTO) throws LogicException {
        logger.info("Controller info about update category :{}", categoryDTO);
        categoryService.update(categoryDTO);
        return  new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about delete category :{}", id);
        categoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
