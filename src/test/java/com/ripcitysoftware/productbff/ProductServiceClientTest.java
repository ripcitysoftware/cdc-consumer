package com.ripcitysoftware.productbff;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = "product-service.base-url:http://localhost:9091",
    classes = ProductServiceClient.class)
public class ProductServiceClientTest {
    @Rule
    public PactProviderRuleMk2 providerRuleMk2 =
            new PactProviderRuleMk2("productServiceProvider",
                    "localhost", 9091, this);

    @Autowired
    private ProductServiceClient productServiceClient;

    @Test
    @PactVerification
    public void test_product_exists() {
        Product product = productServiceClient.getProduct(1L);
        Assert.assertThat(product.getId(), IsEqual.equalTo(1L));
        Assert.assertThat(product.getName(), IsEqual.equalTo("Tide Original"));
    }


    @Pact(state = "one product",
            provider = "productServiceProvider",
            consumer = "productServiceClient")
    public RequestResponsePact pactProductExists(PactDslWithProvider builder) {
        return builder.given("Product 1 exists")
                .uponReceiving("A request for /products/1")
                .path("/products/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(LambdaDsl.newJsonBody((o) -> o
                        .numberType("id", 1)
                        .stringType("name", "Tide Original"))
                        .build())
                .toPact();
    }



}
