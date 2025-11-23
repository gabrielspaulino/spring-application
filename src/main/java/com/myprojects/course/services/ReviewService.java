package com.myprojects.course.services;

import com.myprojects.course.entities.Product;
import com.myprojects.course.entities.Review;
import com.myprojects.course.repositories.ProductRepository;
import com.myprojects.course.repositories.ReviewRepository;
import com.myprojects.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(String id) {
        return reviewRepository.findById(Long.valueOf(id)).orElseThrow(ResourceNotFoundException::new);
    }

    public void deleteReview(String id) {
        Review review = reviewRepository.findById(Long.valueOf(id)).orElseThrow(ResourceNotFoundException::new);
        reviewRepository.delete(review);
        reviewRepository.flush();

        Product product = productRepository.findById(review.getProduct().getId()).orElseThrow(ResourceNotFoundException::new);
        product.updateRating();
        productRepository.flush();
    }
}

