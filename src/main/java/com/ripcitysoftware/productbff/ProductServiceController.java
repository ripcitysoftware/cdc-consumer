package com.ripcitysoftware.productbff;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProductServiceController {
    private final ProductServiceClient productServiceClient;

    public ProductServiceController(
            ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @GetMapping("/products")
    @ResponseBody
    public Product getProduct() {
        log.info("/products was invoked!");
        return productServiceClient.getProduct(1L);
    }
}
