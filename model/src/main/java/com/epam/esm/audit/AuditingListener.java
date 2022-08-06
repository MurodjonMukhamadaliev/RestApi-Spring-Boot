package com.epam.esm.audit;

import com.epam.esm.entity.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuditingListener {

    @PrePersist
    void onPrePersist(BaseEntity baseEntity){
        baseEntity.setId(UUID.randomUUID());
        baseEntity.setCreateDate(LocalDateTime.now());
        baseEntity.setLastUpdateDate(LocalDateTime.now());
    }

    @PreUpdate
    void onPreUpdate(BaseEntity baseEntity){
        baseEntity.setLastUpdateDate(LocalDateTime.now());
    }

}
