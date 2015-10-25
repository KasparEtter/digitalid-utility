package net.digitalid.utility.system.errors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.digitalid.utility.annotations.state.Immutable;
import net.digitalid.utility.system.logger.Log;

/**
 * This exception is thrown when a fatal error occurs.
 * 
 * @see InitializationError
 * @see ShouldNeverHappenError
 */
@Immutable
public abstract class FatalError extends Error {
    
    /**
     * Creates a new fatal error with the given message.
     * 
     * @param message a string explaining the error.
     */
    protected FatalError(@Nonnull String message) {
        this(message, null);
    }
    
    /**
     * Creates a new fatal error with the given cause.
     * 
     * @param cause a reference to the cause of the error.
     */
    protected FatalError(@Nonnull Throwable cause) {
        this(null, cause);
    }
    
    /**
     * Creates a new fatal error with the given message and cause.
     * 
     * @param message a string explaining the error.
     * @param cause a reference to the cause of the error.
     */
    protected FatalError(@Nullable String message, @Nullable Throwable cause) {
        super(message == null ? "A fatal error occurred." : message, cause);
        
        Log.error("A fatal error occurred.", this);
    }
    
}
