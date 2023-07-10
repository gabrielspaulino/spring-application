package com.myprojects.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.course.entities.Order;

//annotation is not necessary as it extends JpaRepository
public interface OrderRepository extends JpaRepository<Order, Long> {
	//JpaRepository already has a default implementation
}
