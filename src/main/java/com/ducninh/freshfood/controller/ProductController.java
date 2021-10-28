package com.ducninh.freshfood.controller;

import com.ducninh.freshfood.dto.ProductDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.service.impl.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findByAll() {
        logger.info("Controller info about findAll Product");
        List<ProductDTO> productDTOS = productService.findByAll();
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about findById Product {}", id);

        ProductDTO productDTO = productService.findById(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) throws LogicException {
        logger.info("Controller info about create Product {}", productDTO);

        productService.create(productDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) throws LogicException {
        logger.info("Controller info about update Product {}", productDTO);

        productService.update(productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about delete Product {}", id);

        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
