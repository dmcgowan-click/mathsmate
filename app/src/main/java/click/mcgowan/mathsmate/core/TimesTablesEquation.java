package click.mcgowan.mathsmate.core;

import android.util.Log;

/**
 * Generate and return a times table equation in accordance with provided parameters
 *
 * Some important notes about this class:
 *
 * * Operand count is hard coded to 2. Should never be more
 * * Precision hard coded to 0. Never decimal places in times tables
 * * Negative is hard coded to false. Never negative numbers in times tables
 * * The parent constructor calculates the number of questions based on the range. Where range is x, total questions are always x*x
 */
public class TimesTablesEquation extends Equation {

    //For timestables, we take in preset operands. Will always be 0 decimal precision positive. Never dynamically calculated
    private int operandA;
    private int operandB;

    /**
     * Create a new Times Table Equations object and call genEquation to create the actual equation
     *
     * Since we know the type of equations, we statically set all parameters except for range and take in two operands
     *
     * @param operandA Value of the first operand
     * @param operandB Value of the second operand
     */
    public TimesTablesEquation(
            int range,
            int operandA,
            int operandB
    ) {
        //As operands are been statically defined, so can parameters except for range
        super (
                2,
                range,
                0,
                false
        );

        this.operandA = operandA;
        this.operandB = operandB;

        Log.i("EQUATION_TT_INIT","Equation Object Initialized");

        this.genEquation();
    }

    /**
     * Generate a times table equation. Use operands passed in via the constructor.
     *
     * Operand count will always be exactly 2. 0 decimal precision positive number
     */
    protected void genEquation () {

        //Set operands into array. This makes handling the operands array easier
        double[] tteOperands = {this.operandA,this.operandB};
        operands = new Operand[tteOperands.length];

        //Loop through each operand and calculate the actual answer
        for (int counter=0; counter < this.operands.length; counter++) {

            //Set the operand object
            operands[counter] = new Operand(
                    this.range,
                    this.precision,
                    this.negative,
                    tteOperands[counter]
            );

            //Calculate the actual answer as looping through
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
