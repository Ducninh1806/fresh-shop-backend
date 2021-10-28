package com.ducninh.freshfood.service;

import com.ducninh.freshfood.dto.ColorDTO;
import com.ducninh.freshfood.exception.LogicException;

import java.util.List;

public interface ColorService {
    List<ColorDTO> findByAll();
    ColorDTO findById(Long id) throws LogicException;
    void create(ColorDTO colorDTO) throws LogicException;
    void update(ColorDTO colorDTO) throws LogicException;
    void delete(Long id) throws LogicException;

}
