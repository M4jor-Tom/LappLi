package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Element;
import com.muller.lappli.repository.ElementRepository;
import com.muller.lappli.service.ElementKindService;
import com.muller.lappli.service.ElementService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Element}.
 */
@Service
@Transactional
public class ElementServiceImpl implements ElementService {

    private final Logger log = LoggerFactory.getLogger(ElementServiceImpl.class);

    private final ElementRepository elementRepository;

    private final ElementKindService elementKindService;

    public ElementServiceImpl(ElementRepository elementRepository, ElementKindService elementKindService) {
        this.elementRepository = elementRepository;
        this.elementKindService = elementKindService;
    }

    @Override
    public Element save(Element element) {
        log.debug("Request to save Element : {}", element);
        return elementRepository.save(element);
    }

    @Override
    public Optional<Element> partialUpdate(Element element) {
        log.debug("Request to partially update Element : {}", element);

        return elementRepository
            .findById(element.getId())
            .map(existingElement -> {
                if (element.getNumber() != null) {
                    existingElement.setNumber(element.getNumber());
                }
                if (element.getColor() != null) {
                    existingElement.setColor(element.getColor());
                }

                return existingElement;
            })
            .map(elementRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Element> findAll() {
        log.debug("Request to get all Elements");
        return onListRead(elementRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Element> findOne(Long id) {
        log.debug("Request to get Element : {}", id);
        return onOptionalRead(elementRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Element : {}", id);
        elementRepository.deleteById(id);
    }

    @Override
    public Element onRead(Element element) {
        return element.elementKind(elementKindService.onRead(element.getElementKind()));
    }
}
