/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph.model;

/**
 *
 * @author Ali Gholami <gholami@pdc.kth.se>
 */
public class ShareEntry {

    //threholdless 0 shares
    // threshhold 1 admin account
    private int shareNum; // just 1

    private byte[] salt;

    private byte[] passHash;

    
    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getPassHash() {
        return passHash;
    }

    public void setPassHash(byte[] passHash) {
        this.passHash = passHash;
    }

}
