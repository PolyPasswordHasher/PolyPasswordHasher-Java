/**
 * *****************************************************************************
 * $Id: $ Copyright (c) 2009-2010 Tim Tiemens. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the GNU
 * Lesser Public License v2.1 which accompanies this distribution, and is
 * available at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *
 * Contributors: Tim Tiemens - initial API and implementation
 *****************************************************************************
 */
package com.tiemens.secretshare.math;

import java.math.BigInteger;

import com.tiemens.secretshare.exceptions.SecretShareException;

/**
 * Polynomial equation implementation. 8*x^3 + 5*x^2 + 13*x + 432 [term3]
 * [term2] [term1] [term0]
 *
 * Arguments to the constructor are in this order: 432 + 13*x + 5*x^2 + 8*x^3
 * [term0] [term1] [term2] [term3]
 *
 *
 * Provides support ONLY for integer exponent values. Provides support ONLY for
 * BigInteger values of "x". Provides support ONLY for BigInteger values of
 * "coefficients"
 *
 * It may be a "short distance" between BigInteger and BigDecimal, but that
 * distance is still too big to justify making this implementation more complex.
 *
 * @author tiemens
 *
 */
public class PolyEquationImpl {

  // ==================================================
  // [0] is x^0, i.e. "*1" the "constant term"
  // [1] is x^1, i.e. "*x" the "x term"
  // [2] is x^2, i.e. the "*x^2 term"
  private BigInteger[] coefficients;


  // ==================================================
  // factories
  // ==================================================
  /**
   * Helper factory to create instances. Accepts 'int', converts them to
   * BigInteger, and calls the constructor.
   *
   * @param args as int values
   * @return instance
   */
  public static PolyEquationImpl create(final int... args) {
    BigInteger[] bigints = new BigInteger[args.length];
    for (int i = 0, n = args.length; i < n; i++) {
      bigints[i] = BigInteger.valueOf(args[i]);
    }
    return new PolyEquationImpl(bigints);
  }

  
  // ==================================================
  // constructors
  // ==================================================
  public PolyEquationImpl(final BigInteger[] inCoeffs) {
    if (inCoeffs.length <= 0) {
      throw new SecretShareException("Must have at least 1 coefficient");
    }
    coefficients = new BigInteger[inCoeffs.length];

    for (int i = 0, n = inCoeffs.length; i < n; i++) {
      if (inCoeffs[i] == null) {
        throw new SecretShareException("Coefficient index=" + i
                + " is null, and cannot be null");
      }
      coefficients[i] = inCoeffs[i];
    }

    // Constraint check:
    for (Number n : coefficients) {
      if (n == null) {
        throw new SecretShareException(
                "Programmer error - internal coefficient is null");
      }
    }
  }
  
  // ==================================================
  // public methods
  // ==================================================

  public BigInteger calculateFofX(final BigInteger x) {
    // pick up the 0th term directly:
    BigInteger ret = coefficients[0];

    // for each of the other terms:
    for (int term = 1, n = coefficients.length; term < n; term++) {
      // the index of term N is N:
      final int indexOfTerm = term;

      // the exponent of term N is N:
      final int powerOfTerm = term;

      BigInteger base = x;
      BigInteger power = base.pow(powerOfTerm);

      BigInteger add = coefficients[indexOfTerm].multiply(power);
      ret = ret.add(add);
    }
    return ret;
  }

  public String debugDump() {
    String ret = "PolyEqImpl[\n";
    for (int i = 0, n = coefficients.length; i < n; i++) {
      ret += "  x^" + i + " * " + coefficients[i].toString() + " + \n";
    }
    ret += "]";
    return ret;
  }

}
