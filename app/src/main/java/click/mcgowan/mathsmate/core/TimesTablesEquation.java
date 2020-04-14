package click.mcgowan.mathsmate.core;

import android.util.Log;

/**
 * Generate and return an times table equation in accordance with provided parameters
 */
public class TimesTablesEquation extends Equation {

    //For timestables, we take in preset operands. Will always be 0 decimal precision positive. Never dynamically calculated
    private int operandA;
    private int operandB;

    /**
     * Create a new Times Table Equations object
     *
     * Since we know the type of equations, we statically set all parameters and just take in two operands
     *
     * @param operandA Value of the first operand
     * @param operandB Value of the second operand
     */
    TimesTablesEquation(
            int operandA,
            int operandB
    ) {
        //As operands are been statically defined, so can parameters
        super (
                2,
                12,
                0,
                false
        );

        this.operandA = operandA;
        this.operandB = operandB;

        Log.i("EQUATION_TT_INIT","Equation Object Initialized");
    }

    /**
     * Generate the equation and set the actual calculated answer
     */
    public void genEquation () {
        //Make sure operands are created first. For this equation, we set manually
        double[] tteOperands = {this.operandA,this.operandB};
        setOperands(tteOperands);

        //Loop through each operand and calculate the actual answer
        for (int counter=0; counter < this.operands.length; counter++) {

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
