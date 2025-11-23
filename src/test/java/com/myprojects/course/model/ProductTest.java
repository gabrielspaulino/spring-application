package com.myprojects.course.model;

import com.myprojects.course.entities.Product;
import com.myprojects.course.entities.dto.ProductDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.myprojects.course.ProductTestUtils.mockProduct;
import static com.myprojects.course.ProductTestUtils.mockProductWithDifferentPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ProductTest {

    @Test
    public void validateNegativePriceOnConstructor() {
        ProductDTO productDTO = mockProductWithDifferentPrice(-1.00);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Product(productDTO));
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    public void validateSetNegativePrice() {
        Product product = new Product(mockProduct());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> product.setPrice(-1.00));
        assertEquals("Price cannot be negative", exception.getMessage());
    }
}