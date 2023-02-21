package org.example.service;

import org.example.apt.annotations.LogDecorator;
import org.example.domain.Bean;
import org.example.exceptions.InvalidBeanException;
import org.example.persistence.DemoPersistence;
import org.example.exceptions.DuplicateBeanException;

@LogDecorator
public class DemoService {

    private final DemoPersistence persistence;

    public DemoService(final DemoPersistence persistence) {
        this.persistence = persistence;
    }

    public void addBean(final Bean bean) throws InvalidBeanException, DuplicateBeanException {
        if (isValid(bean)) {
            persistence.storeBean(bean);
        } else {
            throw new InvalidBeanException("Bean is not valid: " + bean);
        }
    }

    public void updateBean(final String id, final Bean bean) throws InvalidBeanException {
        if (isValid(bean)) {
            persistence.updateBean(id, bean);
        } else {
            throw new InvalidBeanException("Bean is not valid: " + bean);
        }
    }

    public Bean getBean(final String id) {
        return persistence.findBeanById(id);
    }

    private boolean isValid(final Bean bean) {
        if (bean == null) return false;
        return bean.getId() != null && !bean.getId().isBlank();
    }

}
