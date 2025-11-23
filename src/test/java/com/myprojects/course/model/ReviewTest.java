package com.myprojects.course.model;

import com.myprojects.course.entities.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.myprojects.course.ReviewTestUtils.REVIEW_COMMENT;
import static com.myprojects.course.ReviewTestUtils.mockReview;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ReviewTest {

    @Test
    public void validateNegativeRatingOnConstructor() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Review(mockReview(-1, REVIEW_COMMENT)));
        assertEquals(exception.getMessage(), "Rating must be between 0 and 5");
    }

    @Test
    public void validateSetNegativeRating() {
        Review review = new Review(mockReview());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> review.setRating(-1));
        assertEquals(exception.getMessage(), "Rating must be between 0 and 5");
    }

    @Test
    public void validateOverlimitRatingOnConstructor() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Review(mockReview(6, REVIEW_COMMENT)));
        assertEquals(exception.getMessage(), "Rating must be between 0 and 5");
    }

    @Test
    public void validateSetOverlimitRating() {
        Review review = new Review(mockReview());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> review.setRating(6));
        assertEquals(exception.getMessage(), "Rating must be between 0 and 5");
    }
}