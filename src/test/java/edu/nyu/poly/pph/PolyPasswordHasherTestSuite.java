package edu.nyu.poly.pph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 *
 * @author Ali Gholami <gholami@pdc.kth.se>
 */
public class PolyPasswordHasherTestSuite extends TestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for PolyPassHasher");
    suite.addTestSuite(PolyPasswordHasherTest.class);
    suite.addTestSuite(ShamirSchemTest.class);
    return suite;
  }
}
