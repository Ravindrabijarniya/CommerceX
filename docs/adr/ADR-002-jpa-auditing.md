# ADR-002: Use Spring Data JPA Auditing

## Status
Accepted

## Context

All domain entities require consistent audit information such as creation and modification timestamps.

## Decision

Use Spring Data JPA Auditing with:

- @EnableJpaAuditing
- @CreatedDate
- @LastModifiedDate
- AuditingEntityListener

Audit fields are inherited through BaseEntity.

## Consequences

- Eliminates duplicate timestamp logic
- Keeps business services clean
- Ensures consistent audit data
- Provides a foundation for future @CreatedBy and @LastModifiedBy support
