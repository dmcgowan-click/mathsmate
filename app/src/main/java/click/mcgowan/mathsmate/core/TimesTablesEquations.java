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
 * * Number of questions based on the range. Where range is x, total questions are always x*x
 */
public class TimesTablesEquations extends Equations {

    //Parameters
    private boolean random; //Do we want the order of equations to be random

    /**
     * Create a new Times Table Equations object
     *
     * All parameters except for range are statically defined
     *
     * @param range  Allow adjustment of range for timestables
     * @param random One extra parameter for times tables. Do we want the questions out of order
     */
    public TimesTablesEquations (
            int range,
            boolean random
    ) {
        super (
                range * range,
                2,
                range,
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

        //Loop exactly 12 times for x axis
        for (int countx=0; countx < this.range; countx++) {

            //Loop exactly 12 times for y axis
            for (int county=0; county < this.range; county++) {

                //Create new TimesTableEquation object and generate the equation
                prepEquation = new TimesTablesEquation(
                        this.range,
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
