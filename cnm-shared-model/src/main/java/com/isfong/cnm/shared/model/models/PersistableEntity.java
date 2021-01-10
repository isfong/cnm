package com.isfong.cnm.shared.model.models;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@Getter
@MappedSuperclass
public abstract class PersistableEntity< ID > extends Entity< ID > {
    private Long createdAt;
    private Long updatedAt;
    @Version
    private Long version;

    @PrePersist
    void setInitialDate( ) {
        createdAt = updatedAt = System.currentTimeMillis( );
    }

    @PreUpdate
    void updateDate( ) {
        updatedAt = System.currentTimeMillis( );
    }
}
