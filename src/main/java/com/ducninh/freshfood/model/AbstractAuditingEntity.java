package com.ducninh.freshfood.model;

import lombok.*;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class AbstractAuditingEntity implements Serializable {

    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;

    @PrePersist
    public void prePersist(){
        setCreatedDate(Instant.now());
    }

    @PreUpdate
    public void preUpdate(){
        setLastModifiedDate(Instant.now());
    }

}
