/*
 * Copyright (C) 2017 Synacts GmbH, Switzerland (info@synacts.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.digitalid.utility.generator.typevisitors;

import java.util.Arrays;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;

import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.processor.generator.JavaFileGenerator;
import net.digitalid.utility.tuples.Quartet;

/**
 * The comparison type visitor generates code that compares two values with each other.
 * 
 * When using this type visitor, you need to call {@link javax.lang.model.util.AbstractTypeVisitor6#visit(TypeMirror, Object)} with a {@link Quartet quartet} of the left reference, the right reference and a {@link JavaFileGenerator java file generator}. 
 * The {@link javax.lang.model.util.AbstractTypeVisitor6#visit(TypeMirror)} method cannot create the reference names nor a java file generator.
 */
public class GenerateComparisonTypeVisitor extends SimpleTypeVisitor7<Object, Quartet<@Nonnull String, @Nonnull String, @Nonnull JavaFileGenerator, @Nonnull String>> {
    
    @Override
    protected Object defaultAction(TypeMirror e, Quartet<String, String, JavaFileGenerator, String> quartet) {
        Require.that(quartet != null).orThrow("The java file generator is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, Quartet<String, String, JavaFileGenerator, String>) instead.");
        assert quartet != null;
    
        final @Nonnull String leftReference = quartet.get0();
        final @Nonnull String rightReference = quartet.get1();
        final @Nonnull JavaFileGenerator fileGenerator = quartet.get2();
        final @Nonnull String result = quartet.get3();
    
        fileGenerator.addStatement(result + " = " + result + " && " + fileGenerator.importIfPossible(Objects.class) + ".equals(" + leftReference + ", " + rightReference + ")");
        return null;
    }
    
    @Override 
    public Object visitArray(@Nonnull ArrayType t, @Nullable Quartet<@Nonnull String, @Nonnull String, @Nonnull JavaFileGenerator, String> quartet) {
        Require.that(quartet != null).orThrow("The java file generator is a required parameter and cannot be generated on the fly. Please call visit(TypeMirror, Quartet<String, String, JavaFileGenerator, String>) instead.");
        assert quartet != null;
        
        final @Nonnull String leftReference = quartet.get0();
        final @Nonnull String rightReference = quartet.get1();
        final @Nonnull JavaFileGenerator fileGenerator = quartet.get2();
        final @Nonnull String result = quartet.get3();
        
        if (t.getComponentType().getKind().isPrimitive()) {
            fileGenerator.addStatement(result + " = " + result + " && " + fileGenerator.importIfPossible(Arrays.class) + ".equals(" + leftReference + ", " + rightReference + ")");
        } else {
            fileGenerator.addStatement(result + " = " + result + " && " + fileGenerator.importIfPossible(Arrays.class) + ".deepEquals(" + leftReference + ", " + rightReference + ")");
        }
        return null;
    }
    
}
