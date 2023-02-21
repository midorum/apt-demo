package org.example.template;

import org.example.apt.domain.ClassInfo;

import java.io.Writer;

public interface TemplateApplier {

    void applyTemplate(String template, ClassInfo classInfo, Writer writer);
}
