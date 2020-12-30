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
 * * Additional range parameters to control x axis as well as y axis
 * * rangeHigh and rangeLow parameters also control number of questions. Number questions are calculated as follows:
 * * * (rangeHigh - (rangeLow - 1)) * (yRangeHigh - (yRangeLow - 1))),
 */
public class TimesTablesEquations extends Equations {

    //Parameters
    private boolean random; //Do we want the order of equations to be random
    private int yRangeHigh; //Highest number of the operand for the Y axis
    private int yRangeLow; //Highest number of the operand for the Y axis

    /**
     * Create a new Times Table Equations object
     *
     * All parameters except for range are statically defined
     *
     * @param rangeHigh  Highest number of the operand (applicable for x operand)
     * @param rangeLow   Lowest number of the operand  (applicable for x operand)
     * @param yRangeHigh Highest number of the operand (applicable for y operand)
     * @param yRangeLow  Lowest number of the operand  (applicable for y operand)
     * @param random     One extra parameter for times tables. Do we want the questions out of order
     */
    public TimesTablesEquations (
            int rangeHigh,
            int rangeLow,
            int yRangeHigh,
            int yRangeLow,
            boolean random
    ) {
        super (
                (rangeHigh - (rangeLow - 1)) * (yRangeHigh - (yRangeLow - 1)),
                2,
                rangeHigh,
                rangeLow,
                0,
                false,
                new char[]{'*'}
        );

        this.yRangeHigh = yRangeHigh;
        this.yRangeLow = yRangeLow;
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
        for (int countx = (this.rangeLow -1 ); countx < this.rangeHigh; countx++) {

            //Loop from start of yRangeLow to the upper limit of yRangeHigh
            for (int county = (this.yRangeLow - 1); county < this.yRangeHigh; county++) {

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
