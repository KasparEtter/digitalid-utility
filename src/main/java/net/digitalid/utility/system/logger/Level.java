package net.digitalid.utility.system.logger;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;

/**
 * This class enumerates the various level of log messages.
 * 
 * @see Logger
 * 
 * @author Kaspar Etter (kaspar.etter@digitalid.net)
 * @version 1.0.0
 */
public enum Level {
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Constants –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * The level for verbose.
     */
    VERBOSE(0),
    
    /**
     * The level for debugging.
     */
    DEBUGGING(1),
    
    /**
     * The level for information.
     */
    INFORMATION(2),
    
    /**
     * The level for warnings.
     */
    WARNING(3),
    
    /**
     * The level for errors.
     */
    ERROR(4),
    
    /**
     * The level for off.
     */
    OFF(5);
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Value –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Stores the byte representation of this level.
     */
    private final byte value;
    
    /**
     * Returns the byte representation of this level.
     * 
     * @return the byte representation of this level.
     */
    @Pure
    public byte getValue() {
        return value;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Constructor –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    /**
     * Creates a new level with the given value.
     * 
     * @param value the value encoding the level.
     */
    private Level(int value) {
        this.value = (byte) value;
    }
    
    /* –––––––––––––––––––––––––––––––––––––––––––––––––– Object –––––––––––––––––––––––––––––––––––––––––––––––––– */
    
    @Pure
    @Override
    public @Nonnull String toString() {
        final @Nonnull String string = name().toLowerCase();
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
}
