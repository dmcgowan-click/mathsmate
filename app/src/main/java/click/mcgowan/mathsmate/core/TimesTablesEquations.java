package click.mcgowan.mathsmate.core;

import android.util.Log;

import java.text.DecimalFormat;

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
     * Exactly 144 questions from 1x1 to 12x12
     */
    protected void genEquations () {
        TimesTablesEquation prepEquation;
        int index = 0;
        int operanda, operandb;

        //Loop exactly 12 times for x axis
        for (int countx=0; countx < this.range; countx++) {

            //Loop exactly 12 times for y axis
            for (int county=0; county < this.range; county++) {

                //If random was true, we use some more complex logic to create the operands
                if (this.random == true) {
                    operanda = countx + 1;
                    operandb = county + 1;
                }
                //If random was false, we just use the countx and county to set the operands
                else {
                    operanda = countx + 1;
                    operandb = county + 1;
                }

                //Create new TimesTableEquation object and generate the equation
                prepEquation = new TimesTablesEquation(
                        operanda,
                        operandb
                );
                prepEquation.genEquation();

                //Add new equation to map and increment index
                equationMap.put(String.valueOf(index),prepEquation);
                index++;
            }
        }

        Log.i("EQUATIONS_TT_GEN","Generated " + String.valueOf(index) + " Equations");
    }

    /**
     * Return the operands for a specific equation in map identified by key. Return as a String array for easy rendering at the GUI side
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @param key Key to the map element that contains the desired Equation
     * @return    An array with the operands for the equation
     */
    public String[] getOperandsForEquation (String key) {

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(key);
        Operand operand[] = tte.getOperands();
        String operandAsString[] = new String[operand.length];

        //We want to extract the values from the operand object and return them as a simple String array for easy rendering at the GUI side
        for (int counter = 0; counter < operand.length; counter++) {

            operandAsString[counter] = String.valueOf(operand[counter].getOperand());
        }

        return (operandAsString);
    }

    /**
     * Return the operands for the next equation in map. Key is incremented automatically each time this method is called. Return as a String array for easy rendering at the GUI side
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @return An array with the operands for the equation
     */
    public String[] getOperandsNextEquation () {

        //Increment the current equation. We need to do this first as other methods depend on this index
        this.currentEquationIndex++;

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationIndex));
        Operand operand[] = tte.getOperands();
        String operandAsString[] = new String[operand.length];
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        //We want to extract the values from the operand object and return them as a simple String array for easy rendering at the GUI side
        for (int counter = 0; counter < operand.length; counter++) {

            operandAsString[counter] = String.valueOf(zeroPrecision.format(operand[counter].getOperand()));
        }

        return (operandAsString);
    }

    /**
     * Return the calculated answer for a specific equation in map identified by key. Return as a String for easy rendering at the GUI side
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @param key Key to the map element that contains the desired Equation
     * @return    The calculated answer
     */
    public String getAnswerCalcForEquation (String key) {

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(key);
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

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationIndex));
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

        TimesTablesEquation tte = (TimesTablesEquation) this.equationMap.get(String.valueOf(this.currentEquationIndex));

        return (tte.verifyAnswerUser(Double.parseDouble(answerUser)));
    }
}
