/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph;

import com.tiemens.secretshare.engine.ShamirSchem;
import edu.nyu.poly.pph.model.PPHAccount;
import edu.nyu.poly.pph.model.ShareEntry;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Ali Gholami <gholami@pdc.kth.se>
 */
public class PolyPasswordHasher {

  private Properties props;

  private PPHAccount pphAccount;

  private List<PPHAccount> users;

  private byte[] shieldKey;

// knowledge of shares to decode others.
  private int threshold;

  private int maxSharesSize;

  private int saltSize;

  private int nextavailableshare = 1;

  private ShamirSchem sc;

  private String[] pieces;

  private byte[] shamirData;

  public PolyPasswordHasher(String propFile) throws UnsupportedEncodingException,
          IOException {

    loadPPHProperties(propFile);
    this.threshold = Integer.parseInt(props.getProperty("THRESHOLD"));
    this.shieldKey = props.getProperty("MASTER_KEY").getBytes("UTF-8");
    this.maxSharesSize = Integer.parseInt(props.getProperty(
            "MAX_NUMBER_OF_SHARES"));
    this.saltSize = Integer.parseInt(props.getProperty("SALT_SIZE"));
    this.sc = new ShamirSchem();

    // shieldkey, divide the shieldkey to n shares, number of shares to recover 
    pieces = sc.splitSecretIntoPieces(props.getProperty("MASTER_KEY"),
            maxSharesSize, this.threshold);

    users = new ArrayList<>();

  }

  public void createAccount(String username, String password, int shares) throws
          NoSuchAlgorithmException, UnsupportedEncodingException,
          InvalidKeyException, IllegalBlockSizeException,
          InvalidAlgorithmParameterException, NoSuchPaddingException,
          BadPaddingException, Exception {

    if (isAccountUnique(username)) {
      return;
    }

    if (shares < 0 || shares > maxSharesSize) {
      return;
    }

    if (shares + nextavailableshare > 255) {
      return;
    }

    PPHAccount ppa = new PPHAccount(username, password);

    List<ShareEntry> sharesEntires = new ArrayList<>();

    if (shares == 0) {
      ShareEntry se = new ShareEntry();
      se.setShareNum(0);
      byte[] salt = SecurityUtil.getSalt(saltSize);
      se.setSalt(salt);

      byte[] saltedHashPass = SecurityUtil.getHash(
              SecurityUtil.concatenateByteArrays(
                      salt, password.getBytes("UTF-8")));
      AES.setKey(this.shieldKey);
      byte[] ph = AES.encrypt(saltedHashPass).getBytes("UTF-8");
      se.setPassHash(ph);
      sharesEntires.add(se);

    } else if (shares != 0) {
      ShareEntry se = new ShareEntry();
      for (int i = nextavailableshare; i < nextavailableshare + shares; i++) {
        se.setShareNum(i);

        //System.out.println("### create account Share " + i+ " " + SecurityUtil.bytetoString(this.sc.computeShare(pieces, i)));
        //E8D2p7yqLi6L1zp3EzKGqg== E8D2p7yqLi6L1zp3EzKGqg==
        shamirData = SecurityUtil.concatenateByteArrays(
                this.sc.computeShare(pieces, i),
                this.sc.computeShare(pieces, i));

        byte[] salt = SecurityUtil.getSalt(saltSize);
        se.setSalt(salt);

        byte[] saltedHashPass
                = SecurityUtil.getHash(
                        SecurityUtil.concatenateByteArrays(salt,
                                password.getBytes("UTF-8")));

        se.setPassHash(SecurityUtil.xorByteArray(saltedHashPass, shamirData));

        sharesEntires.add(se);
      }
    }
    ppa.setShareEntry(sharesEntires);
    users.add(ppa);
    nextavailableshare = nextavailableshare + shares;
  }

  public boolean isValidLogin(String username, String password) throws
          NoSuchAlgorithmException, UnsupportedEncodingException,
          InvalidKeyException, IllegalBlockSizeException,
          InvalidAlgorithmParameterException, NoSuchPaddingException,
          BadPaddingException, Exception {

    PPHAccount u = findPPHAccount(username);

    if (u == null) {
      return false;
    }

    for (ShareEntry se : u.getShareEntry()) {
      byte[] saltedPassHash
              = SecurityUtil.getHash(
                      SecurityUtil.concatenateByteArrays(
                              se.getSalt(),
                              password.getBytes("UTF-8")));

      if (se.getShareNum() == 0) {
        AES.setKey(this.shieldKey);
        byte[] enc = AES.encrypt(saltedPassHash).getBytes("UTF-8");
        byte[] dec = se.getPassHash();
        return (Arrays.equals(enc, dec));
      } else if (se.getShareNum() != 0) {
        byte[] sharedata = SecurityUtil.xorByteArray(saltedPassHash, se.
                getPassHash());
        return (sc.isValidShare(pieces, sharedata, se.getShareNum()));
      }
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
    for (PPHAccount p : users) {
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

  private boolean isValidShamireShare(int num, byte[] sharedata) throws
          UnsupportedEncodingException {
    return Arrays.equals(pieces[num].getBytes("UTF-8"), sharedata);
  }

  public PPHAccount getPphAccount() {
    return pphAccount;
  }

  public void setPphAccount(PPHAccount pphAccount) {
    this.pphAccount = pphAccount;
  }

  public List<PPHAccount> getUsers() {
    return users;
  }

  public void setUsers(List<PPHAccount> users) {
    this.users = users;
  }

  public int getThreshold() {
    return threshold;
  }

  public void setThreshold(int threshold) {
    this.threshold = threshold;
  }

  public int getNextavailableshare() {
    return nextavailableshare;
  }

  public void setNextavailableshare(int nextavailableshare) {
    this.nextavailableshare = nextavailableshare;
  }

  /**
   * Load the PPH properties from the properties file.
   * <p>
   * @param propFile
   * @throws java.io.IOException
   */
  public final void loadPPHProperties(String propFile) throws IOException {
    props = new Properties();

    try (InputStream inputStream
            = getClass().getClassLoader().getResourceAsStream(propFile)) {
      props.load(inputStream);
    }

  }

}
