package click.mcgowan.mathsmate.core;

import android.util.Log;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generate and return a random generated operand and operator in accordance with provided parameters
 *
 * * Special considerations made for subtraction where negative flag is false to ensure no negative numbers
 * * Special considerations made for division to ensure all numbers are divisible without producing remainders
 * * Two constructors are offered. One allows you to provide the previous operator and calculated answer to assist with these special considerations
 *
 * OR
 *
 * Set and return desired operand and operator (where the operand / operator is pre determined)
 */
public class Operand {

    //Parameters
    private int rangeHigh;      //Highest number of the operand
    private int rangeLow;      //Lowest number of the operand
    private int precision;     //Precision of the operand (0 means no decimals)
    private boolean negative;  //Are negative operands allowed?
    private char operator;     //The operator to use after the operand. Ignored if this is the last operand in the equation
    private char[] operators;  //Array of operators permitted for equations. Operators are randomly selected based on what is permitted
    private double calcAnswer; //The calculated answer based on previous operands (if applicable). To help with equation generation
    private char prevOperator; //The operator of previous operand (if applicable). To help with equation generation

    //Values
    private double operand = 0; //The actual operand once generated

    /**
     * Create a new Operand object and call genOperandOperator
     *
     * For this constructor:
     *
     * * calcAnswer and prevOperator are set default and are not accepted as input
     *
     * Calling classes ignore operator if operand is the last in the equation
     *
     * @param rangeHigh  Highest number of the operand
     * @param rangeLow   Lowest number of the operand
     * @param precision  Precision of the operand (0 means no decimals)
     * @param negative   Are negative operands allowed?
     * @param operators  Array of operators that may be randomly selected along side this operand
     */
    public Operand(
            int rangeHigh,
            int rangeLow,
            int precision,
            boolean negative,
            char[] operators
    ) {
        //Set parameters
        this.rangeHigh = rangeHigh;
        this.rangeLow = rangeLow;
        this.precision = precision;
        this.negative = negative;
        this.operators = operators;
        this.calcAnswer = rangeHigh; //When calcAnswer isn't provided, we just set it to high range effectively ensuring it is ignored
        this.prevOperator = '?';

        //Log.i("OPERAND_INIT", "Operand Object Initialized");

        genOperandOperator();
    }

    /**
     * Create a new Operand object and call genOperandOperator
     *
     * For this constructor:
     *
     * * calcAnswer and prevOperator are set by input and will be used by the genOperandOperator method
     *
     * Calling classes ignore operator if operand is the last in the equation
     *
     * @param rangeHigh    Highest number of the operand
     * @param rangeLow     Lowest number of the operand
     * @param precision    Precision of the operand (0 means no decimals)
     * @param negative     Are negative operands allowed?
     * @param operators    Array of operators that may be randomly selected along side this operand
     * @param calcAnswer   Calculated answer from previous operands
     * @param prevOperator Operator from previous operand
     */
    public Operand(
            int rangeHigh,
            int rangeLow,
            int precision,
            boolean negative,
            char[] operators,
            double calcAnswer,
            char prevOperator
    ) {
        //Set parameters
        this.rangeHigh = rangeHigh;
        this.rangeLow = rangeLow;
        this.precision = precision;
        this.negative = negative;
        this.operators = operators;
        this.calcAnswer = calcAnswer;
        this.prevOperator = prevOperator;

        Log.i("OPERAND_INIT", "Operand Object Initialized");

        genOperandOperator();
    }

    /**
     * For this constructor, the operand is already known and provided.
     * We will still ask for various parameters however it's for metadata only and not relevant to the creation of the operand
     *
     * Calling classes ignore operator if operand is the last in the equation
     *
     * @param rangeHigh    Highest number of the operand
     * @param rangeLow     Lowest number of the operand
     * @param precision Precision of the operand (0 means no decimals)
     * @param negative  Are negative operands allowed?
     * @param operator  Operator to use after operand
     * @param operand   The operand you want to set
     */
    public Operand(
            int rangeHigh,
            int rangeLow,
            int precision,
            boolean negative,
            char operator,
            double operand
    ) {
        this.rangeHigh = rangeHigh;
        this.rangeLow = rangeLow;
        this.precision = precision;
        this.negative = negative;
        this.operator = operator;
        this.operators = new char[]{operator};
        this.operand = operand;
        this.calcAnswer = rangeHigh; //When calcAnswer isn't provided, we just set it to range effectively ensuring it is ignored
        this.prevOperator = '?';

        Log.i("OPERAND_SET", "Operand " + String.valueOf(this.operand) + " Set");
    }

    /**
     * Generate a new operand and operator based on parameters
     *
     * * An operand is generated based on provided parameters
     * * An operator is randomly selected based on the array of operators. So if array of operators has just one value of +, then operator will always be +
     * * Where calcAnswer and prevOperator are set to default values, they are not used when generating the operand or operator
     */
    private void genOperandOperator () {

        //For random generation of operand where required
        Random randomOpa = new Random();
        int divint;
        int modulus;
        List<Integer> validDiv;

        //For random generation of operator
        Random randomOpt = new Random();
        int randomIndex;
        int add = -1;
        int sub = -1;
        int mul = -1;
        int div = -1;

        //////////////////////////////////////////////////////////////////////////////////////
        //Logic to create negative and decimal precision numbers to be added at a later date//
        //////////////////////////////////////////////////////////////////////////////////////

        //Select a random operator

        //Loop through each value of operators. Set an integer value for each operator that was found
        for(int counterOpt = 0; counterOpt < this.operators.length; counterOpt++) {

            //Where an operator is found, set the counterOpt number to the operator type. Now the operator is represented by a unique index
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

        //Now generate a random number which will be a index to the desired operator as set in previous step.
        randomIndex = randomOpt.nextInt(this.operators.length);

        //If the random number matches the index we set for add, set operator to addition
        if (add == randomIndex) {

            this.operator = '+';
        }
        //If the random number matches the index we set for sub, set operator to subtraction
        else if (sub == randomIndex) {

            this.operator = '-';
        }
        //If the random number matches the index we set for mul, set operator to multiplication
        else if (mul == randomIndex) {

            this.operator = '*';
        }
        //If the random number matches the index we set for div, set operator to division
        else if (div == randomIndex) {

            this.operator = '/';
        }

        //Generate a random operand

        //Special consideration to ensure divisible numbers where
        // * operator for this operand is /
        // * previous operator is ? (this rule only applies if this is the first operand in an equation)
        if (this.operator == '/' && this.prevOperator == '?') {

            do {
                this.operand = randomOpa.nextInt(this.rangeHigh - (this.rangeLow - 1)) + 1 + (this.rangeLow - 1);
                divint = (int)this.operand;

                if (divint == 1) {
                    modulus = 0;
                }
                else {
                    modulus = divint % 2;
                }

            } while (modulus > 0);
        }

        //Special consideration for subtraction to prevent negative numbers where
        // * negatives are false
        // * previous operator is -
        // * calculated answer < range
        else if (this.negative == false && this.prevOperator == '-' && this.calcAnswer < this.rangeHigh) {

            //If calcAnswer is already 0, we can't go any further. Static set to 0
            if (this.calcAnswer == 0) {
                this.operand = 0;
            }
            //Use calcAnswer as our range
            else {
                //May need further work. Or we might need to accept numbers below desired range to avoid negatives
                this.operand = randomOpa.nextInt((int)this.calcAnswer) + 1;
            }
        }

        //Special consideration to ensure divisible numbers where
        // * previous operator is /
        else if (this.prevOperator == '/') {

            //Build an array of numbers that will divide the previous operand without remainders
            //As with subtraction, we might need to accept numbers below desired range to avoid negatives
            validDiv = new ArrayList<Integer>();

            for (int divCounter = 0; divCounter < this.calcAnswer; divCounter++) {

                divint = (int)this.calcAnswer;
                modulus = divint % (divCounter + 1);

                if (modulus == 0) {
                    validDiv.add(divCounter + 1);
                }
            }

            //Randomly select an operand from list of valid operands
            this.operand = validDiv.get(randomOpa.nextInt(validDiv.size()));
        }
        //Special consideration not required. Generate operand as per other parameters
        else {

            this.operand = randomOpa.nextInt(this.rangeHigh - (this.rangeLow - 1)) + 1 + (this.rangeLow - 1);
        }

        Log.i("OPERAND_GEN", "Operand " + String.valueOf(this.operand) + " Generated");
    }

    /**
     * Return the operand
     *
     * @return return the operand value
     */
    public double getOperand() {
        return (this.operand);
    }

    /**
     * Return the operator
     *
     * @return return the operator value
     */
    public char getOperator() {
        return (this.operator);
    }
}
