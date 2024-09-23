package com.orca.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private Boolean active = true;

    private LocalDate creationDate = LocalDate.now();
    private LocalDateTime inactivationDate;

    public void setActive(Boolean active) {
        if (!active && !this.active.equals(active))
            this.inactivationDate = LocalDateTime.now();

        this.active = active;
    }

}

