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
package net.digitalid.utility.processing.utility;

import javax.annotation.Nonnull;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.validation.annotations.type.Mutable;

/**
 * This interface allows a code generator to import the referenced types.
 */
@Mutable
public interface TypeImporter {
    
    /**
     * Imports the given qualified type name if its simple name is not yet mapped to a different type.
     * 
     * @return the simple name of the given qualified type name if it could be imported without a naming conflict and the given qualified type name otherwise.
     * 
     * @require qualifiedName.contains(".") : "The name has to be qualified.";
     */
    @Impure
    public @Nonnull String importIfPossible(@Nonnull String qualifiedTypeName);
    
    /**
     * Imports the given type if its simple name is not yet mapped to a different type.
     * 
     * @return the simple name of the given type if it could be imported without a naming conflict and the qualified name of the given type otherwise.
     */
    @Impure
    public @Nonnull String importIfPossible(@Nonnull Class<?> type);
    
    /**
     * Imports the given element, which has to be qualified nameable, if its simple name is not yet mapped to a different type.
     * 
     * @return the simple name of the given element if it could be imported without a naming conflict and the qualified name of the given element otherwise.
     * 
     * @require typeElement.getKind().isClass() || typeElement.getKind().isInterface() : "The element has to be a type.";
     */
    @Impure
    public @Nonnull String importIfPossible(@Nonnull Element typeElement);
    
    /**
     * Imports the given type mirror with its generic parameters if their simple names are not yet mapped to different types.
     * 
     * @return the simple name of the given type mirror if it could be imported without a naming conflict and the qualified name of the given type mirror otherwise.
     */
    @Impure
    public @Nonnull String importIfPossible(@Nonnull TypeMirror typeMirror);
    
    /**
     * Imports the given qualified member name if its simple name is not yet mapped to a different member.
     * 
     * @return the simple name of the given member if the qualified member name could be imported without a naming conflict and the given qualified member name otherwise.
     */
    @Impure
    public @Nonnull String importStaticallyIfPossible(@Nonnull String qualifiedMemberName);
    
}
