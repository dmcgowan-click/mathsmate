package click.mcgowan.mathsmate.core;

import android.util.Log;

import java.util.Random;

/**
 * Generate and return an Arithmetic equation
 *
 * Some important notes about this class:
 *
 * * Operand count
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
     * This will generate operands matching the value in operandCount. The random generation of the operand is handled by the Operand class based on provided parameters
     *
     * For each loop, it will also randomly select an operator based on the operators provided. So it works like this:
     *
     * * If operator array has just +, then a random number of always 0 will be generated each loop and + will be used in the next step of the equation
     * * If operator array has +,-, then a random number of 0 or 1 will be generated each loop and either + or - will be used in the next step of the equation
     */
    protected void genEquation () {

        //set up random selection of operator
        Random randomOpt = new Random();
        Random randomOpa = new Random();
        int randomIndex;
        int add = -1;
        int sub = -1;
        int mul = -1;
        int div = -1;

        //Init the operands array based on operandCount length
        this.operands = new Operand[this.operandCount];

        //Loop till operand count is reached
        for (int counterOpa = 0; counterOpa < this.operandCount; counterOpa++) {

            //Loop through each value of operators. Set an integer value for each operator that was found
            for(int counterOpt = 0; counterOpt < this.operators.length; counterOpt++) {

                //Where an operator is found, set the counterOpt number to the different operator types to indicate the array index it was found
                switch (this.operators[counterOpt]) {
                    case '+' : add = counterOpt;
                        break;
                    case '-' : sub = counterOpt;
                        break;
                    case '*' : mul = counterOpt;
                        break;
                    case '/' : div = counterOpt;
                }
            }

            //Now generate a random number which will be a random index to the desired operator.
            randomIndex = randomOpt.nextInt(this.operators.length);

            //If the random operator index matches the index that was set for add, we are adding
            if (add == randomIndex) {
                this.operands[counterOpa] = new Operand(
                        this.range,
                        this.precision,
                        this.negative,
                        '+'
                );
                if (counterOpa == 0) {
                    this.answerCalc = this.operands[counterOpa].getOperand();
                }
                else {
                    this.answerCalc = this.answerCalc + this.operands[counterOpa].getOperand();
                }
            }
            //If the random operator index matches the index that was set for sub, we are subtracting
            else if (sub == randomIndex) {
                this.operands[counterOpa] = new Operand(
                        this.range,
                        this.precision,
                        this.negative,
                        '-'
                );
                if (counterOpa == 0) {
                    this.answerCalc = this.operands[counterOpa].getOperand();
                }
                else {
                    this.answerCalc = this.answerCalc - this.operands[counterOpa].getOperand();
                }
            }
            //If the random operator index matches the index that was set for mul, we are multiplying
            else if (mul == randomIndex) {
                this.operands[counterOpa] = new Operand(
                        this.range,
                        this.precision,
                        this.negative,
                        '*'
                );
                if (counterOpa == 0) {
                    this.answerCalc = this.operands[counterOpa].getOperand();
                }
                else {
                    this.answerCalc = this.answerCalc * this.operands[counterOpa].getOperand();
                }
            }
            //If the random operator index matches the index that was set for mul, we are multiplying
            else if (div == randomIndex) {
                this.operands[counterOpa] = new Operand(
                        this.range,
                        this.precision,
                        this.negative,
                        '/'
                );
                if (counterOpa == 0) {
                    this.answerCalc = this.operands[counterOpa].getOperand();
                }
                else {
                    this.answerCalc = this.answerCalc / this.operands[counterOpa].getOperand();
                }
            }

            //When the
            //Set an operand

            //Set the Operand object and generate the operand
//            this.operands[counter] = new Operand(
//                    this.range,
//                    this.precision,
//                    this.negative);

            //Calculate the actual answer as looping through
        }
    }
}
