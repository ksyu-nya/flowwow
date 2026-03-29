package com.flower.model.dto.response.items;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private boolean isHit;
    private boolean isNew;
    private CategoryDto category;

    @Data
    public static class CategoryDto {
        private Long id;
        private String name;
        private String slug;
    }
}
