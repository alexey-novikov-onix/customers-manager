package com.onix.customers.mappers;

import com.onix.customers.entities.AuditEntryEntity;
import com.onix.customers.responses.AuditEntryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditEntryMapper {

    AuditEntryResponse toResponse(AuditEntryEntity entity);

}
