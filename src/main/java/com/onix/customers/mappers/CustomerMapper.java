package com.onix.customers.mappers;

import com.onix.customers.entities.CustomerEntity;
import com.onix.customers.requests.CustomerRequest;
import com.onix.customers.responses.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerResponse toResponse(CustomerEntity entity);

    void requestToEntity(@MappingTarget CustomerEntity entity, CustomerRequest response);

}
