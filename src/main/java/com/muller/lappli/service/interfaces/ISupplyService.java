package com.muller.lappli.service.interfaces;

import com.muller.lappli.domain.abstracts.AbstractSupply;
import java.util.Set;

public interface ISupplyService<T extends AbstractSupply> extends ISpecificationExecutorService<T> {
    /**
     * Returns a supplies Set from a strand id
     *
     * @param id the id of the strand
     * @return a Set of supplies
     */
    public abstract Set<T> findByStrandId(Long id);
}
