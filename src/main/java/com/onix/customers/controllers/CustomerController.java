package com.onix.customers.controllers;

import com.onix.customers.requests.CustomerRequest;
import com.onix.customers.requests.PaginatedCustomersRequest;
import com.onix.customers.responses.CustomerResponse;
import com.onix.customers.responses.PageResponse;
import com.onix.customers.responses.SuccessResponse;
import com.onix.customers.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public PageResponse<CustomerResponse> getCustomers(final @Valid PaginatedCustomersRequest request) {
        return customerService.getPaginatedCustomers(request);
    }

    @PostMapping
    public SuccessResponse<CustomerResponse> createCustomer(final @Valid @RequestBody CustomerRequest request) {
        return customerService.create(request);
    }

    @PutMapping("/{id}")
    public SuccessResponse<CustomerResponse> updateCustomer(
            final @PathVariable long id,
            final @Valid @RequestBody CustomerRequest request
    ) {
        return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse<CustomerResponse> deleteCustomer(final @PathVariable long id) {
        return customerService.delete(id);
    }

}
