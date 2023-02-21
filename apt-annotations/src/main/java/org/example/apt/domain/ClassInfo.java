package org.example.apt.domain;

import java.util.List;

public record ClassInfo(String fqTypeName,
                        String typeName,
                        String packageName,
                        List<VariableInfo> fields,
                        List<MethodInfo> methods) {
}