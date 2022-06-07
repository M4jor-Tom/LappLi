package com.muller.lappli.domain.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muller.lappli.domain.Material;
import com.muller.lappli.domain.enumeration.SheathingKind;
import javax.persistence.*;
import javax.validation.constraints.*;

@MappedSuperclass
public abstract class AbstractSheathing<T extends AbstractSheathing<T>> extends AbstractOperation<T> {

    @NotNull
    @Column(name = "operation_layer", nullable = false)
    private Long operationLayer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "materialMarkingStatistics" }, allowSetters = true)
    private Material material;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sheathing_kind", nullable = false)
    private SheathingKind sheathingKind;

    public AbstractSheathing() {
        super();
    }

    public Long getOperationLayer() {
        return this.operationLayer;
    }

    public T operationLayer(Long operationLayer) {
        this.setOperationLayer(operationLayer);
        return getThis();
    }

    public void setOperationLayer(Long operationLayer) {
        this.operationLayer = operationLayer;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public T material(Material material) {
        this.setMaterial(material);
        return getThis();
    }

    public SheathingKind getSheathingKind() {
        return this.sheathingKind;
    }

    public T sheathingKind(SheathingKind sheathingKind) {
        this.setSheathingKind(sheathingKind);
        return getThis();
    }

    public void setSheathingKind(SheathingKind sheathingKind) {
        this.sheathingKind = sheathingKind;
    }
}
