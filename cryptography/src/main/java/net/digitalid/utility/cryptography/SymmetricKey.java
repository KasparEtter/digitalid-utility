package net.digitalid.utility.cryptography;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.SecureRandom;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import net.digitalid.utility.collections.annotations.size.NonEmpty;
import net.digitalid.utility.initialization.errors.InitializationError;
import net.digitalid.utility.errors.ShouldNeverHappenError;
import net.digitalid.utility.exceptions.external.InvalidEncodingException;
import net.digitalid.utility.exceptions.external.MaskingInvalidEncodingException;
import net.digitalid.utility.exceptions.internal.InternalException;
import net.digitalid.utility.validation.math.NonNegative;
import net.digitalid.utility.validation.math.Positive;
import net.digitalid.utility.validation.reference.Capturable;
import net.digitalid.utility.validation.state.Immutable;
import net.digitalid.utility.validation.state.Pure;

/**
 * Symmetric keys are used to encrypt and decrypt byte arrays with the Advanced Encryption Standard (AES).
 */
@Immutable
public final class SymmetricKey {
    
    /* -------------------------------------------------- Circumvent Cryptographic Restrictions -------------------------------------------------- */
    
    static {
        try {
            final int length = Cipher.getMaxAllowedKeyLength("AES");
            if (length < Parameters.ENCRYPTION_KEY) {
                if (System.getProperty("java.runtime.name").equals("Java(TM) SE Runtime Environment")) {
                    /*
                     * Do the following, but with reflection to bypass access checks (taken from http://stackoverflow.com/a/22492582):
                     *
                     * JceSecurity.isRestricted = false;
                     * JceSecurity.defaultPolicy.perms.clear();
                     * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
                     */
                    try {
                        final @Nonnull Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
                        final @Nonnull Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
                        final @Nonnull Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");
                        
                        final @Nonnull Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
                        isRestrictedField.setAccessible(true);
                        isRestrictedField.set(null, false);
                        
                        final @Nonnull Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
                        defaultPolicyField.setAccessible(true);
                        final @Nonnull PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);
                        
                        final @Nonnull Field permsField = cryptoPermissions.getDeclaredField("perms");
                        permsField.setAccessible(true);
                        ((Map<?, ?>) permsField.get(defaultPolicy)).clear();
                        
                        final @Nonnull Field instanceField = cryptoAllPermission.getDeclaredField("INSTANCE");
                        instanceField.setAccessible(true);
                        defaultPolicy.add((Permission) instanceField.get(null));
                    } catch (@Nonnull ClassNotFoundException | NoSuchFieldException | IllegalArgumentException | SecurityException | IllegalAccessException exception) {
                        throw InitializationError.get("Your system allows only a maximal key length of " + length + " bits for symmetric encryption but a length of " + Parameters.ENCRYPTION_KEY + " bits is required for security reasons."
                                + "Please install the Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files from http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html for Java 7."
                                + "(All you have to do is to download the files and replace with them 'local_policy.jar' and 'US_export_policy.jar' in '" + System.getProperty("java.home") + File.separator + "lib" + File.separator + "security" + File.separator + "'.)", exception);
                    }
                }
            }
        } catch (@Nonnull NoSuchAlgorithmException exception) {
            throw InitializationError.get("Your system does not support the Advanced Encryption Standard (AES). Unfortunately, you are not able to use Digital ID for now.", exception);
        }
    }
    
    /* -------------------------------------------------- Parameters -------------------------------------------------- */
    
    /**
     * Stores the length of symmetric keys in bytes.
     */
    private static final int LENGTH = Parameters.ENCRYPTION_KEY / 8;
    
    /**
     * Stores the mode of the encryption cipher.
     */
    private static final @Nonnull String mode = "AES/CBC/PKCS5Padding";
    
    /* -------------------------------------------------- Value -------------------------------------------------- */
    
    /**
     * Stores the value of this symmetric key.
     */
    private final @Nonnull BigInteger value;
    
    /**
     * Returns the value of this symmetric key.
     * 
     * @return the value of this symmetric key.
     */
    @Pure
    public final @Nonnull BigInteger getValue() {
        return value;
    }
    
    /* -------------------------------------------------- Key -------------------------------------------------- */
    
    /**
     * Stores the key of this symmetric key.
     */
    private final @Nonnull Key key;
    
    /* -------------------------------------------------- Constructor -------------------------------------------------- */
    
    /**
     * Creates a new symmetric key with the given value.
     * 
     * @param value the value of the new symmetric key.
     */
    private SymmetricKey(@Nonnull BigInteger value) {
        this.value = value;
        final @Nonnull byte[] bytes = value.toByteArray();
        final @Nonnull byte[] key = new byte[LENGTH];
        System.arraycopy(bytes, Math.max(bytes.length - LENGTH, 0), key, Math.max(LENGTH - bytes.length, 0), Math.min(LENGTH, bytes.length));
        this.key = new SecretKeySpec(key, "AES");
    }
    
    /**
     * Creates a new symmetric key with the given value.
     * 
     * @param value the value of the new symmetric key.
     * 
     * @return a new symmetric key with the given value.
     */
    @Pure
    public static @Nonnull SymmetricKey get(@Nonnull BigInteger value) {
        return new SymmetricKey(value);
    }
    
    /**
     * Creates a new symmetric key with a random value.
     * 
     * @return a new symmetric key with a random value.
     */
    @Pure
    public static @Nonnull SymmetricKey getRandom() {
        return new SymmetricKey(new BigInteger(Parameters.ENCRYPTION_KEY, new SecureRandom()));
    }
    
    /* -------------------------------------------------- Encryption and Decryption -------------------------------------------------- */
    
    /**
     * Encrypts the indicated section in the given byte array with this symmetric key.
     * 
     * @param initializationVector the initialization vector for the encryption.
     * @param bytes the byte array of which a section is to be encrypted.
     * @param offset the offset of the section in the given byte array.
     * @param length the length of the section in the given byte array.
     * 
     * @return the encryption of the indicated section in the given byte array.
     * 
     * @require offset + length <= bytes.length : "The indicated section may not exceed the given byte array.";
     */
    @Pure
    public final @Capturable @Nonnull @NonEmpty byte[] encrypt(@Nonnull InitializationVector initializationVector, @Nonnull byte[] bytes, @NonNegative int offset, @Positive int length) {
        assert offset >= 0 : "The offset is not negative.";
        assert length > 0 : "The length is positive.";
        assert offset + length <= bytes.length : "The indicated section may not exceed the given byte array.";
        
        try {
            final @Nonnull Cipher cipher = Cipher.getInstance(mode);
            cipher.init(Cipher.ENCRYPT_MODE, key, initializationVector);
            return cipher.doFinal(bytes, offset, length);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException exception) {
            throw ShouldNeverHappenError.get("Could not encrypt the given bytes.", exception);
        }
    }
    
    /**
     * Decrypts the indicated section in the given byte array with this symmetric key.
     * 
     * @param initializationVector the initialization vector for the decryption.
     * @param bytes the byte array of which a section is to be decrypted.
     * @param offset the offset of the section in the given byte array.
     * @param length the length of the section in the given byte array.
     * 
     * @return the decryption of the indicated section in the given byte array.
     * 
     * @require offset + length <= bytes.length : "The indicated section may not exceed the given byte array.";
     */
    @Pure
    public final @Capturable @Nonnull @NonEmpty byte[] decrypt(@Nonnull InitializationVector initializationVector, @Nonnull byte[] bytes, @NonNegative int offset, @Positive int length) throws InvalidEncodingException, InternalException {
        assert offset >= 0 : "The offset is not negative.";
        assert length > 0 : "The length is positive.";
        assert offset + length <= bytes.length : "The indicated section may not exceed the given byte array.";
        
        try {
            final @Nonnull Cipher cipher = Cipher.getInstance(mode);
            cipher.init(Cipher.DECRYPT_MODE, key, initializationVector);
            return cipher.doFinal(bytes, offset, length);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException exception) {
            throw MaskingInvalidEncodingException.get(exception);
        }
    }
    
    /* -------------------------------------------------- Object -------------------------------------------------- */
    
    @Pure
    @Override
    public final boolean equals(@Nullable Object object) {
        if (object == this) { return true; }
        if (object == null || !(object instanceof SymmetricKey)) { return false; }
        @Nonnull SymmetricKey other = (SymmetricKey) object;
        return this.value.equals(other.value);
    }
    
    @Pure
    @Override
    public final int hashCode() {
        return this.value.hashCode();
    }
    
}
