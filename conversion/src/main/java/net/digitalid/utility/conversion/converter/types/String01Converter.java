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
public class String01Converter implements LeafConverter<Character> {
    
    public static final @Nonnull String01Converter INSTANCE = new String01Converter();
    
    @Override
    public void convert(@NonCaptured @Unmodified Character object, @NonCaptured @Modified ValueCollector valueCollector) {
        
    }
    
}
