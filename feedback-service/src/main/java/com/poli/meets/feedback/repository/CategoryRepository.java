package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {

    List<Category> findAllByNameNotLike(String name);

    List<Category> findByOrderById();
}
