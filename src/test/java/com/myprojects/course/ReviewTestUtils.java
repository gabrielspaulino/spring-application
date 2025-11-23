package com.myprojects.course;

import com.myprojects.course.entities.Product;
import com.myprojects.course.entities.dto.ReviewDTO;

import static com.myprojects.course.ProductTestUtils.mockProduct;

public class ReviewTestUtils {

    public static final String REVIEW_COMMENT = "Test comment";

    public static final Integer REVIEW_RATING = 0;

    public static final String REVIEWS_ENDPOINT = "/reviews";

    public static final String GET_REVIEW_ENDPOINT = "/reviews/{id}";

    public static final String CREATE_REVIEW_ENDPOINT = "/products/{id}/reviews";

    public static ReviewDTO mockReview() {
        return new ReviewDTO(REVIEW_RATING, REVIEW_COMMENT, new Product(mockProduct()));
    }

    public static ReviewDTO mockReview(Integer rating, String comment) {
        return new ReviewDTO(rating, comment, new Product(mockProduct()));
    }
}
