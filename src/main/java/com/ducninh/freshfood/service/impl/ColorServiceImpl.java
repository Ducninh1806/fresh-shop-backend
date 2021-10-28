package com.ducninh.freshfood.service.impl;

import com.ducninh.freshfood.dto.ColorDTO;
import com.ducninh.freshfood.exception.ErrorCode;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.Color;
import com.ducninh.freshfood.repository.ColorRepository;
import com.ducninh.freshfood.security.SecurityUtil;
import com.ducninh.freshfood.service.ColorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColorServiceImpl implements ColorService {
    private static final Logger logger = LoggerFactory.getLogger(ColorServiceImpl.class);

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ColorDTO> findByAll() {
        logger.info("Service info about findAll Product");
        return colorRepository.findAll().stream().map(color -> modelMapper.map(color, ColorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ColorDTO findById(Long id) throws LogicException {
        logger.info("Service info about get detail color :{}", id);
        Optional<Color> color = colorRepository.findById(id);
        if (!color.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        return modelMapper.map(color.get(), ColorDTO.class);
    }

    @Override
    public void create(ColorDTO colorDTO) throws LogicException {
        logger.info("Service info about create color :{}", colorDTO);

        Optional<Color> colorOptional = colorRepository.findByName(colorDTO.getName());
        if (colorOptional.isPresent()) {
            throw new LogicException(ErrorCode.NAME_EXISTED);
        }
        Color color = modelMapper.map(colorDTO, Color.class);
        color.setCreatedBy(SecurityUtil.getCurrentUserEmailLogin());
        colorRepository.save(color);
    }

    @Override
    public void update(ColorDTO colorDTO) throws LogicException {
        logger.info("Service info about update color :{}", colorDTO);

        Optional<Color> colorOptional = colorRepository.findById(colorDTO.getId());
        if (!colorOptional.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        Color color = colorOptional.get();
        color.setName(colorDTO.getName());
        color.setDescription(colorDTO.getDescription());
        color.setImage(colorDTO.getImage());
        color.setLastModifiedBy(SecurityUtil.getCurrentUserEmailLogin());
        colorRepository.save(color);
    }

    @Override
    public void delete(Long id) throws LogicException {
        logger.info("Service info about delete color :{}", id);

        Optional<Color> color = colorRepository.findById(id);
        if (!color.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        colorRepository.delete(color.get());
    }
}
