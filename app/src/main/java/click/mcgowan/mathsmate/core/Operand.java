package click.mcgowan.mathsmate.core;

import android.util.Log;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Generate and return a random generated operand in accordance with provided parameters
 */
public class Operand {

    //Parameters
    private int range;          //Largest number of the operand
    private int precision;      //Precision of the operand (0 means no decimals)
    private boolean negative;   //Are negative operands allowed?
    private char operator;      //The operator to use after the operand. Ignored if this is the last operand in the equation

    //Values
    private double operand = 0; //The actual operand once generated

    /**
     * Create a new Operand object
     *
     * For this constructor, an operand is generated based on provided parameters
     * Calling classes ignore operator if operand is the last in the equation
     *
     * @param range     Largest number of the operand
     * @param precision Precision of the operand (0 means no decimals)
     * @param negative  Are negative operands allowed?
     * @param operator  Operator to use after operand
     */
    public Operand(
            int range,
            int precision,
            boolean negative,
            char operator
    ) {
        String precisionMask="#.";
        Random random = new Random();

        this.range     = range;
        this.precision = precision;
        this.negative  = negative;
        this.operator  = operator;

        Log.i("OPERAND_INIT", "Operand Object Initialized");

        //Create plain integer
        this.operand = random.nextInt(this.range) + 1;

        //CANCELLING CODE. WILL INTRODUCE AT LATER DATE
        //Create random decimal to specified precision and add to integer
//        if (this.precision > 0) {
//            for (int count=0; count <  this.precision; count++) {
//                precisionMask = precisionMask + "#";
//            }
//            DecimalFormat precisionFormat = new DecimalFormat(precisionMask);
//
//            this.operand = this.operand + Double.parseDouble(precisionFormat.format(random.nextDouble())) - 1;
//        }

        //Negative Support to be added at a later date

        Log.i("OPERAND_GEN", "Operand " + String.valueOf(this.operand) + " Generated");
    }

    /**
     * Create a new Operand object
     *
     * For this constructor, the operand is already known and provided. Other parameters are used to verify it complies with parameters
     * Calling classes ignore operator if operand is the last in the equation
     *
     * @param range     Largest number of the operand
     * @param precision Precision of the operand (0 means no decimals)
     * @param negative  Are negative operands allowed?
     * @param operator  Operator to use after operand
     * @param operand   The operand you want to set
     */
    public Operand(
            int range,
            int precision,
            boolean negative,
            char operator,
            double operand
    ) {

        this.range = range;
        this.precision = precision;
        this.negative = negative;
        this.operator = operator;

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
