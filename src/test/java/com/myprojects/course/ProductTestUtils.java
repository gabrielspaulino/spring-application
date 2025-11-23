package com.myprojects.course;

import com.myprojects.course.entities.dto.ProductDTO;

import java.util.HashMap;
import java.util.Map;

public class ProductTestUtils {

    public static final String PRODUCT_NAME = "iPhone 16";

    public static final String PRODUCT_DESCRIPTION = "Product description";

    public static final String IMAGE_URL = "imageUrl.test.com";

    public static final Double PRODUCT_PRICE = 5400.00;

    public static final Double PRODUCT_RATING = 0.0;

    public static final String PRODUCT_COLOR = "Black";

    public static final String MEMORY_SIZE = "256 GB";

    public static final String PRODUCT_2_NAME = "iPhone 15";

    public static final String PRODUCT_3_NAME = "iPhone 14";

    public static final String PRODUCTS_ENDPOINT = "/products";

    public static final String COMPARE_PRODUCTS_ENDPOINT = "/products/compare";

    public static final String PRODUCT_IDS_PARAMETER = "productIds";

    public static final String GET_PRODUCT_ENDPOINT = "/products/{id}";

    public static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource not found";

    public static final ProductDTO mockProduct() {
        Map<String, String> specifications = new HashMap<>();
        specifications.put("color", PRODUCT_COLOR);
        specifications.put("memory", MEMORY_SIZE);
        return new ProductDTO(PRODUCT_NAME, PRODUCT_DESCRIPTION, IMAGE_URL, PRODUCT_PRICE, specifications);
    }

    public static final ProductDTO mockProductWithDifferentName(String productName) {
        Map<String, String> specifications = new HashMap<>();
        specifications.put("color", PRODUCT_COLOR);
        specifications.put("memory", MEMORY_SIZE);
        return new ProductDTO(productName, PRODUCT_DESCRIPTION, IMAGE_URL, PRODUCT_PRICE, specifications);
    }

    public static final ProductDTO mockProductWithDifferentPrice(Double price) {
        Map<String, String> specifications = new HashMap<>();
        specifications.put("color", PRODUCT_COLOR);
        specifications.put("memory", MEMORY_SIZE);
        return new ProductDTO(PRODUCT_NAME, PRODUCT_DESCRIPTION, IMAGE_URL, price, specifications);
    }
}
