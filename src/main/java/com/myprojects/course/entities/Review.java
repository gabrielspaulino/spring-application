package com.myprojects.course.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.myprojects.course.entities.dto.ReviewDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Product product;

    public Review(ReviewDTO reviewDTO) {
        if (reviewDTO.rating() < 0 || reviewDTO.rating() > 5) throw new IllegalArgumentException("Rating must be between 0 and 5");
        this.rating = reviewDTO.rating();
        this.comment = reviewDTO.comment();
        this.product = reviewDTO.product();
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 0 || rating > 5) throw new IllegalArgumentException("Rating must be between 0 and 5");
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
