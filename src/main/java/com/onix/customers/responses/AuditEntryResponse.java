package com.onix.customers.responses;

public record AuditEntryResponse(Long id, String action, Long customerId, String request, String status,
                                 String createdAt) {
}
