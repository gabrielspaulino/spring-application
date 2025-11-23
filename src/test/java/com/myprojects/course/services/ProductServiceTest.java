package com.myprojects.course.services;

import com.myprojects.course.entities.Product;
import com.myprojects.course.repositories.ProductRepository;
import com.myprojects.course.services.exceptions.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.myprojects.course.ProductTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testGetProductById() {
        // Verify if product is returned when calling the getProductById method
        Product product = new Product(mockProduct());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertEquals(product, productService.findById(1L));
    }

    @Test
    public void testGetNonexistentProductById() {
        // Verify if exception is thrown when findById doesn't return any product
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.findById(1L));
    }

    @Test
    public void testComparedProducts() throws BadRequestException {
        // Mock products and return when finding by id
        Product product = new Product(mockProduct());
        Product product2 = new Product(mockProduct());
        product2.setName(PRODUCT_2_NAME);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(productRepository.findById(3L)).thenReturn(Optional.empty());

        // Verify if method functions and throws exception when passing nonexisting id or passing only one id
        assertIterableEquals(Lists.newArrayList(product, product2), productService.getComparedProducts(List.of("1", "2")));
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> productService.getComparedProducts(List.of("1", "2", "3")));
        assertEquals(resourceNotFoundException.getMessage(), RESOURCE_NOT_FOUND_MESSAGE);
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> productService.getComparedProducts(List.of("1")));
        assertEquals(badRequestException.getMessage(), "It is not possible to compare less than two products");
    }

    @Test
    public void testDeleteNonexistentProduct() {
        // Verify if exception is thrown when findById doesn't return any product
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> productService.delete(1L));
        assertEquals(resourceNotFoundException.getMessage(), RESOURCE_NOT_FOUND_MESSAGE);
    }
}