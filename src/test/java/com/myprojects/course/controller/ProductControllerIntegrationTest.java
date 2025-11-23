package com.myprojects.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.course.entities.Product;
import com.myprojects.course.entities.Review;
import com.myprojects.course.entities.dto.ProductDTO;
import com.myprojects.course.entities.dto.ReviewDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.myprojects.course.ProductTestUtils.*;
import static com.myprojects.course.ReviewTestUtils.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void populateDatabase() throws Exception {
        // Create product DTO
        ProductDTO productDTO = mockProduct();
        ProductDTO productDTO2 = mockProductWithDifferentName(PRODUCT_2_NAME);
        ProductDTO productDTO3 = mockProductWithDifferentName(PRODUCT_3_NAME);

        // Create three products via POST request
        mockMvc.perform(post(PRODUCTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME));

        mockMvc.perform(post(PRODUCTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(PRODUCT_2_NAME));

        mockMvc.perform(post(PRODUCTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO3)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(PRODUCT_3_NAME));
    }

    @Test
    public void testCompareProducts() throws Exception {
        // Verify we can compare all three products
        mockMvc.perform(get(COMPARE_PRODUCTS_ENDPOINT).param(PRODUCT_IDS_PARAMETER,"1,2,3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$[*].imageUrl", everyItem(equalTo(IMAGE_URL))))
                .andExpect(jsonPath("$[*].price", everyItem(equalTo(PRODUCT_PRICE))))
                .andExpect(jsonPath("$[*].rating", everyItem(equalTo(PRODUCT_RATING))))
                .andExpect(jsonPath("$[*].specifications.color", everyItem(equalTo(PRODUCT_COLOR))))
                .andExpect(jsonPath("$[*].specifications.memory", everyItem(equalTo(MEMORY_SIZE))))
                .andExpect(jsonPath("$[1].name").value(PRODUCT_2_NAME))
                .andExpect(jsonPath("$[2].name").value(PRODUCT_3_NAME));

        // Verify we can compare two products
        mockMvc.perform(get(COMPARE_PRODUCTS_ENDPOINT).param(PRODUCT_IDS_PARAMETER,"1,3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$[*].imageUrl", everyItem(equalTo(IMAGE_URL))))
                .andExpect(jsonPath("$[*].price", everyItem(equalTo(PRODUCT_PRICE))))
                .andExpect(jsonPath("$[*].rating", everyItem(equalTo(PRODUCT_RATING))))
                .andExpect(jsonPath("$[*].specifications.color", everyItem(equalTo(PRODUCT_COLOR))))
                .andExpect(jsonPath("$[*].specifications.memory", everyItem(equalTo(MEMORY_SIZE))))
                .andExpect(jsonPath("$[1].name").value(PRODUCT_3_NAME));

        // Verify we can't compare less than two products
        mockMvc.perform(get(COMPARE_PRODUCTS_ENDPOINT).param(PRODUCT_IDS_PARAMETER,"1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Verify we can retrieve all three products
        mockMvc.perform(get(COMPARE_PRODUCTS_ENDPOINT).param(PRODUCT_IDS_PARAMETER,"1,2,3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
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
    public void testCreateAndGetProduct() throws Exception {
        // Create a product DTO
        String productName = "iPhone 13";
        ProductDTO productDTO = mockProductWithDifferentName(productName);

        // Create the product via POST request
        String productJson = objectMapper.writeValueAsString(productDTO);

        String response = mockMvc.perform(post(PRODUCTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productName))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the created product ID
        Product createdProduct = objectMapper.readValue(response, Product.class);
        String productId = String.valueOf(createdProduct.getId());

        // Verify we can retrieve the product
        mockMvc.perform(get(GET_PRODUCT_ENDPOINT, productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value(productName))
                .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL))
                .andExpect(jsonPath("$.price").value(PRODUCT_PRICE))
                .andExpect(jsonPath("$.rating").value(PRODUCT_RATING))
                .andExpect(jsonPath("$.specifications.color").value(PRODUCT_COLOR))
                .andExpect(jsonPath("$.specifications.memory").value(MEMORY_SIZE));
    }

    @Test
    public void testDeleteAndGetProduct() throws Exception {
        // Create a product DTO
        String productName = "iPhone 12";
        ProductDTO productDTO = mockProductWithDifferentName(productName);

        // Create the product via POST request
        String productJson = objectMapper.writeValueAsString(productDTO);

        String response = mockMvc.perform(post(PRODUCTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productName))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the created product ID
        Product createdProduct = objectMapper.readValue(response, Product.class);
        String productId = String.valueOf(createdProduct.getId());

        // Delete the created product
        mockMvc.perform(delete(GET_PRODUCT_ENDPOINT, productId))
                .andExpect(status().isNoContent());

        // Verify product is not found anymore
        mockMvc.perform(get(GET_PRODUCT_ENDPOINT, productId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAndGetReview() throws Exception {
        // Create a product DTO
        String productName = "iPhone 12";
        ProductDTO productDTO = mockProductWithDifferentName(productName);

        // Create the product via POST request
        String productJson = objectMapper.writeValueAsString(productDTO);

        String response = mockMvc.perform(post(PRODUCTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productName))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the created product ID
        Product createdProduct = objectMapper.readValue(response, Product.class);
        String productId = String.valueOf(createdProduct.getId());

        // Create a review DTO
        String reviewComment = "Good product";
        Integer reviewRating = 4;
        ReviewDTO reviewDTO = mockReview(reviewRating, reviewComment);

        // Create the review via POST request
        String reviewJson = objectMapper.writeValueAsString(reviewDTO);

        String reviewResponse = mockMvc.perform(post(CREATE_REVIEW_ENDPOINT, productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.rating").value(reviewRating))
                .andExpect(jsonPath("$.comment").value(reviewComment))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the created review ID
        Review createdReview = objectMapper.readValue(reviewResponse, Review.class);
        String reviewId = String.valueOf(createdReview.getId());

        // Verify we can retrieve the review
        mockMvc.perform(get(GET_REVIEW_ENDPOINT, reviewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(reviewRating))
                .andExpect(jsonPath("$.comment").value(reviewComment));
    }

    @Test
    public void testDeleteReviewAndGetProduct() throws Exception {
        // Create a product DTO
        String productName = "iPhone 12";
        ProductDTO productDTO = mockProductWithDifferentName(productName);

        // Create the product via POST request
        String productJson = objectMapper.writeValueAsString(productDTO);

        String response = mockMvc.perform(post(PRODUCTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productName))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the created product ID
        Product createdProduct = objectMapper.readValue(response, Product.class);
        String productId = String.valueOf(createdProduct.getId());

        // Create a review DTO
        String reviewComment = "Good product";
        Integer reviewRating = 4;
        ReviewDTO reviewDTO = mockReview(reviewRating, reviewComment);

        // Create the review via POST request
        String reviewJson = objectMapper.writeValueAsString(reviewDTO);

        String reviewResponse = mockMvc.perform(post(CREATE_REVIEW_ENDPOINT, productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.rating").value(reviewRating))
                .andExpect(jsonPath("$.comment").value(reviewComment))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Verify the review is in the product
        mockMvc.perform(get(GET_PRODUCT_ENDPOINT, productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviews", hasSize(1)))
                .andExpect(jsonPath("$.reviews[0].id").exists())
                .andExpect(jsonPath("$.reviews[0].rating").value(reviewRating))
                .andExpect(jsonPath("$.reviews[0].comment").value(reviewComment));

        // Extract the created review ID
        Review createdReview = objectMapper.readValue(reviewResponse, Review.class);
        String reviewId = String.valueOf(createdReview.getId());

        // Delete the created review
        mockMvc.perform(delete(GET_REVIEW_ENDPOINT, reviewId))
                .andExpect(status().isNoContent());

        // Verify the review is not in the product anymore
        mockMvc.perform(get(GET_PRODUCT_ENDPOINT, productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviews", hasSize(0)));
    }
}