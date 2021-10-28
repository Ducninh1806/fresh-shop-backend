package com.ducninh.freshfood.service.impl;

import com.ducninh.freshfood.dto.SizeDTO;
import com.ducninh.freshfood.exception.ErrorCode;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.Size;
import com.ducninh.freshfood.repository.SizeRepository;
import com.ducninh.freshfood.security.SecurityUtil;
import com.ducninh.freshfood.service.SizeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService {
    private static final Logger logger = LoggerFactory.getLogger(SizeServiceImpl.class);

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SizeDTO> findByAll() {
        logger.info("Service info about findAll size");
        return sizeRepository.findAll().stream().map(size -> modelMapper.map(size, SizeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public SizeDTO findById(Long id) throws LogicException {
        logger.info("Service info about get detail size :{}", id);
        Optional<Size> size = sizeRepository.findById(id);
        if (!size.isPresent()){
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        return modelMapper.map(size, SizeDTO.class);
    }

    @Override
    public void create(SizeDTO sizeDTO) throws LogicException {
        logger.info("Service info about create size :{}", sizeDTO);
        Optional<Size> sizeExist = sizeRepository.findByName(sizeDTO.getName());
        if (sizeExist.isPresent()) {
            throw new LogicException(ErrorCode.NAME_EXISTED);
        }
        Size size = modelMapper.map(sizeDTO, Size.class);
        size.setCreatedBy(SecurityUtil.getCurrentUserEmailLogin());
        sizeRepository.save(size);
    }

    @Override
    public void update(SizeDTO sizeDTO) throws LogicException {
        logger.info("Service info about update size :{}", sizeDTO);
        Optional<Size> sizeExist = sizeRepository.findById(sizeDTO.getId());
        if (sizeExist.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        Size size = sizeExist.get();
        size.setName(sizeDTO.getName());
        size.setDescription(sizeDTO.getDescription());
        size.setImage(sizeDTO.getImage());
        size.setLastModifiedBy(SecurityUtil.getCurrentUserEmailLogin());
        sizeRepository.save(size);
    }

    @Override
    public void delete(Long id) throws LogicException {
        logger.info("Service info about delete size :{}", id);
        Optional<Size> size = sizeRepository.findById(id);
        if (!size.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        sizeRepository.delete(size.get());
    }
}
