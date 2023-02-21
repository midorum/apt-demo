package org.example;

import org.example.domain.Bean;
import org.example.exceptions.InvalidBeanException;
import org.example.persistence.DemoPersistence;
import org.example.exceptions.DuplicateBeanException;
import org.example.service.DemoService;
import org.example.service.DemoServiceLogged;

public class Main {
    public static void main(String[] args) throws InvalidBeanException, DuplicateBeanException {
        final DemoService demoService = new DemoService(new DemoPersistence());
        demoService.addBean(new Bean("1", "one"));
        System.out.println(demoService.getBean("1"));
        // using generated decorator
        final DemoServiceLogged demoServiceLogged = new DemoServiceLogged(demoService);
        demoServiceLogged.getBean("1");
        demoServiceLogged.addBean(new Bean("2", "two"));
        demoServiceLogged.getBean("2");
        demoServiceLogged.updateBean("2", new Bean("2", "tree"));
        demoServiceLogged.getBean("2");

    }
}
