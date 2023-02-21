package org.example.persistence;

import org.example.domain.Bean;
import org.example.exceptions.DuplicateBeanException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DemoPersistence {

    private final Map<String, Bean> storage = new ConcurrentHashMap<>();

    public Bean findBeanById(final String id) {
        return storage.get(id);
    }

    public void storeBean(final Bean bean) throws DuplicateBeanException {
        if (storage.containsKey(bean.getId()))
            throw new DuplicateBeanException("Bean with id=" + bean.getId() + " already exists in storage");
        storage.put(bean.getId(), bean);
    }

    public void updateBean(final String id, final Bean bean) {
        if (!bean.getId().equals(id))
            throw new IllegalArgumentException("Bean id=" + bean.getId() + " does not correspond passed id=" + id);
        if (!storage.containsKey(id)) throw new IllegalStateException("Bean with id=" + id + " not found in storage");
        storage.put(bean.getId(), bean);
    }
}
