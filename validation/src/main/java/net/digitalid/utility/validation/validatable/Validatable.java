package net.digitalid.utility.validation.validatable;

import net.digitalid.utility.contracts.exceptions.InvariantViolationException;
import net.digitalid.utility.validation.annotations.method.CallSuper;
import net.digitalid.utility.validation.annotations.method.Pure;

/**
 * This interface allows to validate the invariant of implementing classes.
 */
public interface Validatable {
    
    /**
     * Validates the invariant and all non-private fields of this object.
     * Typically, the programmer implements this method to check all non-
     * trivial invariants which cannot be expressed through annotations
     * and the field checks are generated by an annotation processor in
     * a subclass by overriding this method and calling the supermethod.
     * 
     * @throws InvariantViolationException if the invariant is violated.
     */
    @Pure
    @CallSuper
    public void validate();
    
}