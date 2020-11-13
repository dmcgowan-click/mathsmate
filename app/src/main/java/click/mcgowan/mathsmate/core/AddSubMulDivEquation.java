package click.mcgowan.mathsmate.core;

import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generate and return an Arithmetic equation
 *
 * The range, precision and whether to be negative or positive is controlled by the parameters set when this class was created
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
     * This will generate an array of operands. The number of these operands is based on the operandCount.
     * The range, precision and whether to be negative or positive is controlled by the parameters set when this class was created
     *
     * On the second operand, previous operator and calculated answer is also provided to Operand class
     */
    protected void genEquation () {

        //Set answerCalc to -1 This is to avoid null issues
        this.answerCalc = -1;

        //Init the operands array based on operandCount length
        this.operands = new Operand[this.operandCount];

        //Loop till operand count is reached
        for (int counter = 0; counter < this.operandCount; counter++) {

            //If counter == 0, this is our first run. Generate operand without providing previous operator or calculated answer. Set calcAnswer to operand
            if (counter == 0) {

                //Set the operand object without providing previous operator or calculated answer
                this.operands[counter] = new Operand(
                        this.range,
                        this.precision,
                        this.negative,
                        this.operators
                );

                this.answerCalc = this.operands[counter].getOperand();
            }

            //On our second run, we need to begin calculating our equations
            else {

                //Set the operand object and provide previous operator and calculated answer
                this.operands[counter] = new Operand(
                        this.range,
                        this.precision,
                        this.negative,
                        this.operators,
                        this.answerCalc,
                        this.operands[counter - 1].getOperator()
                );

                //Look at operator of previous operand to decide how to calculate
                switch (this.operands[counter - 1].getOperator()) {

                    case '+' :

                        //Add the operands
                        this.answerCalc = this.answerCalc + this.operands[counter].getOperand();
                        break;

                    case '-' :

                        //Subtract the operands
                        this.answerCalc = this.answerCalc - this.operands[counter].getOperand();
                        break;

                    case '*' :

                        //Multiply the operands
                        this.answerCalc = this.answerCalc * this.operands[counter].getOperand();
                        break;

                    case '/' :

                        //Divide the operands
                        this.answerCalc = this.answerCalc / this.operands[counter].getOperand();
                }
            }
        }

        Log.i("EQUATION_ASMD_GEN", "Generated Equation with Randomly Generated Operands");
    }
}
