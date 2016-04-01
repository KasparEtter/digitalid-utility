package net.digitalid.utility.configuration;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.digitalid.utility.annotations.method.Impure;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.annotations.ownership.Capturable;
import net.digitalid.utility.annotations.ownership.Captured;
import net.digitalid.utility.annotations.ownership.NonCapturable;
import net.digitalid.utility.annotations.state.Modifiable;
import net.digitalid.utility.annotations.type.Mutable;
import net.digitalid.utility.configuration.exceptions.CyclicDependenciesException;
import net.digitalid.utility.configuration.exceptions.InitializedConfigurationException;
import net.digitalid.utility.configuration.exceptions.MaskingInitializationException;
import net.digitalid.utility.configuration.exceptions.UninitializedConfigurationException;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.functional.iterables.FiniteIterable;
import net.digitalid.utility.validation.annotations.method.Chainable;
import net.digitalid.utility.validation.annotations.type.Functional;

/**
 * The configuration of a service is given by a provider.
 * A provider can only be replaced but no longer removed.
 * 
 * @param <P> the type of the provider for some service.
 */
@Mutable
public class Configuration<P> {
    
    /* -------------------------------------------------- Interface -------------------------------------------------- */
    
    /**
     * Objects that implement this interface can be used to observe a {@link Configuration configuration}.
     */
    @Functional
    public static interface Observer<P> {
        
        /**
         * This method is called on {@link Configuration#register(net.digitalid.utility.configuration.Configuration.Observer) registered} observers when the provider of the given configuration has been replaced.
         * 
         * @require !newProvider.equals(oldProvider) : "The new provider may not the same as the old provider.";
         */
        public void replaced(@Nonnull Configuration<P> configuration, @Nullable P oldProvider, @Nonnull P newProvider);
        
    }
    
    /* -------------------------------------------------- Observers -------------------------------------------------- */
    
    /**
     * Stores the set of registered observers of this configuration.
     */
    private final @Nonnull Set<@Nonnull Observer<P>> observers = new LinkedHashSet<>();
    
    /**
     * Registers the given observer for this configuration.
     * 
     * @return whether the given observer was not already registered.
     */
    @Impure
    public boolean register(@Nonnull Observer<P> observer) {
        Require.that(observer != null).orThrow("The observer may not be null.");
        
        return observers.add(observer);
    }
    
    /**
     * Deregisters the given observer for this configuration.
     * 
     * @return whether the given observer was actually registered.
     */
    @Impure
    public boolean deregister(@Nonnull Observer<P> observer) {
        Require.that(observer != null).orThrow("The observer may not be null.");
        
        return observers.remove(observer);
    }
    
    /**
     * Returns whether the given observer is registered for this configuration.
     */
    @Pure
    public boolean isRegistered(@Nonnull Observer<P> observer) {
        Require.that(observer != null).orThrow("The observer may not be null.");
        
        return observers.contains(observer);
    }
    
    /* -------------------------------------------------- Provider -------------------------------------------------- */
    
    /**
     * Stores the provider of this configuration.
     * The provider is null until it is once set.
     */
    private @Nullable P provider;
    
    /**
     * Returns the provider of this configuration.
     * 
     * @throws UninitializedConfigurationException if no provider was set for this configuration.
     */
    @Pure
    public @Nonnull P get() {
        if (provider == null) { throw UninitializedConfigurationException.with(this); }
        return provider;
    }
    
    /**
     * Sets the provider of this configuration and notifies all observers.
     */
    @Impure
    public void set(@Captured @Nonnull P provider) {
        Require.that(provider != null).orThrow("The provider may not be null.");
        
        if (!provider.equals(this.provider)) {
            for (@Nonnull Observer<P> observer : observers) {
                observer.replaced(this, this.provider, provider);
            }
            this.provider = provider;
        }
    }
    
    /**
     * Returns whether the provider of this configuration is set.
     */
    @Pure
    public boolean isSet() {
        return provider != null;
    }
    
    /* -------------------------------------------------- Declaration -------------------------------------------------- */
    
    private final @Nonnull String qualifiedClassName;
    
    /**
     * Returns the qualified name of the class where this configuration is declared.
     */
    @Pure
    public @Nonnull String getQualifiedClassName() {
        return qualifiedClassName;
    }
    
    private final String className;
    
    /**
     * Returns the simple name of the class where this configuration is declared.
     */
    @Pure
    public @Nonnull String getClassName() {
        return className;
    }
    
    private final int lineNumber;
    
    /**
     * Returns the line number on which this configuration is declared.
     */
    @Pure
    public int getLineNumber() {
        return lineNumber;
    }
    
    @Pure
    @Override
    public @Nonnull String toString() {
        return getClassName();
    }
    
    /* -------------------------------------------------- Configurations -------------------------------------------------- */
    
    /**
     * Stores the set of all configurations that were created.
     */
    private static final @Nonnull Set<@Nonnull Configuration<?>> configurations = new LinkedHashSet<>();
    
    /**
     * Returns a finite iterable of all configurations that were created.
     */
    @Pure
    public static @Nonnull FiniteIterable<@Nonnull Configuration<?>> getAllConfigurations() {
        return FiniteIterable.of(configurations);
    }
    
    /**
     * Initializes all configurations of this library.
     * 
     * @throws InitializationException if a problem occurs.
     */
    @Impure
    public static void initializeAllConfigurations() {
        for (@Nonnull Initializer initializer : ServiceLoader.load(Initializer.class)) {
            initializer.toString(); // Just to remove the unused variable warning.
        }
        try {
            for (@Nonnull Configuration<?> configuration : configurations) {
                configuration.initialize();
            }
        } catch (@Nonnull Exception exception) {
            throw MaskingInitializationException.with(exception);
        }
    }
    
    /* -------------------------------------------------- Constructors -------------------------------------------------- */
    
    /**
     * Creates a configuration with the given nullable provider.
     */
    protected Configuration(@Nonnull P provider) {
        this.provider = provider;
        
        final @Nonnull StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        this.qualifiedClassName = element.getClassName();
        this.className = qualifiedClassName.substring(qualifiedClassName.lastIndexOf('.') + 1);
        this.lineNumber = element.getLineNumber();
        
        configurations.add(this);
    }
    
    /**
     * Returns a configuration with the given provider.
     */
    @Impure
    public static <P> @NonCapturable @Nonnull Configuration<P> with(@Nonnull P provider) {
        Require.that(provider != null).orThrow("The provider may not be null.");
        
        return new Configuration<>(provider);
    }
    
    /**
     * Returns a configuration whose provider still needs to be set.
     */
    @Impure
    public static <P> @NonCapturable @Nonnull Configuration<P> withUnknownProvider() {
        return new Configuration<>(null);
    }
    
    /* -------------------------------------------------- Initializers -------------------------------------------------- */
    
    /**
     * Stores the set of initializers which need to be executed by this configuration.
     */
    private final @Nonnull Set<@Nonnull Initializer> initializers = new LinkedHashSet<>();
    
    /**
     * Adds the given initializer to the set of initializers for this configuration.
     * 
     * @throws InitializedConfigurationException if this configuration has already been initialized.
     */
    @Impure
    protected void addInitializer(@Nonnull Initializer initializer) {
        Require.that(initializer != null).orThrow("The initializer may not be null.");
        
        if (isInitialized) { throw InitializedConfigurationException.with(this); }
        initializers.add(initializer);
    }
    
    /* -------------------------------------------------- Dependencies -------------------------------------------------- */
    
    /**
     * Stores the set of configurations which need to be initialized before this configuration.
     */
    private final @Nonnull Set<@Nonnull Configuration<?>> dependencies = new LinkedHashSet<>();
    
    /**
     * Returns whether this configuration depends on the given configuration (directly or indirectly).
     */
    @Pure
    public boolean dependsOn(@Nonnull Configuration<?> configuration) {
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        
        if (configuration.equals(this)) { return true; }
        for (@Nonnull Configuration<?> dependency : dependencies) {
            if (dependency.dependsOn(configuration)) { return true; }
        }
        return false;
    }
    
    /**
     * Returns a list of configurations through which this configuration depends on the given configuration.
     * The returned list is empty if (and only if) this configuration does not depend on the given configuration.
     * Otherwise, the first element in the returned list is this configuration and the last the given configuration.
     */
    @Pure
    public @Capturable @Modifiable @Nonnull List<@Nonnull Configuration<?>> getDependencyChain(@Nonnull Configuration<?> configuration) {
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        
        if (configuration.equals(this)) {
            final @Nonnull LinkedList<@Nonnull Configuration<?>> result = new LinkedList<>();
            result.add(this);
            return result;
        }
        
        for (@Nonnull Configuration<?> dependency : dependencies) {
            final @Nonnull List<@Nonnull Configuration<?>> dependencyChain = dependency.getDependencyChain(configuration);
            if (!dependencyChain.isEmpty()) {
                dependencyChain.add(0, this);
                return dependencyChain;
            }
        }
        
        return new LinkedList<>();
    }
    
    /**
     * Returns the {@link #getDependencyChain(net.digitalid.utility.configuration.Configuration) dependency chain} as a string.
     * 
     * @require dependsOn(configuration) : "This configuration has to depend on the given configuration.";
     */
    @Pure
    public @Nonnull String getDependencyChainAsString(@Nonnull Configuration<?> configuration) {
        Require.that(configuration != null).orThrow("The configuration may not be null.");
        Require.that(dependsOn(configuration)).orThrow("This configuration $ has to depend on the given configuration $.", this, configuration);
        
        return FiniteIterable.of(getDependencyChain(configuration)).join(" -> ");
    }
    
    /**
     * Adds the given dependency to the set of dependencies and returns this configuration.
     * 
     * @throws InitializedConfigurationException if this configuration has already been initialized.
     * @throws CyclicDependenciesException if the given dependency depends on this configuration.
     */
    @Impure
    @Chainable
    public @NonCapturable @Nonnull Configuration<P> addDependency(@Nonnull Configuration<?> dependency) {
        Require.that(dependency != null).orThrow("The dependency may not be null.");
        
        if (isInitialized) { throw InitializedConfigurationException.with(this); }
        if (dependency.dependsOn(this)) { throw CyclicDependenciesException.with(this, dependency); }
        dependencies.add(dependency);
        return this;
    }
    
    /* -------------------------------------------------- Initialization -------------------------------------------------- */
    
    /**
     * Stores whether this configuration has been initialized.
     */
    private boolean isInitialized = false;
    
    /**
     * Initializes all dependencies and executes all initializers of this configuration.
     * 
     * @throws Exception if any problems occur.
     */
    @Impure
    public void initialize() throws Exception {
        if (!isInitialized) {
            for (@Nonnull Configuration<?> dependency : dependencies) { dependency.initialize(); }
            for (@Nonnull Initializer initializer : initializers) { initializer.execute(); }
            this.isInitialized = true;
        }
    }
    
}
