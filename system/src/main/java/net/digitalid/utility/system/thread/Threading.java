package net.digitalid.utility.system.thread;

import javax.annotation.Nonnull;
import net.digitalid.utility.annotations.state.Pure;
import net.digitalid.utility.annotations.state.Stateless;

/**
 * This class facilitates the handling of threads.
 */
@Stateless
public final class Threading {
    
    /* -------------------------------------------------- Main Thread -------------------------------------------------- */
    
    /**
     * Stores the main thread.
     */
    private static final @Nonnull Thread main = Thread.currentThread();
    
    /**
     * Returns whether the current thread is the main thread.
     * 
     * @return whether the current thread is the main thread.
     */
    @Pure
    public static final boolean isMainThread() {
        return Thread.currentThread() == main;
    }
    
}