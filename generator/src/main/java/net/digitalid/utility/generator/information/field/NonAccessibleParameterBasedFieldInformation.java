package net.digitalid.utility.generator.information.field;

import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.processing.logging.SourcePosition;

/**
 * This type collects the relevant information about a directly accessible parameter-based field for generating a {@link net.digitalid.utility.generator.SubclassGenerator subclass} and {@link net.digitalid.utility.generator.BuilderGenerator builder}.
 */
public class NonAccessibleParameterBasedFieldInformation extends NonAccessibleDeclaredFieldInformation implements ParameterBasedFieldInformation {
    
    /* -------------------------------------------------- Parameter -------------------------------------------------- */
    
    @Pure
    @Override
    public @Nonnull VariableElement getParameter() {
        return (VariableElement) getElement();
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    protected NonAccessibleParameterBasedFieldInformation(@Nonnull VariableElement parameter, @Nonnull NonAccessibleDeclaredFieldInformation nonDirectlyAccessibleFieldInformation) {
        super(parameter, parameter.asType(), nonDirectlyAccessibleFieldInformation.getContainingType());
        
        Require.that(parameter.getKind() == ElementKind.PARAMETER).orThrow("The element $ has to be a parameter.", SourcePosition.of(parameter));
    }
    
    /**
     * Returns the field information of the given parameter, containing type, getter and setter which can be used to access the value.
     * 
     * @require parameter.getKind() == ElementKind.PARAMETER : "The element has to be a parameter.";
     * @require getter.isGetter() : "The first method has to be a getter.";
     * @require setter == null || setter.isSetter() : "The second method has to be null or a setter.";
     */
    @Pure
    public static @Nonnull NonAccessibleParameterBasedFieldInformation of(@Nonnull VariableElement parameter, @Nonnull NonAccessibleDeclaredFieldInformation nonAccessibleDeclaredFieldInformation) {
        return new NonAccessibleParameterBasedFieldInformation(parameter, nonAccessibleDeclaredFieldInformation);
    }
    
}