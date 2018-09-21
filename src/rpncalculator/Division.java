/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalculator;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class Division implements Operator {

	@Override
	public BigDecimal calculate(BigDecimal a, BigDecimal b) throws ArithmeticException {
		return a.divide(b,MathContext.DECIMAL128);
	}
}
