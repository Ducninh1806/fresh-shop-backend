package com.ducninh.freshfood.controller;

import com.ducninh.freshfood.dto.ColorDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.service.impl.ColorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {
    private static final Logger logger = LoggerFactory.getLogger(ColorController.class);

    @Autowired
    private ColorServiceImpl colorService;

    @GetMapping
    public ResponseEntity<List<ColorDTO>> findByAll(){
        logger.info("Controller info about findAll color");
        return new ResponseEntity<>(colorService.findByAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorDTO> findById(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about get detail color :{}", id);
        return new ResponseEntity<>(colorService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ColorDTO> create(@RequestBody ColorDTO  colorDTO) throws LogicException {
        logger.info("Controller info about create color :{}", colorDTO);
        colorService.create(colorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ColorDTO> update(@RequestBody ColorDTO colorDTO) throws LogicException {
        logger.info("Controller info about update color :{}", colorDTO);
        colorService.update(colorDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about delete color :{}", id);

        colorService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
