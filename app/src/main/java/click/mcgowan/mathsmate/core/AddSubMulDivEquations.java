package click.mcgowan.mathsmate.core;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generate and return a map of times table equations in accordance with provided parameters
 *
 * * Some important notes about this class:
 *
 * * Equation count, operand count, range, precision, negative and allowed operators all set by input parameters by calling class
 */
public class AddSubMulDivEquations extends Equations {

    /**
     * Create a new object for Addition, Subtraction, Multiplication and Division
     *
     * @param equationCount Number of equations to generate
     * @param operandCount  Number of operands in the equation
     * @param rangeHigh    Highest number of the operand
     * @param rangeLow     Lowest number of the operand
     * @param precision     Precision of the operand (0 means no decimals)
     * @param negative      Are negative operands allowed?
     */
    public AddSubMulDivEquations(
            int equationCount,
            int operandCount,
            int rangeHigh,
            int rangeLow,
            int precision,
            boolean negative,
            char[] operators
    ) {
        super (
                equationCount,
                operandCount,
                rangeHigh,
                rangeLow,
                precision,
                negative,
                operators
        );

        Log.i ("EQUATIONS_ASMD_INIT", "Equations Object Initialized");

        this.genEquations();
    }

    /**
     * Generate equations for Addition, Subtraction, Multiplication and Division
     *
     * Provided parameters will control the type of equations generated
     */
    protected void genEquations () {
        AddSubMulDivEquation prepEquation;

        for (int counter = 0; counter < this.equationCount; counter++) {

            prepEquation = new AddSubMulDivEquation(
                    this.operandCount,
                    this.rangeHigh,
                    this.rangeLow,
                    this.precision,
                    this.negative,
                    this.operators
            );

            equationMap.put(counter, prepEquation);
        }

        Log.i("EQUATIONS_TT_GEN","Generated " + this.equationCount + " Equations");
    }
}
