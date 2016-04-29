package net.digitalid.utility.cryptography.key;

import javax.annotation.Nonnull;

import net.digitalid.utility.annotations.method.CallSuper;
import net.digitalid.utility.annotations.method.Pure;
import net.digitalid.utility.collaboration.annotations.TODO;
import net.digitalid.utility.collaboration.enumerations.Author;
import net.digitalid.utility.collaboration.enumerations.Priority;
import net.digitalid.utility.contracts.Require;
import net.digitalid.utility.cryptography.HashGenerator;
import net.digitalid.utility.generator.annotations.GenerateBuilder;
import net.digitalid.utility.generator.annotations.GenerateSubclass;
import net.digitalid.utility.group.annotations.InGroup;
import net.digitalid.utility.math.Element;
import net.digitalid.utility.math.Exponent;
import net.digitalid.utility.math.GroupWithUnknownOrder;
import net.digitalid.utility.tuples.Pair;
import net.digitalid.utility.validation.annotations.type.Immutable;

/**
 * This class stores the groups, elements and exponents of a host's public key.
 * 
 * @see PrivateKey
 * @see KeyPair
 */
@Immutable
@GenerateBuilder
@GenerateSubclass
public abstract class PublicKey extends AsymmetricKey {
    
    /* -------------------------------------------------- Composite Group -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull GroupWithUnknownOrder getCompositeGroup();
    
    /**
     * Returns the encryption and verification exponent.
     */
    @Pure
    public abstract @Nonnull Exponent getE();
    
    /**
     * Returns the base for blinding.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAb();
    
    /**
     * Returns the base of the client's secret.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAu();
    
    /**
     * Returns the base of the serial number.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAi();
    
    /**
     * Returns the base of the hashed identifier.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAv();
    
    /**
     * Returns the base of the exposed arguments.
     */
    @Pure
    public abstract @Nonnull @InGroup("compositeGroup") Element getAo();
    
    /* -------------------------------------------------- Subgroup Proof -------------------------------------------------- */
    
    /**
     * Returns the hash of the temporary commitments in the subgroup proof.
     */
    @Pure
    public abstract @Nonnull Exponent getT();
    
    /**
     * Returns the solution for the proof that au is in the subgroup of ab.
     */
    @Pure
    public abstract @Nonnull Exponent getSu();
    
    /**
     * Returns the solution for the proof that ai is in the subgroup of ab.
     */
    @Pure
    public abstract @Nonnull Exponent getSi();
    
    /**
     * Returns the solution for the proof that av is in the subgroup of ab.
     */
    @Pure
    public abstract @Nonnull Exponent getSv();
    
    /**
     * Returns the solution for the proof that ao is in the subgroup of ab.
     */
    @Pure
    public abstract @Nonnull Exponent getSo();
    
    /**
     * Returns whether the proof that au, ai, av and ao are in the subgroup of ab is correct.
     */
    @Pure
    public boolean verifySubgroupProof() {
        final @Nonnull Element tu = getAb().pow(getSu()).multiply(getAu().pow(getT()));
        final @Nonnull Element ti = getAb().pow(getSi()).multiply(getAi().pow(getT()));
        final @Nonnull Element tv = getAb().pow(getSv()).multiply(getAv().pow(getT()));
        final @Nonnull Element to = getAb().pow(getSo()).multiply(getAo().pow(getT()));
        
        return getT().getValue().equals(HashGenerator.generateHash(tu, ti, tv, to));
    }
    
    /* -------------------------------------------------- Square Group -------------------------------------------------- */
    
    @Pure
    @Override
    public abstract @Nonnull GroupWithUnknownOrder getSquareGroup();
    
    /**
     * Returns the generator of the square group.
     */
    @Pure
    public abstract @Nonnull @InGroup("squareGroup") Element getG();
    
    /**
     * Returns the encryption element of the square group.
     */
    @Pure
    public abstract @Nonnull @InGroup("squareGroup") Element getY();
    
    /**
     * Returns the encryption base of the square group.
     */
    @Pure
    public abstract @Nonnull @InGroup("squareGroup") Element getZPlus1();
    
    /* -------------------------------------------------- Verifiable Encryption -------------------------------------------------- */
    
    /**
     * Returns the verifiable encryption of the given value m with the random value r.
     */
    @Pure
    @TODO(task = "Move this method to where it is used.", date = "2016-04-19", author = Author.KASPAR_ETTER, priority = Priority.LOW)
    public @Nonnull Pair<@Nonnull Element, @Nonnull Element> getVerifiableEncryption(@Nonnull Exponent m, @Nonnull Exponent r) {
        return Pair.of(getY().pow(r).multiply(getZPlus1().pow(m)), getG().pow(r));
    }
    
    /* -------------------------------------------------- Validate -------------------------------------------------- */
    
    @Pure
    @Override
    @CallSuper
    public void validate() {
        super.validate();
        Require.that(verifySubgroupProof()).orThrow("The elements au, ai, av and ao have to be in the subgroup of ab.");
    }
}
