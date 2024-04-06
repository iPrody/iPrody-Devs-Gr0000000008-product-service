package com.iprodi08.stepsdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class FindProductById {

    private HttpResponse response;
    private String endpoint;


    @Given("Product endpoint {string} with http method GET available")
    public void productEndpointWithHttpMethodGETAvailable(String endpointPart) {

        this.endpoint = endpointPart;

    }

    @When("client wants to find a product with id {long}")
    public void clientWantsFindProductWithId(long productId) throws IOException, InterruptedException {

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/" + endpoint + productId))
                    .GET()
                    .build();

            this.response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

    }

    @Then("response code is {int}")
    public void responseCodeIs(int expectedCode) {
        final int actualCode = response.statusCode();
        assertThat(actualCode).isEqualTo(expectedCode);
    }

}
