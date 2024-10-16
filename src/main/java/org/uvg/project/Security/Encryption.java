package org.uvg.project.Security;

import org.uvg.project.Exceptions.SecurityException;
import org.uvg.project.utils.ILoadEnv;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;


public class Encryption implements ILoadEnv {

    public Encryption(){}

    private static SecretKeySpec createKey() throws SecurityException {
        try {
            byte[] bytesArray = encryptionKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytesArray = md.digest(bytesArray);
            bytesArray = Arrays.copyOf(bytesArray, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bytesArray, "AES");
            return secretKeySpec;
        } catch (Exception e) {
            throw new SecurityException("Error al crear la clave de encriptación: " + e.getMessage());
        }
    }

    public static String encrypt(String text) throws SecurityException {

        try {
            SecretKeySpec secretKeySpec = createKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] bytesArray = text.getBytes(StandardCharsets.UTF_8);
            byte[] encrypt = cipher.doFinal(bytesArray);

            String encryptedArray = Base64.getEncoder().encodeToString(encrypt);
            return encryptedArray;

        } catch (Exception e) {
            throw new SecurityException("Error al encriptar: " + e.getMessage());
        }

    }

    public static String decrypt(String text) throws SecurityException {

        try {
            SecretKeySpec secretKeySpec = createKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] bytesArray = Base64.getDecoder().decode(text);
            byte[] decryption = cipher.doFinal(bytesArray);

            String decryptArray = new String(decryption, StandardCharsets.UTF_8);
            return decryptArray;

        } catch (Exception e) {
            throw new SecurityException("Error al desencriptar: " + e.getMessage());
        }

    }

}
