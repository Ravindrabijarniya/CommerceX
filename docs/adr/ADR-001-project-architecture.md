# ADR-001: Feature-Based Architecture

## Status

Accepted

## Context

CommerceX is intended to be a production-grade backend application.

Traditional package-by-layer architecture becomes difficult to maintain as projects grow.

## Decision

Use feature-based packaging.

Example:

auth/

product/

order/

inventory/

instead of:

controller/

service/

repository/

## Consequences

- Better modularity
- Easier navigation
- Better scalability
- Cleaner ownership
