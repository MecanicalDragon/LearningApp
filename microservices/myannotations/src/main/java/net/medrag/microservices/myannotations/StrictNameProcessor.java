package net.medrag.microservices.myannotations;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@AutoService(Processor.class)
//@SupportedAnnotationTypes("net.medrag.microservices.myannotations.StrictName")
//@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class StrictNameProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(StrictName.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_17;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements) {
                final var simpleName = annotatedElement.getSimpleName().toString();
                final var requiredName = annotatedElement.getAnnotation(StrictName.class).value();
                if (!simpleName.equals(requiredName)) {
                    final var type = annotatedElement.getKind().name();
                    final var message = String.format("Compilation failed! %s <%s> must have @StrictName <%s>!", type, simpleName, requiredName);
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
                    throw new RuntimeException(message);
                }
            }
        }
        return true;
    }
}
