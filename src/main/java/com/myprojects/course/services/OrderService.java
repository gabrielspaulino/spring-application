package com.myprojects.course.services;

import java.util.List;
import java.util.Optional;

import com.myprojects.course.repositories.AddressRepository;
import com.myprojects.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.myprojects.course.entities.Order;
import com.myprojects.course.entities.OrderItem;
import com.myprojects.course.entities.Product;
import com.myprojects.course.entities.User;
import com.myprojects.course.repositories.OrderItemRepository;
import com.myprojects.course.repositories.OrderRepository;
import com.myprojects.course.services.exceptions.DatabaseException;
import com.myprojects.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Order> findAll() {
		return repository.findAll();
	}
	
	public Order findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		return obj.get();
	}
	
	public Order insert(Order obj) {
		addressRepository.save(obj.getAddress());
		obj.setClient(userRepository.findByEmail(obj.getClient().getEmail()).orElse(null));
		Order savedObj = repository.save(obj);
		obj.getItems().forEach(item -> {
			orderItemRepository.save(new OrderItem(obj, item.getProduct(), item.getQuantity(), item.getPrice()));
		});
		return savedObj;
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
