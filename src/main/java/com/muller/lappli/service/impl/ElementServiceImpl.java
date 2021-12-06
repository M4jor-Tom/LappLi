package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Element;
import com.muller.lappli.repository.ElementRepository;
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

    public ElementServiceImpl(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
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
        return elementRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Element> findOne(Long id) {
        log.debug("Request to get Element : {}", id);
        return elementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Element : {}", id);
        elementRepository.deleteById(id);
    }
}
