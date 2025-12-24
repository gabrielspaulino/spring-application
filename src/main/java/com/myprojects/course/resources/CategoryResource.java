package com.myprojects.course.resources;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myprojects.course.entities.Category;
import com.myprojects.course.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
@CrossOrigin(origins = "http://localhost:8081")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@GetMapping
	@Operation(summary = "List categories", description = "Returns all categories in the database")
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Find category by ID", description = "Returns the category with the ID contained in the path")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category user = service.findById(id);
		return ResponseEntity.ok().body(user);
	}
}
