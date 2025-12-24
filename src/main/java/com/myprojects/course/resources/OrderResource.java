package com.myprojects.course.resources;

import java.net.URI;
import java.util.List;

import com.myprojects.course.entities.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myprojects.course.entities.Order;
import com.myprojects.course.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
@CrossOrigin(origins = "http://localhost:8081")
public class OrderResource {

	@Autowired
	private OrderService service;

	@GetMapping
	@Operation(summary = "List orders", description = "Returns all orders in the database")
	public ResponseEntity<List<Order>> findAll() {
		List<Order> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Find order by ID", description = "Returns the order with the ID contained in the path")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		Order order = service.findById(id);
		return ResponseEntity.ok().body(order);
	}

	@PostMapping
	@Operation(summary = "Create order", description = "Creates a new order")
	public ResponseEntity<Order> insert(@RequestBody OrderDTO orderDTO) {
		Order order = service.insert(new Order(orderDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
		return ResponseEntity.created(uri).body(order);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete order", description = "Deletes the order with the ID contained in the path")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
