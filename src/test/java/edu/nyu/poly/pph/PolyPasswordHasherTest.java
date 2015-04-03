/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph;

import edu.nyu.poly.pph.model.PPHAccount;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ali Gholami <gholami@pdc.kth.se>
 */
public class PolyPasswordHasherTest extends TestCase{
    
    public PolyPasswordHasherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createAccount method, of class PolyPasswordHasher.
     */
    @Test
    public void testCreateAccount(String username, String password, int shares) throws Exception {
        System.out.println("createAccount");
        PolyPasswordHasher instance = null;
        instance.createAccount(username, password, shares);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValidLogin method, of class PolyPasswordHasher.
     * @param username
     * @param password
     */
    @Test
    public void testIsValidLogin(String username, String password) throws Exception {
        System.out.println("isValidLogin");
        PolyPasswordHasher instance = null;
        boolean expResult = false;
        boolean result = instance.isValidLogin(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAccountUnique method, of class PolyPasswordHasher.
     * @param username
     */
    @Test
    public void testIsAccountUnique(String username) {
        System.out.println("isAccountUnique");
        PolyPasswordHasher instance = null;
        boolean expResult = false;
        boolean result = instance.isAccountUnique(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findPPHAccount method, of class PolyPasswordHasher.
     * @param username
     */
    @Test
    public void testFindPPHAccount(String username) {
        System.out.println("findPPHAccount");
        PolyPasswordHasher instance = null;
        PPHAccount expResult = null;
        PPHAccount result = instance.findPPHAccount(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getShamirData method, of class PolyPasswordHasher.
     */
    @Test
    public void testGetShamirData() {
        System.out.println("getShamirData");
        PolyPasswordHasher instance = null;
        byte[] expResult = null;
        byte[] result = instance.getShamirData();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setShamirData method, of class PolyPasswordHasher.
     */
    @Test
    public void testSetShamirData() {
        System.out.println("setShamirData");
        byte[] shamirData = null;
        PolyPasswordHasher instance = null;
        instance.setShamirData(shamirData);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
