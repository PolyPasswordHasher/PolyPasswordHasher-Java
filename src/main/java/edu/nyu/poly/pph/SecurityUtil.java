package edu.nyu.poly.pph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ali Gholami <gholami@pdc.kth.se>
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class SecurityUtil {

    private static final Random random = new SecureRandom();
    private static String hashingAlgorithm = "SHA-256";

    public static String getRandomString(int length) {
        String randomStr = UUID.randomUUID().toString();
        while (randomStr.length() < length) {
            randomStr += UUID.randomUUID().toString();
        }
        return randomStr.substring(0, length);
    }

    public static byte[] getHash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(hashingAlgorithm);
        digest.reset();
        digest.update(data);
        return digest.digest(data);
    }

    
    public static byte[] getSalt(int length) {
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] converToSHA256(byte data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(hashingAlgorithm);
        md.update(data);
        return md.digest();
    }

/*
    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    public static byte[] stringToByte(String input) {

        if (input == null || input.isEmpty()) {
            return null;
        }
        if (org.apache.commons.codec.binary.Base64.isBase64(input)) {
            return org.apache.commons.codec.binary.Base64.decodeBase64(input);

        } else {
            return org.apache.commons.codec.binary.Base64.encodeBase64(input.getBytes());
        }
    }


    public static String bytetoString(byte[] input) {
        if (input == null) {
            return null;
        }
        return org.apache.commons.codec.binary.Base64.encodeBase64String(input);
    }
*/
    public static byte[] xorByteArray(byte[] a1, byte[] a2) {

        byte[] a3 = new byte[a1.length];

        int i = 0;
        for (byte b : a1) {
            a3[i] = (byte) (b ^ a2[i++]);
        }

        return a3;
    }

    public static byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length]; 
        System.arraycopy(a, 0, result, 0, a.length); 
        System.arraycopy(b, 0, result, a.length, b.length); 
    return result;
    } 
}
