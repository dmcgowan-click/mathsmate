package click.mcgowan.mathsmate.core;

import android.util.Log;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Generate and return a random generated operand in accordance with provided parameters
 */
class Operand {

    private int range;          //Largest number of the operand
    private int precision;      //Precision of the operand (0 means no decimals)
    private boolean negative;   //Are negative operands allowed?

    private double operand = 0; //The actual operand once generated

    /**
     * Create a new Operand object. Set the parameters
     *
     * @param range     Largest number of the operand
     * @param precision Precision of the operand (0 means no decimals)
     * @param negative  Are negative operands allowed?
     */
    Operand(
            int range,
            int precision,
            boolean negative
    ) {
        this.range     = range;
        this.precision = precision;
        this.negative  = negative;

        Log.i("OPERAND_INIT", "Operand Object Initialized");
    }

    /**
     * Generate an operand and store in the operand variable
     *
     * Not really happy about elements of this design, but will need to revisit at a later date
     */
    void genOperand() {

        String precisionMask="#.";
        Random random = new Random();

        //Create plain integer
        this.operand = random.nextInt(this.range) + 1;

        //Create random decimal to specified precision and add to integer
        if (this.precision > 0) {
            for (int count=0; count <  this.precision; count++) {
                precisionMask = precisionMask + "#";
            }
            DecimalFormat precisionFormat = new DecimalFormat(precisionMask);

            this.operand = this.operand + Double.parseDouble(precisionFormat.format(random.nextDouble())) - 1;
        }

        //Negative Support to be added at a later date

        Log.i("OPERAND_GEN", "Operand " + String.valueOf(this.operand) + " Generated");
    }

    /**
     * Take in a value and store in the operand variable.
     *
     * This is used in situations where we may want to predetermine the values (Example, TimesTables where we need 144 x 144 exactly)
     *
     * @param operand User provided operand
     */
    void setOperand(double operand) {
        //Eventually will have error checking against parameters
        this.operand = operand;

        Log.i("OPERAND_SET", "Operand " + String.valueOf(this.operand) + " Set");
    }

    /**
     * Return the operand
     *
     * @return return the operand value
     */
    double getOperand() {
        return (this.operand);
    }
}
