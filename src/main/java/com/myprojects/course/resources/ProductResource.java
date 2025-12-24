package com.myprojects.course.resources;

import java.net.URI;
import java.util.List;

import com.myprojects.course.entities.Review;
import com.myprojects.course.entities.dto.ReviewDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myprojects.course.entities.Product;
import com.myprojects.course.services.ProductService;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin(origins = "http://localhost:8081")
public class ProductResource {

	@Autowired
	private ProductService service;

	@GetMapping
	@Operation(summary = "List products", description = "Returns all products in the database")
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Find product by ID", description = "Returns the product with the ID contained in the path")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product user = service.findById(id);
		return ResponseEntity.ok().body(user);
	}

	@PostMapping
	@Operation(summary = "Create product", description = "Creates a new product")
	public ResponseEntity<Product> insert(@RequestBody Product obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete product", description = "Deletes the product with the ID contained in the path")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Update product", description = "Updates the product with the ID contained in the path")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok(obj);
	}

	@GetMapping("/compare")
	@ResponseBody
	@Operation(summary = "Compare products", description = "Returns the products with ID contained in the productIds parameter")
	public ResponseEntity<List<Product>> compareProducts(@RequestParam List<String> productIds) throws BadRequestException {
		List<Product> products = service.getComparedProducts(productIds);
		return ResponseEntity.ok().body(products);
	}

	@PostMapping("/{id}/reviews")
	@ResponseBody
	@Operation(summary = "Create product review", description = "Creates a review for the product with the ID contained in the path")
	public ResponseEntity<Review> createReview(@PathVariable("id") String id, @RequestBody ReviewDTO reviewDTO) {
		Review review = service.createReview(reviewDTO, id);
		return ResponseEntity.status(HttpStatus.CREATED).body(review);
	}
}
