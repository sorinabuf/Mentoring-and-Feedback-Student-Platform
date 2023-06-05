package com.poli.meets.feedback.service;

import com.poli.meets.feedback.repository.CategoryRepository;
import com.poli.meets.feedback.service.dto.CategoryDTO;
import com.poli.meets.feedback.service.mapper.CategoryMapper;
import com.poli.meets.feedback.web.rest.errors.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryDTO findOne(Long categoryId) {
        return categoryRepository.findById(categoryId).map(categoryMapper::toDto)
                .orElseThrow(BadRequestException::new);
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findByOrderById().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
