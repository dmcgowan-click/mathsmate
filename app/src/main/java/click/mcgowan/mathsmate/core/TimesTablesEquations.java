package click.mcgowan.mathsmate.core;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generate and return a map of times table equations in accordance with provided parameters
 *
 * * Some important notes about this class:
 *
 * * Operand count is hard coded to 2. Should never be more
 * * Precision hard coded to 0. Never decimal places in times tables
 * * Negative is hard coded to false. Never negative numbers in times tables
 * * Array of allowed operators is always set to {'*'}
 * * rangeHigh and rangeLow parameters also control number of questions. Number questions are calculated as follows:
 * * * (rangeHigh - (rangeLow - 1)) * 12,
 */
public class TimesTablesEquations extends Equations {

    //Parameters
    private boolean random; //Do we want the order of equations to be random

    /**
     * Create a new Times Table Equations object
     *
     * All parameters except for range are statically defined
     *
     * @param rangeHigh Highest number of the operand (applicable for first operand only)
     * @param rangeLow  Lowest number of the operand  (applicable for first operand only)
     * @param random    One extra parameter for times tables. Do we want the questions out of order
     */
    public TimesTablesEquations (
            int rangeHigh,
            int rangeLow,
            boolean random
    ) {
        super (
                (rangeHigh - (rangeLow - 1)) * 12,
                2,
                rangeHigh,
                rangeLow,
                0,
                false,
                new char[]{'*'}
        );

        this.random = random;

        Log.i ("EQUATIONS_TT_INIT", "Equations Object Initialized");

        this.genEquations();
    }

    /**
     * Generate Equations for Times Tables
     *
     * Unlike other equation types, operands are predefined and not random. HOWEVER, the actual ordering of the equations themselves can be random
     * x*x where x is the desired range
     */
    protected void genEquations () {
        TimesTablesEquation prepEquation;
        List<Integer> rndIndex = new ArrayList<>();
        int index = 0;

        //We create a random index regardless of if its needed
        for (int counter = 0; counter < this.equationCount; counter++) {
            rndIndex.add(counter);
        }
        Collections.shuffle(rndIndex);

        //Loop from start of rangeLow to the upper limit of rangeHigh
        for (int countx = (this.rangeLow -1); countx < this.rangeHigh; countx++) {

            //Loop exactly 12 times for y axis
            for (int county=0; county < 12; county++) {

                //Create new TimesTableEquation object and generate the equation
                prepEquation = new TimesTablesEquation(
                        this.rangeHigh,
                        this.rangeLow,
                        countx + 1,
                        county + 1
                );

                //If random was true, use the collections to set the index
                if (this.random == true) {

                    //Add new equation to map based on random index
                    equationMap.put(rndIndex.get(index), prepEquation);
                }
                //If random was false, just use the index counter
                else {

                    //Add new equation to map based on incremented index
                    equationMap.put(index, prepEquation);
                }

                //Increment Index
                index++;
            }
        }

        Log.i("EQUATIONS_TT_GEN","Generated " + String.valueOf(index) + " Equations");
    }
}
