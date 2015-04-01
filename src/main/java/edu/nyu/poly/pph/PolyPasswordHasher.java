/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph;

import com.tiemens.secretshare.engine.ShamirSchem;
import edu.nyu.poly.pph.model.PPHAccount;
import edu.nyu.poly.pph.model.ShareEntry;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Ali Gholami <gholami@pdc.kth.se>
 */
public class PolyPasswordHasher {

    private PPHAccount pphAccount;

    private List<PPHAccount> users;
    // knowledge of shares to decode others.
    private int threshold;
    private final int nextavailableshare = 1;
    private final int MAX_NUMBER_OF_SHARES = 255;
    private final int saltsize = 16;

    private byte[] shieldKey;
    private ShamirSchem sc;
    private String[] pieces;
    private List<ShareEntry> sharesEntires;
    private byte[] shamirData;

    public PolyPasswordHasher(int threshold, String secret) throws UnsupportedEncodingException {
        this.threshold = threshold;
        this.shieldKey = secret.getBytes();
        this.sc = new ShamirSchem();
        // shieldkey, divide the shieldkey to n shares, number of shares to recover 
        pieces = sc.splitSecretIntoPieces(secret, MAX_NUMBER_OF_SHARES, this.threshold);
        users = new ArrayList<>();
        sharesEntires = new ArrayList<>();
    }

    public void createAccount(String username, String password, int shares) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, Exception {

        if (isAccountUnique(username)) {
            return;
        }

        if (shares < 0 || shares > 255) {
            return;
        }

        if (shares + nextavailableshare > 255) {
            return;
        }

        PPHAccount ppa = new PPHAccount(username,password);

        if (shares != 0) {
            ShareEntry se = new ShareEntry();
            for (int i = nextavailableshare; i < nextavailableshare + shares; i++) {
                se.setShareNum(i);
                shamirData = SecurityUtil.concatenateByteArrays(this.sc.computeShare(pieces,i),this.sc.computeShare(pieces,i));
                
                byte[] salt = SecurityUtil.getSalt(saltsize);
                se.setSalt(salt);
                
                byte[] saltedHashPass =
                        SecurityUtil.getHash(
                                SecurityUtil.concatenateByteArrays(salt,
                                password.getBytes()));
                
                se.setPassHash(SecurityUtil.xorByteArray(saltedHashPass, shamirData));

                sharesEntires.add(se);
                ppa.setShareEntry(sharesEntires);
                users.add(ppa);
            }
            
        } else if (shares == 0) {

            ShareEntry se = new ShareEntry();
            se.setShareNum(0);
            byte[] salt = SecurityUtil.getSalt(saltsize);
            se.setSalt(salt);
       
            byte[] saltedHashPass = SecurityUtil.getHash(
                    SecurityUtil.concatenateByteArrays(salt, password.getBytes()));
            AES.setKey(this.shieldKey);
            byte[] ph = AES.encrypt(saltedHashPass).getBytes();
            
            se.setPassHash(ph);
            sharesEntires.add(se);
            ppa.setShareEntry(sharesEntires);
            users.add(ppa);

        }

    }

    public boolean isValidLogin(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, Exception {

        PPHAccount u = findPPHAccount(username);
        
        if (u==null)
            return false;
        
        for (ShareEntry se : u.getShareEntry()) {
            byte[] saltedPassHash
                    = SecurityUtil.getHash(
                            SecurityUtil.concatenateByteArrays(
                                    se.getSalt(),
                                    password.getBytes()));

            byte[] sharedata = SecurityUtil.xorByteArray(saltedPassHash, se.getPassHash());

            if (se.getShareNum() == 0) {
                AES.setKey(this.shieldKey);
                byte[] enc = AES.encrypt(saltedPassHash).getBytes();
                byte[] dec = se.getPassHash();
                
 //               System.out.println("enc "+ SecurityUtil.bytetoString(enc));
  //              System.out.println("dec "+ SecurityUtil.bytetoString(dec));
                return (enc == dec);
            }
            
            return (sc.isValidShare(pieces,sharedata,se.getShareNum()));
        }

        return false;
    }

    public boolean isAccountUnique(String username) {

        for (PPHAccount p : users) {
            return p.getUsername().equals(username);
        }

        return false;
    }

    /**
     * Find a PPH account based on username.
     *
     * @param username
     * @return
     */
    public PPHAccount findPPHAccount(String username) {
        for (PPHAccount p: users) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }

        return null;
    }

    public byte[] getShamirData() {
        return shamirData;
    }

    public void setShamirData(byte[] shamirData) {
        this.shamirData = shamirData;
    }

    private boolean isValidShamireShare(int num, byte[] sharedata) {

        return Arrays.equals(pieces[num].getBytes(), sharedata);
    }

}
