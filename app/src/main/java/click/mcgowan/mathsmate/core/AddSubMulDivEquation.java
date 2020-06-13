package click.mcgowan.mathsmate.core;

import android.util.Log;

import java.util.Random;

/**
 * Generate and return an Arithmetic equation
 *
 * Some important notes about this class:
 *
 * * Operand count, range, precision, negative and allowed operators all set by input parameters by calling class
 * * Special consideration for subtraction to ensure negative number do not occur unless specifically desired
 */
public class AddSubMulDivEquation extends Equation {

    /**
     * Create a new AddSubMulDivEquation object and call genEquation to create the actual equation
     *
     * Set parameters so an equation can be generated in line with these parameters
     *
     * @param operandCount Number of operands for this equation
     * @param range        Range of operands in equation
     * @param precision    Precision of the operands
     * @param negative     Are negative operands allowed?
     * @param operators    Array of operators permitted for equation. Operators are randomly selected based on what is permitted during generation of equation
     */
    public AddSubMulDivEquation (
            int operandCount,
            int range,
            int precision,
            boolean negative,
            char[] operators
    ) {
        super(
                operandCount,
                range,
                precision,
                negative,
                operators
        );

        Log.i("EQUATION_ASMD_INIT","Equation Object Initialized");

        genEquation();
    }

    /**
     * This is quite a bit more complex than i'd like!
     *
     * This will generate an array of operands. The number of these operands based on the operandCount.
     * The range, precision and whether to be negative or positive is controlled by the parameters set when this class was created
     *
     * Some special considerations:
     *
     * * For subtraction, the range will be adjusted so it's never bigger than the previous number. This is to eliminate unwanted negatives
     * * Additional work is needed to handle division correctly. We want numbers to be divisible without decimals or remainders
     */
    protected void genEquation () {

        //Local vars to assist with calculation of equation
        int calcRange = this.range;

        //Set answerCalc to -1 This is to avoid null issues
        this.answerCalc = -1;

        //Init the operands array based on operandCount length
        this.operands = new Operand[this.operandCount];

        //Loop till operand count is reached
        for (int counter = 0; counter < this.operandCount; counter++) {

            //Set the operand object
            this.operands[counter] = new Operand(
                    calcRange,
                    this.precision,
                    this.negative,
                    this.operators
            );

            //If counter == 0, this is our first run. Set calculated answer to the generated operand
            if (counter == 0) {

                this.answerCalc = this.operands[counter].getOperand();
            }
            //If counter != 0, we have generated our first operand and operator. We can now start to calculate our answer
            //For - and /, some special considerations are needed
            else {

                //Perform different equations based on last operator
                switch (this.operands[counter - 1].getOperator()) {
                    case '+' :
                        this.answerCalc = this.answerCalc + this.operands[counter].getOperand();
                        break;
                    case '-' :
                        this.answerCalc = this.answerCalc - this.operands[counter].getOperand();
                        break;
                    case '*' :
                        this.answerCalc = this.answerCalc * this.operands[counter].getOperand();
                        break;
                    case '/' :
                        this.answerCalc = this.answerCalc / this.operands[counter].getOperand();
                }
            }

            //If operator is -, some special considerations needed for range on the next loop, hence the calculated range
            //We want to ensure the range does not exceed the value of this operand so we don't get negative numbers
            if (this.operands[counter].getOperator() == '-') {

                //If counter is 0, ensure range is equal to the newly generated operand
                if (counter == 0) {
                    calcRange = (int) Math.round(this.operands[counter].getOperand());
                }
                //If counter != 0 and calculated answer is less than the overall range, ensure range is equal to calculated answer
                else if (this.answerCalc < this.range) {
                    calcRange = (int) Math.round(this.answerCalc);
                }
                //If counter != 0 and calculated answer is > than overall range, set range as per normal
                else {
                    calcRange = this.range;
                }
            }
        }

        Log.i("EQUATION_TT_GEN", "Generated Equation with Randomly Generated Operands");
    }
}
