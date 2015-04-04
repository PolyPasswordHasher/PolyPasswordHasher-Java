/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

  private static final String proprtyFile = "pph.properties";

  public static void main(String[] args) throws NoSuchAlgorithmException,
          Exception {

    PolyPasswordHasher pph;
    try {
      pph = new PolyPasswordHasher(proprtyFile);

      // create admin accounts  
      pph.createAccount("admin", "correct horse", pph.getThreshold() / 2);
      
      pph.createAccount("root", "battery staple", pph.getThreshold() / 2);

      // creatre user accounts
      pph.createAccount("dennis", "menace", 0);
      
      pph.createAccount("eve", "iamevil", 0);

      System.out.println("alic kitten " + pph.isValidLogin("alice", "kitten"));
      
      System.out.println("alic bob " + pph.isValidLogin("alice", "bob"));

      System.out.println("admin correct horse " + pph.isValidLogin("admin",
              "correct horse"));
      
      System.out.println("admin admin " + pph.isValidLogin("admin", "admin"));

      System.out.println("denis password " + pph.isValidLogin("dennis",
              "password"));
      
      System.out.println("denis menace " + pph.isValidLogin("dennis", "menace"));

      System.out.println("eve password " + pph.isValidLogin("eve", "password"));

      System.out.println("eve iamevil " + pph.isValidLogin("eve", "iamevil"));

    } catch (UnsupportedEncodingException | InvalidKeyException |
            IllegalBlockSizeException | InvalidAlgorithmParameterException |
            NoSuchPaddingException | BadPaddingException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

}
