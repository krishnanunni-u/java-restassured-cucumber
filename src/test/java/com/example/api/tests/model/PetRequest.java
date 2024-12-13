package com.example.api.tests.model;

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

    public static String createRequestBody(long id, String categoryName, String name, List<String> photoUrls,
                                           List<Tag> tags, String status) {
        try {
            PetRequest petRequest = PetRequest.builder()
                    .id(id)
                    .category(new Category(0L, categoryName)) // Use standalone Category
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
