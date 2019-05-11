package com.wearewaes.assignment.decode.unit.mock

enum MockedUnitTestValues {

    TEST_1("SGVsbG8gV29ybGQh", "Hello World!"),

    TEST_2("eyJuYW1lIjoiSmVhbi1MdWMgUGljYXJkIiwicmFuayI6IkNhcHRhaW4gb2YgdGhlIFVTUyBFbnRlcnByaXNlIn0=",
           "{\"name\" : \"Jean-Luc Picard\", \"rank\": \"Captain of the USS Enterprise\" }"),

    TEST_3("SS4uLiBhIHVuaXZlcnNlIG9mIGF0b21zLCBhbiBhdG9tIGluIHRoZSB1bml2ZXJzZS4=",
            "I... a universe of atoms, an atom in the universe."),

    TEST_4("eyAiam9rZSI6ICJUaGUgY2hlbWljYWwgZm9ybXVsYSBmb3IgdGhlIGhpZ2hseSB0b3hpYyBjeWFuaWRlIGlvbiBpcyBDTi0uI" +
            "FRoZXNlIGFyZSBhbHNvIENodWNrIE5vcnJpcycgaW5pdGlhbHMuIFRoaXMgaXMgbm90IGEgY29pbmNpZGVuY2UuIiB9=",
            "{ \"joke\": \"The chemical formula for the highly toxic cyanide ion is CN-. These are also " +
                    "Chuck Norris' initials. This is not a coincidence.\" }"),

    private String encodedValue
    private String decodedValue

    MockedUnitTestValues(String encodedValue, String decodedValue) {
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
