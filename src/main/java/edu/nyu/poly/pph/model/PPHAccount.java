/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ali Gholami <gholami@pdc.kth.se>
 */
public class PPHAccount {

    // every user has an account

    private String username; // gholami
    private String password; // blah123
    private List<ShareEntry> shareEntry;
    private byte [] passHash;

    
    public PPHAccount(String username, String password) {
        shareEntry = new ArrayList<ShareEntry>();
        this.username= username;
        this.password = password;
    }

    
    public byte[] getPassHash() {
        return passHash;
    }

    public void setPassHash(byte[] passHash) {
        this.passHash = passHash;
    }

    
    public List <ShareEntry> getShareEntry() {
        return shareEntry;
    }

    public void setShareEntry(List<ShareEntry> shareEntry) {
        this.shareEntry = shareEntry;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
