package net.digitalid.utility.math;

import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.digitalid.utility.validation.state.Immutable;
import net.digitalid.utility.validation.state.Matching;
import net.digitalid.utility.validation.state.Pure;

/**
 * An element is a number in a certain group.
 * 
 * @invariant getValue().compareTo(BigInteger.ZERO) >= 0 && getValue().compareTo(getGroup().getModulus()) == -1 : "The value is non-negative and smaller than the group modulus.";
 */
@Immutable
public final class Element extends Number<Element, Group<?>> {
    
    /* -------------------------------------------------- Group -------------------------------------------------- */
    
    /**
     * Stores the group of this element.
     */
    private final @Nonnull Group<?> group;
    
    /**
     * Returns the group of this element.
     * 
     * @return the group of this element.
     */
    @Pure
    public @Nonnull Group<?> getGroup() {
        return group;
    }
    
    /**
     * Returns whether this element is in the given group.
     * 
     * @param group the group of interest.
     * 
     * @return whether this element is in the given group.
     */
    @Pure
    public boolean isElement(@Nonnull Group<?> group) {
        return getGroup().equals(group);
    }
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new element in the given group with the given value.
     * 
     * @param group the group of the new element.
     * @param value the value of the new element.
     */
    private Element(@Nonnull Group<?> group, @Nonnull BigInteger value) {
        super(value.mod(group.getModulus()));
        this.group = group;
        
        assert getValue().compareTo(BigInteger.ZERO) >= 0 && getValue().compareTo(getGroup().getModulus()) == -1 : "The value is non-negative and smaller than the group modulus.";
    }
    
    /**
     * Creates a new element in the given group with the given value.
     * 
     * @param group the group of the new element.
     * @param value the value of the new element.
     * 
     * @return a new element in the given group with the given value.
     */
    @Pure
    public static @Nonnull Element get(@Nonnull Group<?> group, @Nonnull BigInteger value) {
        return new Element(group, value);
    }
    
    /* -------------------------------------------------- Operations -------------------------------------------------- */
    
    /**
     * Adds the given element to this element.
     * 
     * @param element the element to be added.
     * 
     * @return the sum of this and the given element.
     */
    @Pure
    public @Nonnull @Matching Element add(@Nonnull @Matching Element element) {
        assert getGroup().equals(element.getGroup()) : "Both elements are in the same group.";
        
        return new Element(getGroup(), getValue().add(element.getValue()));
    }
    
    /**
     * Subtracts the given element from this element.
     * 
     * @param element the element to be subtracted.
     * 
     * @return the difference between this and the given element.
     */
    @Pure
    public @Nonnull @Matching Element subtract(@Nonnull @Matching Element element) {
        assert getGroup().equals(element.getGroup()) : "Both elements are in the same group.";
        
        return new Element(getGroup(), getValue().subtract(element.getValue()));
    }
    
    /**
     * Multiplies this element with the given element.
     * 
     * @param element the element to be multiplied.
     * 
     * @return the product of this and the given element.
     */
    @Pure
    public @Nonnull @Matching Element multiply(@Nonnull @Matching Element element) {
        assert getGroup().equals(element.getGroup()) : "Both elements are in the same group.";
        
        return new Element(getGroup(), getValue().multiply(element.getValue()));
    }
    
    /**
     * Inverses this element.
     * 
     * @return the multiplicative inverse of this element.
     * 
     * @require isRelativelyPrime() : "The element is relatively prime to the group modulus.";
     */
    @Pure
    public @Nonnull @Matching Element inverse() {
        assert isRelativelyPrime() : "The element is relatively prime to the group modulus.";
        
        return new Element(getGroup(), getValue().modInverse(getGroup().getModulus()));
    }
    
    /**
     * Raises this element by the given exponent.
     * 
     * @param exponent the exponent to be raised by.
     * 
     * @return this element raised by the given exponent.
     */
    @Pure
    public @Nonnull @Matching Element pow(@Nonnull Exponent exponent) {
        return pow(exponent.getValue());
    }
    
    /**
     * Raises this element by the given exponent.
     * 
     * @param exponent the exponent to be raised by.
     * 
     * @return this element raised by the given exponent.
     */
    @Pure
    public @Nonnull @Matching Element pow(@Nonnull BigInteger exponent) {
        return new Element(getGroup(), getValue().modPow(exponent, getGroup().getModulus()));
    }
    
    /* -------------------------------------------------- Conditions -------------------------------------------------- */
    
    /**
     * Returns whether the element is relatively prime to the group modulus.
     * 
     * @return whether the element is relatively prime to the group modulus.
     */
    @Pure
    public boolean isRelativelyPrime() {
        return getValue().gcd(getGroup().getModulus()).compareTo(BigInteger.ONE) == 0;
    }
    
    /**
     * Returns whether the element is equal to the neutral element.
     * 
     * @return whether the element is equal to the neutral element.
     */
    @Pure
    public boolean isOne() {
        return getValue().equals(BigInteger.ONE);
    }
    
}
