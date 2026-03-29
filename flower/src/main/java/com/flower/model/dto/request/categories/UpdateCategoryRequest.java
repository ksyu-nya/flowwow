package com.flower.model.dto.request.categories;
import lombok.Data;

@Data
public class UpdateCategoryRequest {
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
}
