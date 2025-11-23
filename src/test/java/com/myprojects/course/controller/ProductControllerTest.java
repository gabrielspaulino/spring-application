package com.myprojects.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.course.entities.Product;
import com.myprojects.course.entities.dto.ProductDTO;
import com.myprojects.course.resources.ProductResource;
import com.myprojects.course.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.myprojects.course.ProductTestUtils.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductResource productResource;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productResource).build();
    }

    @Test
    public void testGetProductById() throws Exception {
        // Mock product and return when calling the method
        Long productId = 1L;
        Product product = new Product(mockProduct());
        when(productService.findById(productId)).thenReturn(product);

        // Verify if all values of the response are matching
        mockMvc.perform(get(GET_PRODUCT_ENDPOINT, productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL))
                .andExpect(jsonPath("$.price").value(PRODUCT_PRICE))
                .andExpect(jsonPath("$.rating").value(PRODUCT_RATING))
                .andExpect(jsonPath("$.specifications.color").value(PRODUCT_COLOR))
                .andExpect(jsonPath("$.specifications.memory").value(MEMORY_SIZE));
    }

    @Test
    public void testCompareProducts() throws Exception {
        // Mock products and return the list of products when calling the method
        Product product = new Product(mockProduct());
        Product product2 = new Product(mockProduct());
        product2.setName(PRODUCT_2_NAME);
        Product product3 = new Product(mockProduct());
        product3.setName(PRODUCT_3_NAME);
        when(productService.getComparedProducts(List.of("1", "2", "3")))
                .thenReturn(List.of(product, product2, product3));

        // Verify if all products are returned with the expected values
        mockMvc.perform(get(COMPARE_PRODUCTS_ENDPOINT).param(PRODUCT_IDS_PARAMETER, "1,2,3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$[*].imageUrl", everyItem(equalTo(IMAGE_URL))))
                .andExpect(jsonPath("$[*].price", everyItem(equalTo(PRODUCT_PRICE))))
                .andExpect(jsonPath("$[*].rating", everyItem(equalTo(PRODUCT_RATING))))
                .andExpect(jsonPath("$[*].specifications.color", everyItem(equalTo(PRODUCT_COLOR))))
                .andExpect(jsonPath("$[*].specifications.memory", everyItem(equalTo(MEMORY_SIZE))))
                .andExpect(jsonPath("$[1].name").value(PRODUCT_2_NAME))
                .andExpect(jsonPath("$[2].name").value(PRODUCT_3_NAME));
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Mock product and return when calling the method
        ProductDTO productDTO = mockProduct();
        when(productService.insert(new Product(productDTO))).thenReturn(new Product(productDTO));

        // Convert DTO to JSON
        String json = objectMapper.writeValueAsString(productDTO);

        // Verify if all values of the response are matching
        mockMvc.perform(post(PRODUCTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL))
                .andExpect(jsonPath("$.price").value(PRODUCT_PRICE))
                .andExpect(jsonPath("$.rating").value(PRODUCT_RATING))
                .andExpect(jsonPath("$.specifications.color").value(PRODUCT_COLOR))
                .andExpect(jsonPath("$.specifications.memory").value(MEMORY_SIZE));
    }
}