package com.practice.springjpa.repository;

import com.practice.springjpa.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Integer> {
    List<Value> findByProductId(int productID);
}
