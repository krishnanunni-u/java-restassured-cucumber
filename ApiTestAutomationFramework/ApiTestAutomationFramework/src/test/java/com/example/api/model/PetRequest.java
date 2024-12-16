package com.example.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PetRequest {

    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    @Data
    @Builder
    public static class Category {
        private long id;
        private String name;
    }

    @Data
    @Builder
    public static class Tag {
        private long id;
        private String name;
    }

    public static String createRequestBody(long id, String categoryName, String name, List<String> photoUrls,
                                           List<Tag> tags, String status) {
        try {
            PetRequest petRequest = PetRequest.builder()
                    .id(id)
                    .category(Category.builder()
                            .id(0L) // Default category ID
                            .name(categoryName)
                            .build())
                    .name(name)
                    .photoUrls(photoUrls)
                    .tags(tags)
                    .status(status)
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(petRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error generating request body", e);
        }
    }
}
