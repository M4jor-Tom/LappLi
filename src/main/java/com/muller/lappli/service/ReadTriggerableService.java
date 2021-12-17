package com.muller.lappli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Must be implemented on Spring Service classes which
 * <p>
 * want to execute some procedures upon reading.
 * <p>
 * <p>
 * Default implementations are provided for specific data reading
 * <p>
 * such as List and Optional, meant to be used in findAll and findOne methods
 * <p>
 * These methods are
 * <pre>Optional onOptionalRead(Optional domainObjectOptional)</pre>
 * To be used in
 * <pre>Optional Service.findOne()</pre>
 * and
 * <pre>List onListRead(List domainObjectList)</pre>
 * To be used in
 * <pre>List Service.findAll()</pre>
 * All their behaviors depend on the override of
 * <pre>T onRead(T domainObject)</pre>
 */
public interface ReadTriggerableService<T> {
    /**
     * Changes an Optional upon reading
     *
     * @param domainObjectOptional the Optional to read
     * @return the read Optional
     */
    public default Optional<T> onOptionalRead(Optional<T> domainObjectOptional) {
        if (domainObjectOptional.isPresent()) {
            return Optional.of(onRead(domainObjectOptional.get()));
        }

        return domainObjectOptional;
    }

    /**
     * Changes an List upon reading
     *
     * @param domainObjectList the List to read
     * @return the read List
     */
    public default List<T> onListRead(List<T> domainObjectList) {
        List<T> editedDomainObjectList = new ArrayList<T>();

        for (T domainObject : domainObjectList) {
            editedDomainObjectList.add(onRead(domainObject));
        }

        return editedDomainObjectList;
    }

    /**
     * The domainObject reading procedure
     *
     * @param domainObject the entity to read
     * @return the read entity
     */
    public T onRead(T domainObject);
}