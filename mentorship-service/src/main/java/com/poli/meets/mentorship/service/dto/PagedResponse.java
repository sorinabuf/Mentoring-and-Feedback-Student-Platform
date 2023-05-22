package com.poli.meets.mentorship.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


import java.util.List;

@NoArgsConstructor
@Getter
public class PagedResponse<T> {
    @JsonProperty long totalElements;
    @JsonProperty
    List<T> content;


    public static <T> PagedResponse<T> of (Page<T> page){
        PagedResponse<T> response = new PagedResponse<>();
        response.content = page.getContent();
        response.totalElements = page.getTotalElements();
        return response;
    }

    public static <T> PagedResponse<T> of (List<T> content, long totalElements){
        PagedResponse<T> response = new PagedResponse<>();
        response.content = content;
        response.totalElements = totalElements;
        return response;
    }
}