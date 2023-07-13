package com.myprojects.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.course.entities.OrderItem;

//annotation is not necessary as it extends JpaRepository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	//JpaRepository already has a default implementation
}
