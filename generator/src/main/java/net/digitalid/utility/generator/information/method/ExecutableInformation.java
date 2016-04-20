package net.digitalid.utility.generator.information.method;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.generator.information.ElementInformationImplementation;
import net.digitalid.utility.generator.information.field.DirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.generator.information.field.NonDirectlyAccessibleDeclaredFieldInformation;
import net.digitalid.utility.processing.logging.ProcessingLog;
import net.digitalid.utility.processing.utility.StaticProcessingEnvironment;

/**
 * This type collects the relevant information about an executable for generating a {@link net.digitalid.utility.generator.SubclassGenerator subclass} and {@link net.digitalid.utility.generator.BuilderGenerator builder}.
 * 
 * @see ConstructorInformation
 * @see MethodInformation
 */
public abstract class ExecutableInformation extends ElementInformationImplementation {
    
    /* -------------------------------------------------- Element -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull ExecutableElement getElement() {
        return (ExecutableElement) super.getElement();
    }
    
    /* -------------------------------------------------- Type -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull ExecutableType getType() {
        return (ExecutableType) super.getType();
    }
    
    /* -------------------------------------------------- Parameters -------------------------------------------------- */
    
    @Pure
    public boolean hasParameters() {
        return !getElement().getParameters().isEmpty();
    }
    
    @Pure
    public boolean hasSingleParameter() {
        return getElement().getParameters().size() == 1;
    }
    
    @Pure
    public boolean hasSingleParameter(@Nonnull String desiredTypeName) {
        if (hasSingleParameter()) {
            final @Nonnull String parameterTypeName = getElement().getParameters().get(0).asType().toString();
            ProcessingLog.verbose("Parameter type: $, desired type: $", parameterTypeName, desiredTypeName);
            return parameterTypeName.equals(desiredTypeName);
        } else {
            return false;
        }
    }
    
    @Pure
    public boolean hasSingleParameter(@Nonnull Class<?> type) {
        return hasSingleParameter(type.getCanonicalName());
    }
    
    /* -------------------------------------------------- Method Parameters -------------------------------------------------- */
    
    /**
     * An iterable of parameters.
     */
    private final @Nonnull FiniteIterable<@Nonnull MethodParameterInformation> parameters;
    
    /**
     * Returns a list of parameters.
     */
    @Pure
    public @Nonnull FiniteIterable<MethodParameterInformation> getParameters() {
        return parameters;
        
    }
    
    /* -------------------------------------------------- Exceptions -------------------------------------------------- */
    
    @Pure
    public boolean throwsExceptions() {
        return !getElement().getThrownTypes().isEmpty();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected ExecutableInformation(@Nonnull Element element, @Nonnull DeclaredType containingType) {
        super(element, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, element), containingType);
        
        final @Nonnull List<MethodParameterInformation> parameterList = new ArrayList<>(getElement().getParameters().size());
        for (@Nonnull VariableElement variableElement : getElement().getParameters()) {
            parameterList.add(new MethodParameterInformation(variableElement, getContainingType()));
        }
        this.parameters = FiniteIterable.of(parameterList);
    }
    
    protected ExecutableInformation(@Nonnull Element element, @Nonnull DeclaredType containingType, @Nonnull FiniteIterable<@Nonnull DirectlyAccessibleDeclaredFieldInformation> directlyAccessibleDeclaredFieldInformation, @Nonnull FiniteIterable<@Nonnull NonDirectlyAccessibleDeclaredFieldInformation> nonDirectlyAccessibleDeclaredFieldInformation) {
        super(element, StaticProcessingEnvironment.getTypeUtils().asMemberOf(containingType, element), containingType);
        
        final @Nonnull List<MethodParameterInformation> parameterList = new ArrayList<>(getElement().getParameters().size());
        for (@Nonnull VariableElement variableElement : getElement().getParameters()) {
            parameterList.add(new MethodParameterInformation(variableElement, getContainingType(), directlyAccessibleDeclaredFieldInformation, nonDirectlyAccessibleDeclaredFieldInformation));
        }
        this.parameters = FiniteIterable.of(parameterList);
    }
    
}
