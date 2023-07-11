package com.myprojects.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.course.entities.Product;

//annotation is not necessary as it extends JpaRepository
public interface ProductRepository extends JpaRepository<Product, Long> {
	//JpaRepository already has a default implementation
}
