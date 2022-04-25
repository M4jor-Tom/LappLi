package com.muller.lappli.service;

import com.muller.lappli.domain.SupplyPosition;
import java.util.List;

/**
 * Service Interface for managing {@link SupplyPosition}.
 */
public interface SupplyPositionService extends IService<SupplyPosition> {
    /**
     * Get all the SupplyPosition where OwnerCentralAssembly is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SupplyPosition> findAllWhereOwnerCentralAssemblyIsNull();
}
