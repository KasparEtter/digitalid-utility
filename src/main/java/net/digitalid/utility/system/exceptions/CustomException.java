package net.digitalid.utility.system.exceptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.system.logger.Log;

/**
 * All custom exceptions extend this class.
 */
@Immutable
public abstract class CustomException extends Exception {
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a new custom exception with the given message and cause.
     * 
     * @param message a string explaining the problem which has occurred.
     * @param cause the exception that caused this problem, if available.
     */
    protected CustomException(@Nonnull String message, @Nullable Exception cause) {
        super(message, cause);
        
        Log.warning("A problem occurred.", this);
    }
    
    /**
     * Creates a new custom exception with the given message.
     * 
     * @param message a string explaining the problem which has occurred.
     */
    protected CustomException(@Nonnull String message) {
        this(message, null);
    }
    
}
