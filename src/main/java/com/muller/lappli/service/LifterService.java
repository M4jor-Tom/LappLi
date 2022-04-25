package com.muller.lappli.service;

import com.muller.lappli.domain.Lifter;
import com.muller.lappli.domain.abstracts.AbstractLiftedSupply;
import java.util.List;

/**
 * Service Interface for managing {@link Lifter}.
 */
public interface LifterService extends IService<Lifter> {
    /**
     * Get bests lifters
     * @param abstractLiftedSupply the targeted supply
     *
     * @return the list of bests lifters
     */
    List<Lifter> findBestLifterList(AbstractLiftedSupply<?> abstractLiftedSupply);

    /**
     * Get eligibles lifters
     * @param abstractLiftedSupply the targeted supply
     *
     * @return the list of eligibles lifters
     */
    List<Lifter> findEligibleLifterList(AbstractLiftedSupply<?> abstractLiftedSupply);
}
