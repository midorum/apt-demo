package org.example.apt.domain;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Set;

public record MethodInfo(String name, List<String> modifiers, String fqReturnType, List<VariableInfo> parameters, List<TypeInfo> thrownTypes) {
}
