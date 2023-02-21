package org.example.template;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.example.apt.domain.ClassInfo;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;

public class VelocityTemplateWriter implements TemplateApplier {

    public static VelocityTemplateWriter INSTANCE = new VelocityTemplateWriter();
    private final VelocityEngine engine;
    private final Properties props;

    private VelocityTemplateWriter() {
        props = loadVelocityProperties();
        engine = getVelocityEngine(props);
    }

    @Override
    public void applyTemplate(final String templateName, final ClassInfo classInfo, final Writer writer) {
        final Template template = loadVelocityTemplate(engine, templateName);
        final VelocityContext context = fillVelocityContext(classInfo);
        template.merge(context, writer);
    }

    private VelocityContext fillVelocityContext(final ClassInfo classInfo) {
        final VelocityContext vc = new VelocityContext();
        vc.put("packageName", classInfo.packageName());
        vc.put("fqClassName", classInfo.fqTypeName());
        vc.put("className", classInfo.typeName());
        vc.put("fields", classInfo.fields());
        vc.put("methods", classInfo.methods());
        return vc;
    }

    private Template loadVelocityTemplate(final VelocityEngine engine, final String template) {
        return engine.getTemplate(template);
    }

    private VelocityEngine getVelocityEngine(final Properties props) {
        VelocityEngine ve = new VelocityEngine(props);
        ve.init();
        return ve;
    }

    private Properties loadVelocityProperties() {
        final Properties props = new Properties();
        final URL url = this.getClass().getClassLoader().getResource("velocity.properties");
        try {
            props.load(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
