package com.ducninh.freshfood.service;

import com.ducninh.freshfood.dto.SizeDTO;
import com.ducninh.freshfood.exception.LogicException;

import java.util.List;

public interface SizeService {

    List<SizeDTO> findByAll();
    SizeDTO findById(Long id) throws LogicException;
    void create(SizeDTO sizeDTO) throws LogicException;
    void update(SizeDTO sizeDTO) throws LogicException;
    void delete(Long id) throws LogicException;

}
