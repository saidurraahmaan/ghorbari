package com.s4r.ghorbari.core.entity;

import com.s4r.ghorbari.core.context.TenantContext;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = Long.class))
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public abstract class TenantAwareEntity extends BaseEntity {

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @PrePersist
    @PreUpdate
    private void setTenantId() {
        if (this.tenantId == null) {
            Long currentTenantId = TenantContext.getCurrentTenantId();
            if (currentTenantId == null) {
                throw new IllegalStateException("Tenant context is not set. Cannot persist tenant-aware entity.");
            }
            this.tenantId = currentTenantId;
        }
    }

    // Getters and Setters
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
