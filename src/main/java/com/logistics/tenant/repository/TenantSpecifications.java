package com.logistics.tenant.repository;

import com.logistics.tenant.entity.TenantEntity;
import com.logistics.tenant.entity.TenantStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public final class TenantSpecifications {

    private TenantSpecifications() {}

    public static Specification<TenantEntity> statusEquals(TenantStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<TenantEntity> nameContains(String name) {
        return (root, query, cb) ->
                name == null ? null :
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<TenantEntity> createdFrom(Instant from) {
        return (root, query, cb) ->
                from == null ? null : cb.greaterThanOrEqualTo(root.get("createdAt"), from);
    }

    public static Specification<TenantEntity> createdTo(Instant to) {
        return (root, query, cb) ->
                to == null ? null : cb.lessThanOrEqualTo(root.get("createdAt"), to);
    }
}

