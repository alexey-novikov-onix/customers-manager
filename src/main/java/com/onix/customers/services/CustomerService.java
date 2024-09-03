package com.onix.customers.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onix.customers.entities.CustomerEntity;
import com.onix.customers.enumerations.RequestAction;
import com.onix.customers.enumerations.RequestStatus;
import com.onix.customers.exceptions.CustomerNotFoundException;
import com.onix.customers.mappers.CustomerMapper;
import com.onix.customers.repositories.CustomerRepository;
import com.onix.customers.requests.CustomerRequest;
import com.onix.customers.requests.PaginatedCustomersRequest;
import com.onix.customers.responses.CustomerResponse;
import com.onix.customers.responses.PageResponse;
import com.onix.customers.responses.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuditEntryService auditEntryService;
    private final CustomerMapper customerMapper;
    private final ObjectMapper objectMapper;

    @Transactional
    public SuccessResponse<CustomerResponse> create(final CustomerRequest request) {
        final var entity = new CustomerEntity();
        customerMapper.requestToEntity(entity, request);
        customerRepository.save(entity);

        auditEntryService.log(RequestAction.CREATE, entity.getId(), requestToString(request), RequestStatus.SUCCESS);

        final var response = customerMapper.toResponse(entity);
        return new SuccessResponse<>(response);
    }

    @Transactional
    public SuccessResponse<CustomerResponse> update(final long id, final CustomerRequest request) {
        final var entity = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        customerMapper.requestToEntity(entity, request);

        auditEntryService.log(RequestAction.UPDATE, entity.getId(), requestToString(request), RequestStatus.SUCCESS);

        final var response = customerMapper.toResponse(entity);
        return new SuccessResponse<>(response);
    }

    @Transactional
    public SuccessResponse<CustomerResponse> delete(final long id) {
        final var entity = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        customerRepository.delete(entity);
        auditEntryService.log(RequestAction.DELETE, id, null, RequestStatus.SUCCESS);

        final var response = customerMapper.toResponse(entity);
        return new SuccessResponse<>(response);
    }

    public PageResponse<CustomerResponse> getPaginatedCustomers(final PaginatedCustomersRequest request) {
        final var pageable = PageRequest.of(
                request.getPage() - 1,
                request.getItemsCount(),
                Sort.by(request.getOrderDirection(), request.getOrderBy())
        );

        final var customerPage = customerRepository.findAll(pageable)
                .map(customerMapper::toResponse);

        return new PageResponse<>(
                customerPage.getNumber() + 1,
                customerPage.getSize(),
                customerPage.getTotalPages(),
                customerPage.getTotalElements(),
                customerPage.getContent()
        );
    }

    @SneakyThrows
    private String requestToString(final CustomerRequest request) {
        return objectMapper.writeValueAsString(request);
    }

}
