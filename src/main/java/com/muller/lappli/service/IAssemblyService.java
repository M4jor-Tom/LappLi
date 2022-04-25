package com.muller.lappli.service;

import com.muller.lappli.domain.abstracts.AbstractAssembly;

public interface IAssemblyService<T extends AbstractAssembly<T>> extends IService<T> {}
