package com.onix.customers.controllers;

import com.onix.customers.requests.PaginatedAuditsRequest;
import com.onix.customers.responses.AuditEntryResponse;
import com.onix.customers.responses.PageResponse;
import com.onix.customers.services.AuditEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditEntryService auditEntryService;

    @GetMapping
    public PageResponse<AuditEntryResponse> getAudits(final @Valid PaginatedAuditsRequest request) {
        return auditEntryService.getPaginatedAudits(request);
    }

}
