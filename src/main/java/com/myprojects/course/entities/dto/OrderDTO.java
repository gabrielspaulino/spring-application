package com.myprojects.course.entities.dto;

import com.myprojects.course.entities.Address;
import com.myprojects.course.entities.OrderItem;
import com.myprojects.course.entities.User;
import com.myprojects.course.entities.enums.OrderStatus;

import java.time.Instant;
import java.util.Set;

public record OrderDTO(
        Instant moment,
        OrderStatus orderStatus,
        User client,
        Set<OrderItem> items,
        Address address
) {}
