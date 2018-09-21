/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalculator;

import java.math.BigDecimal;

/**
 *
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public interface Operator {
	BigDecimal calculate(BigDecimal a, BigDecimal b);
}
