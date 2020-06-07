package click.mcgowan.mathsmate;

import org.junit.Test;

import click.mcgowan.mathsmate.core.Operand;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the Operand class of this application
 */
public class OperandUnitTest {

    /**
     * Check that creating an operand with value provided functions as expected
     */
    @Test
    public void operandSet_isCorrect() {

        Operand operand = new Operand(
                20,
                0,
                false,
                '+',
                5);

        assertEquals(5, operand.getOperand(),0);
    }
}
