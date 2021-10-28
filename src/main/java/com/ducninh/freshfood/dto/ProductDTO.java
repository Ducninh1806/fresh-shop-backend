package com.ducninh.freshfood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
    private String image;
    private Integer status;
}
