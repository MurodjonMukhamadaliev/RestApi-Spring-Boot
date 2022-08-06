package com.epam.esm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(UUID id, LocalDateTime createDate, LocalDateTime lastUpdateDate, String name) {
        super(id, createDate, lastUpdateDate);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
