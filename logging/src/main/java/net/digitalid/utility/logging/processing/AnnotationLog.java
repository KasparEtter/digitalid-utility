package net.digitalid.utility.logging.processing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.logging.Caller;
import net.digitalid.utility.logging.Level;
import net.digitalid.utility.logging.logger.FileLogger;
import net.digitalid.utility.logging.logger.Logger;

/**
 * This class makes it easier to log messages during annotation processing.
 */
public class AnnotationLog {
    
    /* -------------------------------------------------- Setup -------------------------------------------------- */
    
    /**
     * Sets the output file of the logger to the given non-nullable name.
     */
    public static void setUp(String name) {
        Require.that(name != null).orThrow("The name may not be null.");
        
        Logger.logger.set(FileLogger.with("target/processor-logs/" + name + ".log"));
        Level.threshold.set(Level.VERBOSE);
        Caller.index.set(6);
    }
    
    /* -------------------------------------------------- Mapping -------------------------------------------------- */
    
    /**
     * Stores a mapping from this library's logging levels to the corresponding diagnostic kind.
     */
    private static final Map<Level, Diagnostic.Kind> levelToKind = new HashMap<>(5);
    
    static {
        levelToKind.put(Level.VERBOSE, Diagnostic.Kind.OTHER);
        levelToKind.put(Level.DEBUGGING, Diagnostic.Kind.OTHER);
        levelToKind.put(Level.INFORMATION, Diagnostic.Kind.NOTE);
        levelToKind.put(Level.WARNING, Diagnostic.Kind.WARNING);
        levelToKind.put(Level.ERROR, Diagnostic.Kind.ERROR);
    }
    
    /* -------------------------------------------------- Logging -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position at the given non-nullable level.
     */
    private static void log(Level level, CharSequence message, SourcePosition position) {
        Require.that(level != null).orThrow("The level may not be null.");
        Require.that(message != null).orThrow("The message may not be null.");
        
        Logger.log(level, message.toString() + (position != null ? " " + position : ""), null);
        if (level.getValue() >= Level.INFORMATION.getValue() && AnnotationProcessing.environment.isSet()) {
            if (position == null) {
                AnnotationProcessing.environment.get().getMessager().printMessage(levelToKind.get(level), message);
            } else if (position.getAnnotationValue() != null) {
                AnnotationProcessing.environment.get().getMessager().printMessage(levelToKind.get(level), message, position.getElement(), position.getAnnotationMirror(), position.getAnnotationValue());
            } else if (position.getAnnotationMirror() != null) {
                AnnotationProcessing.environment.get().getMessager().printMessage(levelToKind.get(level), message, position.getElement(), position.getAnnotationMirror());
            } else {
                AnnotationProcessing.environment.get().getMessager().printMessage(levelToKind.get(level), message, position.getElement());
            }
        }
    }
    
    /* -------------------------------------------------- Error -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as an error.
     */
    public static void error(CharSequence message, SourcePosition position) {
        log(Level.ERROR, message, position);
    }
    
    /**
     * Logs the given non-nullable message as an error.
     */
    public static void error(CharSequence message) {
        log(Level.ERROR, message, null);
    }
    
    /* -------------------------------------------------- Warning -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as a warning.
     */
    public static void warning(CharSequence message, SourcePosition position) {
        log(Level.WARNING, message, position);
    }
    
    /**
     * Logs the given non-nullable message as a warning.
     */
    public static void warning(CharSequence message) {
        log(Level.WARNING, message, null);
    }
    
    /* -------------------------------------------------- Information -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position as information.
     */
    public static void information(CharSequence message, SourcePosition position) {
        log(Level.INFORMATION, message, position);
    }
    
    /**
     * Logs the given non-nullable message as information.
     */
    public static void information(CharSequence message) {
        log(Level.INFORMATION, message, null);
    }
    
    /* -------------------------------------------------- Debugging -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position for debugging.
     */
    public static void debugging(CharSequence message, SourcePosition position) {
        log(Level.DEBUGGING, message, position);
    }
    
    /**
     * Logs the given non-nullable message for debugging.
     */
    public static void debugging(CharSequence message) {
        log(Level.DEBUGGING, message, null);
    }
    
    /* -------------------------------------------------- Verbose -------------------------------------------------- */
    
    /**
     * Logs the given non-nullable message with the given nullable position only in verbose mode.
     */
    public static void verbose(CharSequence message, SourcePosition position) {
        log(Level.VERBOSE, message, position);
    }
    
    /**
     * Logs the given non-nullable message only in verbose mode.
     */
    public static void verbose(CharSequence message) {
        log(Level.VERBOSE, message, null);
    }
    
    /* -------------------------------------------------- Utility -------------------------------------------------- */
    
    /**
     * Logs the elements which are annotated with one of the given annotations of the given non-nullable round environment.
     * 
     * @require annotations != null : "The annotations may not be null.";
     * @require roundEnvironment != null : "The round environment may not be null.";
     */
    public static void annotatedElements(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        Require.that(annotations != null).orThrow("The annotations may not be null.");
        Require.that(roundEnvironment != null).orThrow("The round environment may not be null.");
        
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnvironment.getElementsAnnotatedWith(annotation)) {
                AnnotationLog.information("Found '@" + annotation.getSimpleName() + "' on", SourcePosition.of(element));
            }
        }
    }
    
    /**
     * Logs the root elements of the given non-nullable round environment.
     * 
     * @require roundEnvironment != null : "The round environment may not be null.";
     */
    public static void rootElements(RoundEnvironment roundEnvironment) {
        Require.that(roundEnvironment != null).orThrow("The round environment may not be null.");
        
        for (Element rootElement : roundEnvironment.getRootElements()) {
            AnnotationLog.information("Found the " + rootElement.getKind().toString().toLowerCase() + " '" + rootElement.asType().toString() + "'.");
        }
    }
    
}