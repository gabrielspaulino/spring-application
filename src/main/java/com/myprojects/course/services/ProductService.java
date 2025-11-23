package com.myprojects.course.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.myprojects.course.entities.Review;
import com.myprojects.course.entities.dto.ReviewDTO;
import com.myprojects.course.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.myprojects.course.entities.Product;
import com.myprojects.course.repositories.ProductRepository;
import com.myprojects.course.services.exceptions.DatabaseException;
import com.myprojects.course.services.exceptions.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		return obj.get();
	}
	
	public Product insert(Product obj) {
		return productRepository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Product update(Long id, Product obj) {
		try {
			Product entity = productRepository.getReferenceById(id);
			updateData(entity, obj);
			return productRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException();
		}
	}

	private void updateData(Product entity, Product obj) {
		entity.setName(obj.getName());
		entity.setDescription(obj.getDescription());
		entity.setPrice(obj.getPrice());
		entity.setImgUrl(obj.getImgUrl());
	}

	public List<Product> getComparedProducts(List<String> ids) throws BadRequestException {
		if (ids.size() <= 1) {
			throw new BadRequestException("It is not possible to compare less than two products");
		}
		List<Product> products = new ArrayList<>();
		ids.forEach(id -> products.add(productRepository.findById(Long.valueOf(id)).orElseThrow(ResourceNotFoundException::new)));
		return products;
	}

	public Review createReview(ReviewDTO reviewDTO, String productId) {
		Product product = productRepository.findById(Long.valueOf(productId)).orElseThrow(ResourceNotFoundException::new);

		Review review = new Review(reviewDTO);
		review.setProduct(product);
		product.addReview(review);

		reviewRepository.save(review);
		productRepository.save(product);

		return review;
	}
}
