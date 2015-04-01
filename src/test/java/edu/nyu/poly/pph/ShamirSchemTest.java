/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph;

import com.tiemens.secretshare.engine.ShamirSchem;
import java.math.BigInteger;
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
public class ShamirSchemTest extends TestCase{

    public ShamirSchemTest() {
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
     * Test of stringToBigInteger method, of class ShamirSchem.
     */
    @Test
    public void testStringToBigInteger() throws Exception {
        System.out.println("stringToBigInteger");
        String in = "";
        ShamirSchem instance = new ShamirSchem();
        BigInteger expResult = null;
        BigInteger result = instance.stringToBigInteger(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of splitSecretIntoPieces method, of class ShamirSchem.
     */
    @Test
    public void testSplitSecretIntoPieces() throws Exception {
        System.out.println("splitSecretIntoPieces");
        String secret = "";
        int n = 0;
        int k = 0;
        ShamirSchem instance = new ShamirSchem();
        String[] expResult = null;
        String[] result = instance.splitSecretIntoPieces(secret, n, k);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mergePiecesIntoSecret method, of class ShamirSchem.
     */
    @Test
    public void testMergePiecesIntoSecret() throws Exception {
        System.out.println("mergePiecesIntoSecret");
        String[] pieces = null;
        ShamirSchem instance = new ShamirSchem();
        String expResult = "";
        String result = instance.mergePiecesIntoSecret(pieces);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeShare method, of class ShamirSchem.
     */
    @Test
    public void testComputeShare() {
        System.out.println("computeShare");
        String[] pieces = null;
        int shareNum = 0;
        ShamirSchem instance = new ShamirSchem();
        byte[] expResult = null;
        byte[] result = instance.computeShare(pieces, shareNum);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValidShare method, of class ShamirSchem.
     */
    @Test
    public void testIsValidShare() {
        System.out.println("isValidShare");
        String[] pieces = null;
        byte[] data = null;
        int shareNum = 0;
        ShamirSchem instance = new ShamirSchem();
        boolean expResult = false;
        boolean result = instance.isValidShare(pieces, data, shareNum);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
