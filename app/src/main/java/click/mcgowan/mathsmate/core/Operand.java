package click.mcgowan.mathsmate.core;

import android.util.Log;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Generate and return a random generated operand and operator in accordance with provided parameters
 *
 * OR
 *
 * Set and return desired operand and operator in validated against provided parameters
 */
public class Operand {

    //Parameters
    private int range;          //Largest number of the operand
    private int precision;      //Precision of the operand (0 means no decimals)
    private boolean negative;   //Are negative operands allowed?
    private char operator;      //The operator to use after the operand. Ignored if this is the last operand in the equation
    protected char[] operators;  //Array of operators permitted for equations. Operators are randomly selected based on what is permitted

    //Values
    private double operand = 0; //The actual operand once generated

    /**
     * Create a new Operand object
     *
     * For this constructor:
     *
     *  * An operand is generated based on provided parameters
     *  * An operator is randomly selected based on the array of operators. So if array of operators has just one value of +, then operator will always be +
     *
     * Calling classes ignore operator if operand is the last in the equation
     *
     * @param range      Largest number of the operand
     * @param precision  Precision of the operand (0 means no decimals)
     * @param negative   Are negative operands allowed?
     * @param operators  Array of operators that may be randomly selected along side this operand
     */
    public Operand(
            int range,
            int precision,
            boolean negative,
            char[] operators
    ) {
        //For random generation of operand
        Random randomOpa = new Random();
        int divint;
        int modulus;

        //For random generation of operator
        Random randomOpt = new Random();
        int randomIndex;
        int add = -1;
        int sub = -1;
        int mul = -1;
        int div = -1;

        //Set parameters
        this.range     = range;
        this.precision = precision;
        this.negative  = negative;
        this.operators = operators;

        Log.i("OPERAND_INIT", "Operand Object Initialized");

        //////////////////////////////////////////////////////////////////////////////////////
        //Logic to create negative and decimal precision numbers to be added at a later date//
        //////////////////////////////////////////////////////////////////////////////////////

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

        //Create operand. Special consideration for Division to ensure divisible numbers
        if (this.operator == '/') {
            do {
                this.operand = randomOpa.nextInt(this.range) + 1;
                divint = (int)this.operand;

                if (divint == 1) {
                    modulus = 0;
                }
                else {
                    modulus = divint % 2;
                }

            } while (modulus > 0);
        }
        else {
            this.operand = randomOpa.nextInt(this.range) + 1;
        }

        Log.i("OPERAND_GEN", "Operand " + String.valueOf(this.operand) + " Generated");
    }

    /**
     * Create a new Operand object
     *
     * For this constructor, the operand is already known and provided. Other parameters are used to verify it complies with parameters
     * Calling classes ignore operator if operand is the last in the equation
     *
     * NOTE: Actual validation not implemented yet. To be done at a later date
     *
     * @param range     Largest number of the operand
     * @param precision Precision of the operand (0 means no decimals)
     * @param negative  Are negative operands allowed?
     * @param operators Array of permitted operators
     * @param operator  Operator to use after operand
     * @param operand   The operand you want to set
     */
    public Operand(
            int range,
            int precision,
            boolean negative,
            char[] operators,
            char operator,
            double operand
    ) {
        this.range = range;
        this.precision = precision;
        this.negative = negative;
        this.operator = operator;
        this.operators = operators;

        //Eventually will have error checking against parameters
        this.operand = operand;

        Log.i("OPERAND_SET", "Operand " + String.valueOf(this.operand) + " Set");
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
