/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalculator;

import java.math.BigDecimal;

/**
 * Operator Interface.
 * Contains method for calculating an operation.
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public interface Operator {
	/**
	 * Calculates the result of operating a and b using this operator.
	 * @param a a BigDecimal.
	 * @param b a BigDecimal.
	 * @return  The BigDecimal result of the operation.
	 */
	BigDecimal calculate(BigDecimal a, BigDecimal b);
}
