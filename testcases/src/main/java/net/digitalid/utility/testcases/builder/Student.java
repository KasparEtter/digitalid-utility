package net.digitalid.utility.testcases.builder;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.annotations.math.Positive;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This class models a student.
 * 
 * Ideas:
 * - Instead of @Ignore or @Representation, interpret non-abstract methods as the former and abstract methods as the latter. What about mutable fields (due to setters)?
 */
@Mutable
public abstract class Student /* implements Convertible */ {
    
    /**
     * Returns the name of this student.
     */
    @Pure
    // @Logging
    // @Representation
    public abstract @Nonnull @MaxSize(64) String getName();
    
    public abstract void setName(@Nonnull @MaxSize(64) String name);
    
    /**
     * Returns the six digit ID of this student.
     */
    @Pure
    // @Logging
    // @Default("0)
    public abstract @Positive int getID();
    
//    @Pure
//    public abstract @Nonnull @NonNullableElements @NonFrozen ReadOnlyList<Student> getBuddies();
    
    @Pure
    // @Ignore
    public @Nonnull String getFormattedString() {
        return getName() + " (" + getID() + ")";
    }
    
    /* -------------------------------------------------- Convertible -------------------------------------------------- */
    
    @Pure
    public abstract @Nonnull Object[] getFieldValues();
    
}