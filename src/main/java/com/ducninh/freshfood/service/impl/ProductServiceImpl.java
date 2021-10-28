package com.ducninh.freshfood.service.impl;

import com.ducninh.freshfood.dto.ProductDTO;
import com.ducninh.freshfood.exception.ErrorCode;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.Product;
import com.ducninh.freshfood.model.User;
import com.ducninh.freshfood.repository.ProductRepository;
import com.ducninh.freshfood.repository.UserRepository;
import com.ducninh.freshfood.security.SecurityUtil;
import com.ducninh.freshfood.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ProductDTO> findByAll() {
        return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) throws LogicException {
        logger.info("Service info about get detail Product :{}", id);

        Optional<Product> product =productRepository.findById(id);
        if (!product.isPresent()){
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        return modelMapper.map(product.get(), ProductDTO.class);
    }

    @Override
    public void create(ProductDTO productDTO) throws LogicException {
        logger.info("Service info about create Product :{}", productDTO);

        Optional<Product> productOptional = productRepository.findByName(productDTO.getName());
        if (productOptional.isPresent()) {
            throw new LogicException(ErrorCode.NAME_EXISTED);
        }
        Product product = modelMapper.map(productDTO, Product.class);
        Long currentUserId = SecurityUtil.getCurrentUserIdLogin();
        Optional<User> user = userRepository.findById(currentUserId);
        product.setCreatedBy(user.get().getEmail());
        productRepository.save(product);
    }

    @Override
    public void update(ProductDTO productDTO) throws LogicException {
        logger.info("Service info about update Product :{}", productDTO);
        Optional<Product> productOptional = productRepository.findById(productDTO.getId());
        if (!productOptional.isPresent()) {
            throw new LogicException(ErrorCode.PRODUCT_NOT_EXISTED);
        }
        Product product = productOptional.get();
        product.setName(productDTO.getName());
        product.setCode(productDTO.getCode());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage());
        product.setQuantity(productDTO.getQuantity());
        product.setStatus(productDTO.getStatus());
        productRepository.save(product);
    }

    @Override
    public void delete(Long id) throws LogicException {
        logger.info("Service info about delete Product :{}", id);

        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()){
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        productRepository.delete(product.get());
    }
}
