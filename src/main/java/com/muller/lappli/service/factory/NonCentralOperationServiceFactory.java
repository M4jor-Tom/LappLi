package com.muller.lappli.service.factory;

import com.muller.lappli.domain.ContinuityWireLongitLaying;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.domain.Plait;
import com.muller.lappli.domain.Screen;
import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.domain.StripLaying;
import com.muller.lappli.domain.TapeLaying;
import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.service.INonCentralOperationService;

public class NonCentralOperationServiceFactory {

    @SuppressWarnings("unchecked")
    public Class<? extends INonCentralOperationService<?>> getServiceByDomainClass(Class<? extends INonCentralOperation<?>> domainClass)
        throws ServiceNotFoundException {
        //[NON_CENTRAL_OPERATION]
        if (CoreAssembly.class.equals(domainClass)) {
            return (Class<? extends INonCentralOperationService<?>>) CoreAssembly.class;
        } else if (IntersticeAssembly.class.equals(domainClass)) {
            return (Class<? extends INonCentralOperationService<?>>) IntersticeAssembly.class;
        } else if (TapeLaying.class.equals(domainClass)) {
            return (Class<? extends INonCentralOperationService<?>>) TapeLaying.class;
        } else if (Screen.class.equals(domainClass)) {
            return (Class<? extends INonCentralOperationService<?>>) Screen.class;
        } else if (StripLaying.class.equals(domainClass)) {
            return (Class<? extends INonCentralOperationService<?>>) StripLaying.class;
        } else if (Plait.class.equals(domainClass)) {
            return (Class<? extends INonCentralOperationService<?>>) Plait.class;
        } else if (Sheathing.class.equals(domainClass)) {
            return (Class<? extends INonCentralOperationService<?>>) Sheathing.class;
        } else if (ContinuityWireLongitLaying.class.equals(domainClass)) {
            return (Class<? extends INonCentralOperationService<?>>) ContinuityWireLongitLaying.class;
        }

        throw new ServiceNotFoundException();
    }
}
