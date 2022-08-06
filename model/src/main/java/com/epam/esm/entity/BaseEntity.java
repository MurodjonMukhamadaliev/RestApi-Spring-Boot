package com.epam.esm.entity;

import com.epam.esm.audit.AuditingListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;



@MappedSuperclass
@EntityListeners(AuditingListener.class)
public abstract class BaseEntity {

    @Id
    protected UUID id;

    @Column(name = "create_date", updatable = false)
    protected LocalDateTime createDate;

    @Column(name = "last_update_date")
    protected LocalDateTime lastUpdateDate;

    public BaseEntity() {
    }

    public BaseEntity(UUID id, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
