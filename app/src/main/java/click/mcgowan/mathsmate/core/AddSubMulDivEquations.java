package click.mcgowan.mathsmate.core;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generate and return a map of times table equations in accordance with provided parameters
 */
public class AddSubMulDivEquations extends Equations {

    /**
     * Create a new object for Addition, Subtraction, Multiplication and Division
     *
     * @param equationCount Number of equations to generate
     * @param operandCount  Number of operands in the equation
     * @param range         Largest number of the operand
     * @param precision     Precision of the operand (0 means no decimals)
     * @param negative      Are negative operands allowed?
     */
    public AddSubMulDivEquations(
            int equationCount,
            int operandCount,
            int range,
            int precision,
            boolean negative,
            char[] operators
    ) {
        super (
                equationCount,
                operandCount,
                range,
                precision,
                negative,
                operators
        );

        Log.i ("EQUATIONS_ASMD_INIT", "Equations Object Initialized");

        this.genEquations();
    }

    /**
     * Generate equations for Addition, Subtraction, Multiplication and Division
     *
     * Provided parameters will control the type of equations generated
     */
    protected void genEquations () {
        AddSubMulDivEquation prepEquation;

        for (int counter = 0; counter < this.equationCount; counter++) {

            prepEquation = new AddSubMulDivEquation(
                    this.operandCount,
                    this.range,
                    this.precision,
                    this.negative,
                    this.operators
            );

            equationMap.put(String.valueOf(counter), prepEquation);
        }

        Log.i("EQUATIONS_TT_GEN","Generated " + this.equationCount + " Equations");
    }

    /**
     * Return specific operand IN equation. Use key to locate the equation and index to locate the operand
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @param key   Key to the map element that contains the desired Equation
     * @param index Index to the operand IN the map element
     * @return      Operand as a String
     */
    public String getOperandForEquation (String key, int index) {

        AddSubMulDivEquation asmde;

        try {
            asmde = (AddSubMulDivEquation) this.equationMap.get(key);

            return (String.valueOf(asmde.getOperandForIndex(index)));

        } catch (Exception e) {
            return(e.getMessage());
        }
    }

    /**
     * Return specific operator IN equation. Use key to locate the equation and index to locate the operand
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @param key   Key to the map element that contains the desired Equation
     * @param index Index to the operand IN the map element
     * @return      Operator as a String
     */
    public String getOperatorForEquation (String key, int index) {
        AddSubMulDivEquation asmde;

        try {
            asmde = (AddSubMulDivEquation) this.equationMap.get(key);

            return (String.valueOf(asmde.getOperatorForIndex(index)));

        } catch (Exception e) {
            return(e.getMessage());
        }
    }

    /**
     * Return the next operand IN the next equation in map. Key is incremented automatically each time this method is called
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @return The current operands for the current equation
     */
    public String getNextOperandNextEquation () {

        //Always get the equation for the current key in equation map
        AddSubMulDivEquation asmde;
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        try {
            asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));

            //If operand index and operand length match, we have retrieved all operands for an equation. We should then increment and get the latest equation
            if ((asmde.getIndexPosition() + 1) == asmde.getOperandsLength()) {

                this.currentEquationKey++;
                asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));
            }

            return (String.valueOf(zeroPrecision.format(asmde.getOperandNextIndex())));

        } catch (Exception e) {
            return(e.getMessage());
        }
    }

    /**
     * Return the next operator IN the next equation in map. Key is incremented automatically each time this method is called
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @return The current operator for the current equation
     */
    public String getNextOperatorNextEquation () {

        //Always get the equation for the current key in equation map
        AddSubMulDivEquation asmde;
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        try {
            asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));

            //If operand index and operand length match, we have retrieved all operands for an equation. We should then increment and get the latest equation
            if ((asmde.getIndexPosition() + 1) == asmde.getOperandsLength()) {

                this.currentEquationKey++;
                asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));
            }

            return (String.valueOf(zeroPrecision.format(asmde.getOperatorNextIndex())));

        } catch (Exception e) {
            return(e.getMessage());
        }
    }

    /**
     * Return the current operand index FOR the equation in map. Index is incremented automatically each time getNextOperandNextEquation is called
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @return The current operand index for the current equation
     */
    public int getOperandIndexCurrentEquation () {

        AddSubMulDivEquation asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));

        return (asmde.getIndexPosition());
    }

    /**
     * Return the length of the operand array FOR the equation in map
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @return The current operand array length
     */
    public int getOperandLengthCurrentEquation () {

        AddSubMulDivEquation asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));

        return (asmde.getOperandsLength());
    }

    /**
     * Return the calculated answer for a specific equation in map identified by key. Return as a String for easy rendering at the GUI side
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @param key Key to the map element that contains the desired Equation
     * @return    The calculated answer
     */
    public String getAnswerCalcForEquation (int key) {

        AddSubMulDivEquation asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(key));
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        return (String.valueOf(zeroPrecision.format(asmde.getAnswerCalc())));
    }

    /**
     * Return the calculated answer for the this equation in the map. Key is incremented by the getOperandsNextEquation so this must be called before getting the next set of operands
     * Return as a String for easy rendering at the GUI side
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @return The calculated answer
     */
    public String getAnswerCalcThisEquation () {

        AddSubMulDivEquation asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        return (String.valueOf(zeroPrecision.format(asmde.getAnswerCalc())));
    }

    /**
     * Verify user answer against calculated answer for a specific equation in map identified by key. Return a bool of true for match and false for mismatch
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @param key        Key to the map element that contains the desired Equation
     * @param answerUser User provided answer
     * @return           True for match and false for mismatch
     */
    public boolean verifyAnswerUserForEquation (
            String key,
            String answerUser
    ) {
        AddSubMulDivEquation asmde = (AddSubMulDivEquation) this.equationMap.get(key);

        return (asmde.verifyAnswerUser(Double.parseDouble(answerUser)));
    }

    /**
     * Verify user answer against calculated answer for a specific equation in map. Key is incremented by the getOperandsNextEquation so this must be called before getting the next set of operands
     * Return a bool of true for match and false for mismatch
     *
     * Values in map will always be of type AddSubMulDivEquation
     *
     * @param answerUser User provided answer
     * @return           True for match and false for mismatch
     */
    public boolean verifyAnswerUserThisEquation (String answerUser) {

        AddSubMulDivEquation asmde = (AddSubMulDivEquation) this.equationMap.get(String.valueOf(this.currentEquationKey));

        return (asmde.verifyAnswerUser(Double.parseDouble(answerUser)));
    }
}
