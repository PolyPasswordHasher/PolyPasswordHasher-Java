/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph;

import edu.nyu.poly.pph.model.PPHAccount;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author gholami
 */
public class Main {

    private static Properties props;
    private PPHAccount pphAccount;

    public static void main(String[] args) throws NoSuchAlgorithmException, Exception {
        //int treashold = Integer.parseInt(props.getProperty("THRESHOLD"));
        int treashold=2; // any two of n admin can unlock
        //String secret = props.getProperty("MASTER");
        String secret= "0cac5a40-3690-4167-9993-8b6bf58e";
        
        //String secret = SecurityUtil.getRandomString(128);
        PolyPasswordHasher pph;
        try {
            pph = new PolyPasswordHasher(treashold, secret);

            // Create admin users to get 5 shares
            pph.createAccount("admin", "correct horse", treashold / 2);
            pph.createAccount("root", "battery staple", treashold / 2);
            
            /*
            // Create user accounts protector
            pph.createAccount("alice", "kitten", 1);
            pph.createAccount("bob", "puppy", 1);
            pph.createAccount("charlie", "velociraptor", 1);
            */
            pph.createAccount("dennis", "menace", 0);
            pph.createAccount("eve", "iamevil", 0);
          
            System.out.println("alic kitten "+ pph.isValidLogin("alice", "kitten"));           
            System.out.println(pph.isValidLogin("alice", "bob"));
            
            System.out.println(pph.isValidLogin("admin", "correct horse"));
            System.out.println(pph.isValidLogin("admin", "admin"));
            
            System.out.println(pph.isValidLogin("dennis", "password"));
            System.out.println("eve correct " + pph.isValidLogin("dennis", "menace"));
     
            
            System.out.println(pph.isValidLogin("eve", "password"));
            System.out.println(pph.isValidLogin("eve", "iamevil"));
     
            
        } catch (UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadUserProperties() throws IOException {
        props = new Properties();

        InputStream inputStream
                = getClass().getClassLoader().getResourceAsStream("pph.properties");
        
        props.load(inputStream);
    }
}
