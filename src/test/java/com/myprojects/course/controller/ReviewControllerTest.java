package com.myprojects.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.course.entities.Review;
import com.myprojects.course.resources.ReviewResource;
import com.myprojects.course.services.ReviewService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.myprojects.course.ReviewTestUtils.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewResource reviewResource;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewResource).build();
    }

    @Test
    public void testGetReviewById() throws Exception {
        // Mock review and return when calling the method
        String reviewId = "1";
        Review review = new Review(mockReview());
        when(reviewService.getReviewById(reviewId)).thenReturn(review);

        // Verify if all values of the response are matching
        mockMvc.perform(get(GET_REVIEW_ENDPOINT, reviewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(REVIEW_RATING))
                .andExpect(jsonPath("$.comment").value(REVIEW_COMMENT));
    }
}
