package rpncalculator;

import java.math.BigDecimal;

/**
 * Multiplication Operator class.
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class Multiplication implements Operator {


	/**
	 * Calculates the multiplication of two BigDecimal numbers;
	 * @param a Operand a.
	 * @param b Operand b.
	 * @return  The product of a times b.
	 */
	@Override
	public BigDecimal calculate(BigDecimal a, BigDecimal b) {
		return a.multiply(b);
	}
}
