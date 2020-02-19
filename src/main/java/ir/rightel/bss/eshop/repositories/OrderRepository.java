package ir.rightel.bss.eshop.repositories;


import ir.rightel.bss.eshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> { }
