package com.onix.customers.repositories;

import com.onix.customers.entities.AuditEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEntryRepository extends JpaRepository<AuditEntryEntity, Long> {
}
