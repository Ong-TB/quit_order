package com.example.quit.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors;
    private String categoryName;
    private Integer copies;
}
