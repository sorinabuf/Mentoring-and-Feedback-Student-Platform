package com.poli.meets.feedback.service.mapper;

import com.poli.meets.feedback.domain.Category;
import com.poli.meets.feedback.service.dto.CategoryDTO;
import com.poli.meets.feedback.service.dto.CategorySubjectsDTO;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>  {

    Category toEntity(CategoryDTO facultyDTO);

    CategoryDTO toDto(Category category);

    CategorySubjectsDTO toCategorySubjectsDto(Category category);

}
