package com.onix.customers.responses;

public record CustomerResponse(long id, String name, int age, String dateOfBirth, String address, String gender) {
}
