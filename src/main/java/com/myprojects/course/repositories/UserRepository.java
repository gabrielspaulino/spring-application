package com.myprojects.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	//JpaRepository already has a default implementation
}
