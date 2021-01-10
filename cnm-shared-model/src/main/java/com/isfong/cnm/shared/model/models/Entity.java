package com.isfong.cnm.shared.model.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Entity< ID > implements Persistable< ID > {
    @Transient
    private boolean isNew = true;

    @Override
    @JsonIgnore
    public boolean isNew( ) {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew( ) {
        this.isNew = false;
    }
}