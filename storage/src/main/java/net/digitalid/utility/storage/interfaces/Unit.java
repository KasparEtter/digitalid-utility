package net.digitalid.utility.storage.interfaces;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.rootclass.RootInterface;
import net.digitalid.utility.validation.annotations.size.MaxSize;
import net.digitalid.utility.validation.annotations.string.CodeIdentifier;
import net.digitalid.utility.validation.annotations.type.Functional;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This interface models a database unit.
 * A unit is modeled as a database schema.
 */
@Immutable
@Functional
public interface Unit extends RootInterface {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of this unit.
     */
    @Pure
    public abstract @Nonnull @CodeIdentifier @MaxSize(63) String getName();
    
    /* -------------------------------------------------- Default -------------------------------------------------- */
    
    /**
     * Stores a default instance of a database unit.
     */
    public static final @Nonnull Unit DEFAULT = () -> "default";
    
}
