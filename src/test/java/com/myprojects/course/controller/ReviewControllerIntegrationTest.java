package com.myprojects.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.course.entities.Product;
import com.myprojects.course.entities.Review;
import com.myprojects.course.entities.dto.ProductDTO;
import com.myprojects.course.entities.dto.ReviewDTO;
import com.myprojects.course.repositories.ProductRepository;
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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;
    
    private static final Integer REVIEW_2_RATING = 5;

    private static final String REVIEW_2_COMMENT = "Amazing product!";

    private static boolean initialized = false;

    @Before
    public void populateDatabase() throws Exception {
        if (!initialized) {
            // Create a product DTO
            ProductDTO productDTO = mockProductWithDifferentName(PRODUCT_NAME);

            // Create the product via POST request
            String productJson = objectMapper.writeValueAsString(productDTO);

            String response = mockMvc.perform(post(PRODUCTS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(productJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            // Extract the created product ID
            Product createdProduct = objectMapper.readValue(response, Product.class);
            String productId = String.valueOf(createdProduct.getId());

            // Create review DTOs
            ReviewDTO reviewDTO = mockReview();
            ReviewDTO reviewDTO2 = mockReview(REVIEW_2_RATING, REVIEW_2_COMMENT);

            // Create two reviews via POST request
            String reviewJson = objectMapper.writeValueAsString(reviewDTO);

            mockMvc.perform(post(CREATE_REVIEW_ENDPOINT, productId).contentType(MediaType.APPLICATION_JSON)
                            .content(reviewJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.rating").value(REVIEW_RATING))
                    .andExpect(jsonPath("$.comment").value(REVIEW_COMMENT))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            reviewJson = objectMapper.writeValueAsString(reviewDTO2);

            mockMvc.perform(post(CREATE_REVIEW_ENDPOINT, productId).contentType(MediaType.APPLICATION_JSON)
                            .content(reviewJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.rating").value(REVIEW_2_RATING))
                    .andExpect(jsonPath("$.comment").value(REVIEW_2_COMMENT))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            initialized = true;
        }
    }

    @Test
    public void testGetAllReviews() throws Exception {
        // Verify we can retrieve all reviews
        mockMvc.perform(get(REVIEWS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rating").value(REVIEW_RATING))
                .andExpect(jsonPath("$[0].comment").value(REVIEW_COMMENT))
                .andExpect(jsonPath("$[1].rating").value(REVIEW_2_RATING))
                .andExpect(jsonPath("$[1].comment").value(REVIEW_2_COMMENT));
    }

    @Test
    public void testDeleteAndGetReview() throws Exception {
        // Create a review DTO
        String reviewComment = "Bad product";
        Integer reviewRating = 2;
        ReviewDTO reviewDTO = mockReview(reviewRating, reviewComment);

        // Create the review via POST request
        String reviewJson = objectMapper.writeValueAsString(reviewDTO);

        String response = mockMvc.perform(post(CREATE_REVIEW_ENDPOINT, "1")
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
        Review createdReview = objectMapper.readValue(response, Review.class);
        String reviewId = String.valueOf(createdReview.getId());

        // Delete the created review
        mockMvc.perform(delete(GET_REVIEW_ENDPOINT, reviewId))
                .andExpect(status().isNoContent());

        // Verify review is not found anymore
        mockMvc.perform(get(GET_REVIEW_ENDPOINT, reviewId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteReviewAndGetProductRating() throws Exception {
        // Create a review DTO
        String reviewComment = "Bad product";
        Integer reviewRating = 2;
        ReviewDTO reviewDTO = mockReview(reviewRating, reviewComment);

        // Create the review via POST request
        String reviewJson = objectMapper.writeValueAsString(reviewDTO);

        String response = mockMvc.perform(post(CREATE_REVIEW_ENDPOINT, "1")
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
        Review createdReview = objectMapper.readValue(response, Review.class);
        String reviewId = String.valueOf(createdReview.getId());

        // Delete the created review
        mockMvc.perform(delete(GET_REVIEW_ENDPOINT, reviewId))
                .andExpect(status().isNoContent());

        // Verify review rating is updated
        mockMvc.perform(get(GET_PRODUCT_ENDPOINT, "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value((REVIEW_RATING + REVIEW_2_RATING) / 2.0));
    }
}
