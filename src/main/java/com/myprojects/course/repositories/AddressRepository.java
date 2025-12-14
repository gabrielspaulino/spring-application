package com.myprojects.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.course.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
