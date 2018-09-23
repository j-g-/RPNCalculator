package rpncalculator;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Division Operator class.
 * @author J. Garcia, jyo.garcia at gmail.com
 */
public class Division implements Operator {

	/**
	 * Calculates the division of two BigDecimal numbers;
	 * Rounds to DECIMAL128 for irrational numbers.
	 * @param a Operand a.
	 * @param b Operand b.
	 * @return  The division of a  divided by b.
	 */
	@Override
	public BigDecimal calculate(BigDecimal a, BigDecimal b) throws ArithmeticException {
		return b.divide(a,MathContext.DECIMAL128);
	}
}
