package rpncalculator;

import java.math.BigDecimal;

/**
 * Addition Operator class.
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class Addition implements Operator {

	/**
	 * Calculates the sum of two BigDecimal numbers;
	 * @param a Operand a.
	 * @param b Operand b.
	 * @return  The sum of a plus b.
	 */
	@Override
	public BigDecimal calculate(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}
}
