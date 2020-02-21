package com.arioatlas.eshop.repositories;


import com.arioatlas.eshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> { }
