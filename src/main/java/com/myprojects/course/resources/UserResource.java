package com.myprojects.course.resources;

import java.net.URI;
import java.util.List;

import com.myprojects.course.entities.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myprojects.course.entities.User;
import com.myprojects.course.services.UserService;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = "http://localhost:8081")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	@Operation(summary = "List users", description = "Returns all users in the database")
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Find user by ID", description = "Returns the user with the ID contained in the path")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	@Operation(summary = "Create user", description = "Creates a new user")
	public ResponseEntity<User> insert(@RequestBody UserDTO userDTO) {
		User user = service.insert(new User(userDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete user", description = "Deletes the user with the ID contained in the path")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	@Operation(summary = "Update user", description = "Updates the user with the ID contained in the path")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok(obj);
	}
}
