package org.example.template;

import org.example.apt.domain.ClassInfo;
import org.example.apt.domain.MethodInfo;
import org.example.apt.domain.TypeInfo;
import org.example.apt.domain.VariableInfo;

import javax.lang.model.element.Modifier;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class VelocityTemplateWriterTest {

    public static void main(String[] args) throws IOException {
        testLogDecorator();
    }

    private static void testLogDecorator() throws IOException {
        System.out.println("test log decorator generating");
        final List<MethodInfo> methods = Arrays.asList(createFirstMethod(), createSecondMethod(), createThirdMethod());
        final ClassInfo classInfo = new ClassInfo("org.example.Generated", "Generated", "org.example",
                Collections.emptyList(), methods);
        try (FileWriter writer = new FileWriter("generated-decorator.java")) {
            VelocityTemplateWriter.INSTANCE.applyTemplate("log-decorator.vm", classInfo, writer);
        }
    }

    private static MethodInfo createFirstMethod() {
        return new MethodInfo("firstMethod",
                getPublicModifiersSet(),
                "void",
                Collections.emptyList(),
                Collections.emptyList());
    }

    private static MethodInfo createSecondMethod() {
        return new MethodInfo("secondMethod",
                getPublicStaticModifiersSet(),
                "java.util.String",
                Arrays.asList(new VariableInfo("java.util.String", "param1")),
                Arrays.asList(new TypeInfo("java.io.IOException", "java.io.IOException")));
    }

    private static MethodInfo createThirdMethod() {
        return new MethodInfo("thirdMethod",
                getPublicModifiersSet(),
                "int",
                Arrays.asList(new VariableInfo("java.util.String", "param1"),
                        new VariableInfo("int", "param2")),
                Arrays.asList(new TypeInfo("java.io.IOException", "java.io.IOException"),
                        new TypeInfo("java.io.FileNotFoundException", "java.io.FileNotFoundException")));
    }

    private static List<String> getPublicModifiersSet() {
        return new ArrayList<>() {{
            add(Modifier.PUBLIC.name().toLowerCase());
        }};
    }

    private static List<String> getPublicStaticModifiersSet() {
        return new ArrayList<>() {{
            add(Modifier.PUBLIC.name().toLowerCase());
            add(Modifier.STATIC.name().toLowerCase());
        }};
    }

}