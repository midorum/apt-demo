package org.example.apt.processors;

import org.example.apt.annotations.LogDecorator;
import org.example.apt.domain.ClassInfo;
import org.example.apt.domain.MethodInfo;
import org.example.apt.domain.TypeInfo;
import org.example.apt.domain.VariableInfo;
import org.example.template.VelocityTemplateWriter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"org.example.apt.annotations.LogDecorator"})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class DecoratorProcessor extends AbstractProcessor {
    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(LogDecorator.class)) {
            note("LogDecorator annotation found in " + elem.getSimpleName());
            if (elem.getKind() == ElementKind.CLASS) {
                final ClassInfo classInfo = getClassInfo((TypeElement) elem);
                if (classInfo.fqTypeName() != null) {
                    note("Class info will be applied decorator: " + classInfo);
                    generateDecoratorClass(classInfo);
                }
            }
        }
        // process other possible decorators same way
        return true; // no further processing of this annotation type
    }

    private ClassInfo getClassInfo(final TypeElement typeElement) {

        final List<MethodInfo> methods = new ArrayList<>();
        for (Element el : typeElement.getEnclosedElements()) {
            if (el.getKind() == ElementKind.METHOD) {
                final ExecutableElement ee = (ExecutableElement) el;
                final Set<Modifier> modifiers = ee.getModifiers();
                if (!modifiers.contains(Modifier.PUBLIC)) {
                    continue;
                }
                List<String> modifiersList = convertModifiersSetToList(modifiers);

                final List<VariableInfo> parameters = ee.getParameters().stream()
                        .map(variableElement -> new VariableInfo(variableElement.asType().toString(), variableElement.getSimpleName().toString()))
                        .toList();

                final List<TypeInfo> thrownTypes = ee.getThrownTypes().stream()
                        .map(tt -> new TypeInfo(tt.toString(), tt.toString()))
                        .toList();

                final MethodInfo methodInfo = new MethodInfo(ee.getSimpleName().toString(),
                        modifiersList,
                        ee.getReturnType().toString(),
                        parameters,
                        thrownTypes);

                methods.add(methodInfo);
            }
        }

        return new ClassInfo(typeElement.getQualifiedName().toString(),
                typeElement.getSimpleName().toString(),
                ((PackageElement) typeElement.getEnclosingElement()).getQualifiedName().toString(),
                Collections.EMPTY_LIST,
                methods);
    }

    private List<String> convertModifiersSetToList(final Set<Modifier> modifiers) {
        final ArrayList<String> list = new ArrayList<>();
        if (modifiers.contains(Modifier.PUBLIC)) list.add(Modifier.PUBLIC.name().toLowerCase());
        if (modifiers.contains(Modifier.PRIVATE)) list.add(Modifier.PRIVATE.name().toLowerCase());
        if (modifiers.contains(Modifier.PROTECTED)) list.add(Modifier.PROTECTED.name().toLowerCase());
        if (modifiers.contains(Modifier.STATIC)) list.add(Modifier.STATIC.name().toLowerCase());
        return list;
    }

    private void generateDecoratorClass(final ClassInfo classInfo) {
        final String templateName = "log-decorator.vm";
        try {
            final JavaFileObject jfo = processingEnv.getFiler()
                    .createSourceFile(classInfo.fqTypeName() + "Logged");
            note("creating source file: " + jfo.toUri());

            try (final Writer writer = jfo.openWriter()) {
                VelocityTemplateWriter.INSTANCE.applyTemplate(templateName, classInfo, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void note(final Object o) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, o.toString());
    }

    private void note(final String msg, final Element e) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg, e);
    }

}
