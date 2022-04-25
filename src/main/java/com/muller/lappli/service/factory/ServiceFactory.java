package com.muller.lappli.service.factory;

import com.muller.lappli.domain.interfaces.IDomainObject;
import com.muller.lappli.service.IService;

public class ServiceFactory {

    public IService<?> getServiceByDomainClass(Class<? extends IDomainObject<?>> domainClass) throws ServiceNotFoundException {
        throw new ServiceNotFoundException();
    }
}
