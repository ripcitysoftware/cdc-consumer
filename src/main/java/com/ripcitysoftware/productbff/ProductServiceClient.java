package com.ripcitysoftware.productbff;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceClient {
    private final RestTemplate restTemplate;

    public ProductServiceClient(@Value("${product-service.base-url}") String baseUrl) {
        restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public Product getProduct(Long id) {
        return restTemplate.getForObject("/products/" + id, Product.class);
    }
}
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
class Product {
    private Long id;
    private String name;
}
