package com.learn.sample.shopping.cart.entity.repositories;

import com.learn.sample.shopping.cart.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
