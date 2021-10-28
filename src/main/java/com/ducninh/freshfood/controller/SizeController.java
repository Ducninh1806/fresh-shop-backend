package com.ducninh.freshfood.controller;

import com.ducninh.freshfood.dto.SizeDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.Size;
import com.ducninh.freshfood.service.impl.SizeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size")
public class SizeController {

    private static final Logger logger = LoggerFactory.getLogger(SizeController.class);
    @Autowired
    private SizeServiceImpl sizeService;


    @GetMapping
    public ResponseEntity<List<SizeDTO>> findByAll() {
        logger.info("Controller info about findByAll size");
        return new ResponseEntity<>(sizeService.findByAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizeDTO> findById(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about findById size");
        return new ResponseEntity<>(sizeService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody SizeDTO sizeDTO) throws LogicException {
        logger.info("Controller info about create size {}", sizeDTO);
        sizeService.create(sizeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody SizeDTO sizeDTO) throws LogicException {
        logger.info("Controller info about update size {}", sizeDTO);
        sizeService.update(sizeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about delete size {}", id);
        sizeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}