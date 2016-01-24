package net.digitalid.utility.cryptography;

import net.digitalid.utility.validation.state.Stateless;

/**
 * This class specifies the cryptographic parameters.
 */
@Stateless
public final class Parameters {
    
    // TODO: Make the parameters configurable so that we can choose smaller bit-lengths for unit testing.
    
    /**
     * The bit-length of the prime factors.
     */
    public static final int FACTOR = 128; // TODO: 1024;
    
    /**
     * The bit-length of the hash function and certain random values.
     */
    public static final int HASH = 256;
    
    /**
     * The bit-length of the random commitment exponent.
     */
    public static final int RANDOM_EXPONENT = 512;
    
    /**
     * The bit-length of the credential exponent.
     */
    public static final int CREDENTIAL_EXPONENT = 512;
    
    /**
     * The bit-length of the credential exponent.
     */
    public static final int RANDOM_CREDENTIAL_EXPONENT = 768;
    
    /**
     * The bit-length of the blinding exponent.
     */
    public static final int BLINDING_EXPONENT = 768;
    
    /**
     * The bit-length of the random blinding exponent.
     */
    public static final int RANDOM_BLINDING_EXPONENT = 1_024;
    
    /**
     * The bit-length of the verifiable encryption modulus divided by 2.
     */
    public static final int VERIFIABLE_ENCRYPTION = 128; // TODO: 1024;
    
    /**
     * The bit-length of the symmetric encryption key.
     */
    public static final int ENCRYPTION_KEY = 192;
    
}
