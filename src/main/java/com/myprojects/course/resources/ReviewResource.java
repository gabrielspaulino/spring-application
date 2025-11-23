package com.myprojects.course.resources;

import com.myprojects.course.entities.Review;
import com.myprojects.course.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
@CrossOrigin(origins = "http://localhost:8081")
public class ReviewResource {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    @ResponseBody
    @Operation(summary = "List reviews", description = "Returns all reviews")
    public ResponseEntity<List<Review>> getReviews() {
        List<Review> reviews = reviewService.getReviews();
        return ResponseEntity.ok().body(reviews);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Get review by ID", description = "Returns the review with the ID contained in the path")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") String id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok().body(review);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Deletes the review with the ID contained in the path")
    public ResponseEntity<Review> deleteReview(@PathVariable("id") String id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
