package net.digitalid.utility.conversion.converter.types;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.ownership.NonCaptured;
import net.digitalid.utility.annotations.parameter.Modified;
import net.digitalid.utility.annotations.parameter.Unmodified;
import net.digitalid.utility.conversion.converter.LeafConverter;
import net.digitalid.utility.conversion.converter.ValueCollector;

/**
 *
 */
public class Binary256Converter implements LeafConverter<Byte[]> {
    
    public static final @Nonnull Binary256Converter INSTANCE = new Binary256Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Byte[] object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
