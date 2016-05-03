package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class that's encrypts the password
 * @author  Clifton West
 * @version October 15, 2015
 */
public class PasswordEncryption {
    /** A String that contains the salt for encryption */
    private static String salt = getSalt();

    /**
     * Does the encryption of the password.
     * @param password String to be encrypted.
     * @return A string of the encrypted Password.
     */
    private static String encryptedPassword(String password) {
        String encryptedPw = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPw = sb.toString();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return encryptedPw;
    }

    /**
     * Gets the Encryption of the Password.
     * @param   password  String wanted to be encrypted.
     * @return  The encrypted String.
     */
    public static String getEncryptedPw(String password) {
        return encryptedPassword(password);
    }

    /**
     * Generates a 16 byte salt.
     * @return String that contains the salt.
     */
    private static String getSalt() {
        byte[] salt = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[16];
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return salt.toString();
    }

}
