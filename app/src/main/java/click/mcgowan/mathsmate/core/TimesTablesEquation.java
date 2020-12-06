package click.mcgowan.mathsmate.core;

import android.util.Log;

/**
 * Generate and return a times table equation in accordance with provided parameters
 *
 * Some important notes about this class:
 *
 * * Operand count is hard coded to 2. Should never be more or less
 * * Precision hard coded to 0. Never decimal places in times tables
 * * Negative is hard coded to false. Never negative numbers in times tables
 * * Array of allowed operators is always set to {'*'}. It's times tables. Pretty self explanatory!
 */
public class TimesTablesEquation extends Equation {

    //Operand class needs an operator. It will ALWAYS be *
    private final char operator = '*';

    //For timestables, we take in preset operands. Will always be 0 decimal precision positive. Never dynamically calculated
    private int operandA;
    private int operandB;

    /**
     * Create a new Times Table Equations object and call genEquation to create the actual equation
     *
     * For times tables, the equations are more or less predetermined.
     * So we statically set all parameters except for range and take in two operands exactly that should be provided by calling method
     *
     * @param rangeHigh Highest number of the operand
     * @param rangeLow  Lowest number of the operand
     * @param operandA  Value of the first operand
     * @param operandB  Value of the second operand
     */
    public TimesTablesEquation(
            int rangeHigh,
            int rangeLow,
            int operandA,
            int operandB
    ) {
        //As operands are been statically defined, so can parameters except for range
        super (
                2,
                rangeHigh,
                rangeLow,
                0,
                false,
                new char[]{'*'}
        );

        this.operandA = operandA;
        this.operandB = operandB;

        Log.i("EQUATION_TT_INIT","Equation Object Initialized");

        this.genEquation();
    }

    /**
     * Generate a times table equation. Use operands passed in via the constructor.
     *
     * Operand count will always be exactly 2. 0 decimal precision. positive number. Operator always *
     */
    protected void genEquation () {

        //Set operands into array. This makes handling the operands array easier
        double[] tteOperands = {this.operandA,this.operandB};
        this.operands = new Operand[tteOperands.length];

        //Loop through each operand and calculate the actual answer
        for (int counter=0; counter < this.operands.length; counter++) {

            //Set the operand object
            this.operands[counter] = new Operand(
                    this.rangeHigh,
                    this.rangeLow,
                    this.precision,
                    this.negative,
                    this.operator,
                    tteOperands[counter]
            );

            //Calculate the actual answer while looping through
            if (counter == 0) {
                this.answerCalc = this.operands[counter].getOperand();
            }
            else {
                this.answerCalc = this.answerCalc * this.operands[counter].getOperand();
            }
        }

        Log.i("EQUATION_TT_GEN", "Generated Equation with Operands " + String.valueOf(this.operandA) + " and " + String.valueOf(this.operandB));
    }
}
