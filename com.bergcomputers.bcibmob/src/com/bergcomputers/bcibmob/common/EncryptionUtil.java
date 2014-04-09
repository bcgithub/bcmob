package com.bergcomputers.bcibmob.common;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {
	private static final String PASSWORD = "rex+W!zupaxus&*zuq@7RAWu";

	private EncryptionUtil() {
	}

    public static String encrypt(String message) {

        try {
            final SecretKey key = new SecretKeySpec(PASSWORD.getBytes(), "DESede");
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS7Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key);

	        final byte[] plainTextBytes = message.getBytes();
	        final byte[] cipherText = cipher.doFinal(plainTextBytes);
	        return Base64.encodeBytes(cipherText);

        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        }
        return null;
    }

    public static String decrypt(String message) {
        try {
        	final byte[] decodedBytes = Base64.decode(message);
            final SecretKey key = new SecretKeySpec(PASSWORD.getBytes(), "DESede");
            final Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS7Padding");
            decipher.init(Cipher.DECRYPT_MODE, key);

            final byte[] plainText = decipher.doFinal(decodedBytes);

            return new String(plainText);
        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        } catch (IOException e) {
        }

        return null;
    }

    public static byte[] decrypt(byte[] bytes) {
        try {
        	final byte[] decodedBytes = Base64.decode(bytes);
            final SecretKey key = new SecretKeySpec(PASSWORD.getBytes(), "DESede");
            final Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS7Padding");
            decipher.init(Cipher.DECRYPT_MODE, key);

            return decipher.doFinal(decodedBytes);
        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        } catch (IOException e) {
        }

        return null;
    }

}
