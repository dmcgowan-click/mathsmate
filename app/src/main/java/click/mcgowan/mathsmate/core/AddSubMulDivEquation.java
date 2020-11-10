package click.mcgowan.mathsmate.core;

import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
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
     * This will generate an array of operands. The number of these operands is based on the operandCount.
     * The range, precision and whether to be negative or positive is controlled by the parameters set when this class was created
     *
     * Some special considerations:
     *
     * * For subtraction, the range will be adjusted so it's never bigger than the previous number. This is to eliminate unwanted negatives
     * * For subtraction, we need to ensure that the random number generated will ne
     * * Additional work is needed to handle division correctly. We want numbers to be divisible without decimals or remainders
     */
    protected void genEquation () {

        //Local vars to assist with calculation of equation
        int calcRange;
        int divint;
        int modulus;
        List<Integer> validDiv = new ArrayList<Integer>();
        Operand selectValidDiv;

        //Set answerCalc to -1 This is to avoid null issues
        this.answerCalc = -1;

        //Init the operands array based on operandCount length
        this.operands = new Operand[this.operandCount];

        //Loop till operand count is reached
        for (int counter = 0; counter < this.operandCount; counter++) {

            //If counter == 0, this is our first run. Generate operand normally and set calcAnswer to operand
            if (counter == 0) {

                //Set range as per normal
                calcRange = this.range;

                //Set the operand object normally
                this.operands[counter] = new Operand(
                        calcRange,
                        this.precision,
                        this.negative,
                        this.operators
                );

                this.answerCalc = this.operands[counter].getOperand();
            }

            //On our second run, we need to begin calculating our equations AND making special considerations for subtraction and division
            //NOTE: Special considerations not implemented for division. At this point, division is NOT supported
            else {

                //Look at operator of previous operand to decide how to calculate AND if any special considerations are needed
                switch (this.operands[counter - 1].getOperator()) {

                    case '+' :
                        //Set range as per normal
                        calcRange = this.range;

                        //Create new operand as per normal
                        this.operands[counter] = new Operand(
                                calcRange,
                                this.precision,
                                this.negative,
                                this.operators
                        );

                        //Add the operands
                        this.answerCalc = this.answerCalc + this.operands[counter].getOperand();
                        break;

                    case '-' :
                        //Special consideration only applies if negatives are not allowed
                        if (this.negative == false) {

                            //We want to avoid negatives
                            //If the current calculated answer less than our desired range, we need to alter the range to avoid a negative equation
                            if (this.answerCalc < this.range) {

                                //If calculated answer is already 0, we can't subtract any further. Create a pre-determined operand of 0 to avoid negatives
                                if (this.answerCalc == 0) {

                                    //Create pre-determined operand. All settings hard coded
                                    this.operands[counter] = new Operand(
                                            0,
                                            0,
                                            false,
                                            new char[]{'-'},
                                            '-',
                                            0
                                    );
                                }
                                //If calculated answer is 1 or more, we can still generate an equation, though range is the value of the current answer and could be as low as 1
                                else {
                                    calcRange = (int) this.answerCalc;

                                    //Create new operand with reduced range. Note we hard code the negative setting to false as it will always be this value under this situation
                                    this.operands[counter] = new Operand(
                                            calcRange,
                                            this.precision,
                                            false,
                                            this.operators
                                    );
                                }
                            }
                            //If calculated answer is greater than overall range, no risk of negatives. Set range and hence operands as per normal
                            else {

                                //Set range as per normal
                                calcRange = this.range;

                                //Create new operand as per normal. Note we hard code the negative setting to false as it will always be this value under this situation
                                this.operands[counter] = new Operand(
                                        calcRange,
                                        this.precision,
                                        false,
                                        this.operators
                                );
                            }
                        }
                        //Special considerations not applicable. Calculate as per normal
                        else {

                            //Set range as per normal
                            calcRange = this.range;

                            //Create new operand as per normal. Note we hard code the negative setting to true as it will always be this value under this situation
                            this.operands[counter] = new Operand(
                                    calcRange,
                                    this.precision,
                                    true,
                                    this.operators
                            );
                        }

                        //Subtract the operands as per normal regardless of the process setting them
                        this.answerCalc = this.answerCalc - this.operands[counter].getOperand();
                        break;

                    case '*' :
                        //Set range as per normal
                        calcRange = this.range;

                        //Create new operand as per normal
                        this.operands[counter] = new Operand(
                                calcRange,
                                this.precision,
                                this.negative,
                                this.operators
                        );

                        this.answerCalc = this.answerCalc * this.operands[counter].getOperand();
                        break;

                    case '/' :
                        //Special considerations for division to ensure all equations are divisible
                        //Build an array of numbers that will divide the previous operand without remainders
                        for (int divCounter = 0; divCounter < this.operands[counter-1].getOperand(); divCounter++) {

                            divint = (int)this.operands[counter-1].getOperand();
                            modulus = divint % (divCounter + 1);

                            if (modulus == 0) {
                                validDiv.add(divCounter + 1);
                            }
                        }

                        //Use the Operand class, to generate a random index for validDiv
                        //Randomly selected operator will be used for the new operand
                        selectValidDiv = new Operand(
                                validDiv.size(),
                                0,
                                false,
                                this.operators);

                        //Now set the new operand
                        this.operands[counter] = new Operand(
                                this.range,
                                this.precision,
                                this.negative,
                                this.operators,
                                selectValidDiv.getOperator(),
                                validDiv.get((int) selectValidDiv.getOperand())
                        );
//                        calcRange = this.range;
//
//                        //Create new operand as per normal
//                        this.operands[counter] = new Operand(
//                                calcRange,
//                                this.precision,
//                                this.negative,
//                                this.operators
//                        );

                        this.answerCalc = this.answerCalc / this.operands[counter].getOperand();
                }
            }

//            //If operator is -, some special considerations needed for range on the next loop, hence the calculated range
//            //We want to ensure the range does not exceed the value of this operand so we don't get negative numbers
//            if (this.operands[counter].getOperator() == '-') {
//
//                //If calculated answer is less than the overall range, ensure range is equal to calculated answer so random number can't be greater than what we've got
//                if (this.answerCalc < this.range) {
//                    calcRange = (int) Math.round(this.answerCalc);
//                }
//                //If calculated answer is > than overall range, set range as per normal
//                else {
//                    calcRange = this.range;
//                }
//            }
        }

        Log.i("EQUATION_ASMD_GEN", "Generated Equation with Randomly Generated Operands");
    }
}
