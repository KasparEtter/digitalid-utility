package net.digitalid.utility.processor.generator;

import java.io.IOException;

import javax.annotation.Nonnull;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.contracts.exceptions.PreconditionViolationException;
import net.digitalid.utility.logging.processing.AnnotationLog;
import net.digitalid.utility.processor.generator.annotations.NonWrittenRecipient;
import net.digitalid.utility.string.QuoteString;
import net.digitalid.utility.validation.annotations.method.Pure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * Subclasses of this class generate files during annotation processing.
 * 
 * @see ServiceFileGenerator
 * @see JavaFileGenerator
 */
@Mutable
public abstract class FileGenerator {
    
    /* -------------------------------------------------- Name -------------------------------------------------- */
    
    /**
     * Returns the name of this generated file.
     */
    @Pure
    public abstract @Nonnull String getName();
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return QuoteString.inSingle(getName());
    }
    
    /* -------------------------------------------------- Written -------------------------------------------------- */
    
    private boolean written = false;
    
    /**
     * Returns whether this generated file has already been written.
     */
    @Pure
    public boolean isWritten() {
        return written;
    }
    
    /**
     * Throws a {@link PreconditionViolationException} if this file has already been written.
     */
    @Pure
    protected void requireNotWritten() {
        Require.that(!written).orThrow("The generated file " + this + " has already been written.");
    }
    
    /* -------------------------------------------------- Writing -------------------------------------------------- */
    
    /**
     * Writes the generated file to the file system.
     * This method is guaranteed to be called only once.
     */
    @NonWrittenRecipient
    protected abstract void writeOnce() throws IOException;
    
    /**
     * Writes the generated file to the file system and returns whether it was successful.
     */
    @NonWrittenRecipient
    public final boolean write() {
        requireNotWritten();
        
        try {
            writeOnce();
        } catch (@Nonnull IOException exception) {
            AnnotationLog.error("A problem occurred while generating the file " + this + ": " + exception);
            return false;
        }
        
        AnnotationLog.information("Generated the file " + this);
        this.written = true;
        return true;
    }
    
}