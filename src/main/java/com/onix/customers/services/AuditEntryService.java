package com.onix.customers.services;

import com.onix.customers.entities.AuditEntryEntity;
import com.onix.customers.enumerations.RequestAction;
import com.onix.customers.enumerations.RequestStatus;
import com.onix.customers.mappers.AuditEntryMapper;
import com.onix.customers.repositories.AuditEntryRepository;
import com.onix.customers.requests.PaginatedAuditsRequest;
import com.onix.customers.responses.AuditEntryResponse;
import com.onix.customers.responses.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditEntryService {

    private final AuditEntryRepository auditEntryRepository;
    private final AuditEntryMapper auditEntryMapper;

    public PageResponse<AuditEntryResponse> getPaginatedAudits(final PaginatedAuditsRequest request) {
        final var pageable = PageRequest.of(
                request.getPage() - 1,
                request.getItemsCount(),
                Sort.by(request.getOrderDirection(), request.getOrderBy())
        );

        final var customerPage = auditEntryRepository.findAll(pageable)
                .map(auditEntryMapper::toResponse);

        return new PageResponse<>(
                customerPage.getNumber() + 1,
                customerPage.getSize(),
                customerPage.getTotalPages(),
                customerPage.getTotalElements(),
                customerPage.getContent()
        );
    }

    public void log(
            final RequestAction action,
            final Long customerId,
            final String request,
            final RequestStatus status
    ) {
        final var auditEntry = new AuditEntryEntity();
        auditEntry.setAction(action.name());
        auditEntry.setCustomerId(customerId);
        auditEntry.setRequest(request);
        auditEntry.setCreatedAt(LocalDateTime.now());
        auditEntry.setStatus(status.name());
        auditEntryRepository.save(auditEntry);
    }
}
