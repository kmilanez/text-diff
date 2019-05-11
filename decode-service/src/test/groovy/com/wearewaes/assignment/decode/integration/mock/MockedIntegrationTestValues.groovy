package com.wearewaes.assignment.decode.integration.mock

enum MockedIntegrationTestValues {

    TEST_1("SGVsbG8gV29ybGQh", "Hello World!"),
    TEST_2("eyJtb3ZpZUlkIiA6ICJCbGFkZSBSdW5uZXIiLCAicmVsZWFzZS1kYXRlIjogIjE5ODIiIH0",
           "{\"movieId\" : \"Blade Runner\", \"release-date\": \"1982\" }")

    private String encodedValue
    private String decodedValue

    MockedIntegrationTestValues(String encodedValue, String decodedValue) {
        this.encodedValue = encodedValue
        this.decodedValue = decodedValue
    }

    String getEncodedValue() {
        return this.encodedValue
    }

    String getDecodedValue() {
        return this.decodedValue
    }
}