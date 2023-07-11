package com.myprojects.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.course.entities.Category;

//annotation is not necessary as it extends JpaRepository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	//JpaRepository already has a default implementation
}
