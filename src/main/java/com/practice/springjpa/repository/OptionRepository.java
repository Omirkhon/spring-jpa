package com.practice.springjpa.repository;

import com.practice.springjpa.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Integer> {
}
