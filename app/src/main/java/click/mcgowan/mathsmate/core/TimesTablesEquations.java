package click.mcgowan.mathsmate.core;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generate and return a map of times table equations in accordance with provided parameters
 */
public class TimesTablesEquations extends Equations {

    //Parameters
    private boolean random;

    /**
     * Create a new Times Table Equations object
     *
     * operandCount, precision and negative is statically set for timestables
     *
     * @param range  Allow adjustment of range for timestables
     * @param random One extra parameter for times tables. Do we want the questions out of order
     */
    public TimesTablesEquations (
            int range,
            boolean random
    ) {
        super (
                2,
                range,
                0,
                false
        );

        this.random = random;

        Log.i ("EQUATIONS_TT_INIT", "Equations Object Initialized");

        this.genEquations();
    }

    /**
     * Generate Equations for Times Tables
     *
     * Unlike other equation types, this is predefined and not random
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
                    equationMap.put(String.valueOf(rndIndex.get(index)),prepEquation);
                }
                //If random was false, just use the index counter
                else {

                    //Add new equation to map based on incremented index
                    equationMap.put(String.valueOf(index),prepEquation);
                }

                //Increment Index
                index++;
            }
        }

        Log.i("EQUATIONS_TT_GEN","Generated " + String.valueOf(index) + " Equations");
    }

    /**
     * Return specific operand IN equation. Use key to locate the equation and index to locate the operand
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @param key   Key to the map element that contains the desired Equation
     * @param index Index to the operand IN the map element
     * @return    An array with the operands for the equation
     */
    public String getOperandForEquation (String key, int index) {

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(key);

        return (String.valueOf(tte.getOperandForIndex(index)));
    }

    /**
     * Return the next operand IN the next equation in map. Key is incremented automatically each time this method is called
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @return The current operands for the current equation
     */
    public String getNextOperandNextEquation () {

        //Always get the equation for the current key in equation map
        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        //If operand index and operand length match, we have retrieved all operands for an equation. We should then increment and get the latest equation
        if ((tte.getIndexPosition() + 1) == tte.getOperandsLength()) {

            this.currentEquationKey++;
            tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));
        }

        return (String.valueOf(zeroPrecision.format(tte.getOperandNextIndex())));
    }

    /**
     * Return the current operand index FOR the equation in map. Index is incremented automatically each time getNextOperandNextEquation is called
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @return The current operand index for the current equation
     */
    public int getOperandIndexCurrentEquation () {

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));

        return (tte.getIndexPosition());
    }

    /**
     * Return the length of the operand array FOR the equation in map
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @return The current operand array length
     */
    public int getOperandLengthCurrentEquation () {

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));

        return (tte.getOperandsLength());
    }

    /**
     * Return the calculated answer for a specific equation in map identified by key. Return as a String for easy rendering at the GUI side
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @param key Key to the map element that contains the desired Equation
     * @return    The calculated answer
     */
    public String getAnswerCalcForEquation (int key) {

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(key));
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        return (String.valueOf(zeroPrecision.format(tte.getAnswerCalc())));
    }

    /**
     * Return the calculated answer for the this equation in the map. Key is incremented by the getOperandsNextEquation so this must be called before getting the next set of operands
     * Return as a String for easy rendering at the GUI side
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @return The calculated answer
     */
    public String getAnswerCalcThisEquation () {

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        return (String.valueOf(zeroPrecision.format(tte.getAnswerCalc())));
    }

    /**
     * Verify user answer against calculated answer for a specific equation in map identified by key. Return a bool of true for match and false for mismatch
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @param key        Key to the map element that contains the desired Equation
     * @param answerUser User provided answer
     * @return           The calculated answer
     */
    public boolean verifyAnswerUserForEquation (
            String key,
            String answerUser
    ) {
        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(key);

        return (tte.verifyAnswerUser(Double.parseDouble(answerUser)));
    }

    /**
     * Verify user answer against calculated answer for a specific equation in map. Key is incremented by the getOperandsNextEquation so this must be called before getting the next set of operands
     * Return a bool of true for match and false for mismatch
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @param answerUser User provided answer
     * @return           The calculated answer
     */
    public boolean verifyAnswerUserThisEquation (String answerUser) {

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));

        return (tte.verifyAnswerUser(Double.parseDouble(answerUser)));
    }
}
