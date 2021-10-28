package com.ducninh.freshfood.service.impl;

import com.ducninh.freshfood.dto.CategoryDTO;
import com.ducninh.freshfood.exception.ErrorCode;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.Category;
import com.ducninh.freshfood.repository.CategoryRepository;
import com.ducninh.freshfood.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> findByAll() {
        logger.info("Service info about findAll category");

        return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long id) throws LogicException {
        logger.info("Service info about get detail category {}", id);
        Optional<Category> categoryExist = categoryRepository.findById(id);
        if (!categoryExist.isPresent()){
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        return modelMapper.map(categoryExist.get(), CategoryDTO.class);
    }

    @Override
    public void create(CategoryDTO categoryDTO) throws LogicException {
        logger.info("Service info about create category :{}", categoryDTO);
        Optional<Category> categoryExist = categoryRepository.findByName(categoryDTO.getName());
        if (categoryExist.isPresent()) {
            throw  new LogicException(ErrorCode.NAME_EXISTED);
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
    }

    @Override
    public void update(CategoryDTO categoryDTO) throws LogicException {
        logger.info("Service info about update category :{}", categoryDTO);

        Optional<Category> categoryExist = categoryRepository.findById(categoryDTO.getId());
        if (!categoryExist.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        Category category = categoryExist.get();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) throws LogicException {
        logger.info("Service info about delete category :{}", id);

        Optional<Category> categoryExist = categoryRepository.findById(id);
        if (!categoryExist.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        categoryRepository.delete(categoryExist.get());
    }
}
