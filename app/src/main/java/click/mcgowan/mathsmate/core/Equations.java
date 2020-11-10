package click.mcgowan.mathsmate.core;

import android.util.Log;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Generate and return equations in accordance with provided parameters
 *
 * Extended class must define a genEquations method to actually produce the equations
 */
public abstract class Equations {

    //Parameters
    protected int equationCount; //Number of equations
    protected int operandCount;  //Number of operands in the equation
    protected int range;         //Largest number of the operand
    protected int precision;     //Precision of the operand (0 means no decimals)
    protected boolean negative;  //Are negative operands allowed?
    protected char[] operators;  //Array of operators permitted for equations. Operators are randomly selected based on what is permitted

    //Calculated Values
    protected LocalTime startTime;                                         //Timestamp of when the equations start
    protected HashMap<Integer,Object> equationMap = new LinkedHashMap<Integer, Object>(); //A map of all equations. Using map to mix different equation types, and provide an easy way to random order equations
    protected int currentEquationKey = 0;                                  //Index of current equation

    /**
     * Create a new equations object. Set all parameters based on input from inheriting class
     *
     * Inheriting constructor should call the genEquations method to actually generate the equations
     *
     * @param equationCount Number of equations
     * @param operandCount  Number of operands in the equation
     * @param range         Largest number of the operand
     * @param precision     Precision of the operand (0 means no decimals)
     * @param negative      Are negative operands allowed?
     */
    public Equations (
            int equationCount,
            int operandCount,
            int range,
            int precision,
            boolean negative,
            char[] operators
    ) {
        this.equationCount = equationCount;
        this.operandCount = operandCount;
        this.range = range;
        this.precision = precision;
        this.negative = negative;
        this.operators = operators;
        this.startTime = LocalTime.now();
    }

    /**
     * Generate the actual equations. Must be defined in the extended classes
     *
     * genEquations is protected and called directly by the inheriting constructor
     */
    abstract protected void genEquations ();

    /**
     * Return the current index of the equations map.
     *
     * Typically used in conjunction with getEquationIndex to locate an operand or operator by the Activity class to render equations correctly
     *
     * @return Current equation index
     */
    public int getCurrentEquationKey () {
        return (this.currentEquationKey);
    }

    /**
     * Return the size of the equation map.
     *
     * Typically used by Activity class to render equations correctly
     *
     * NOTE: Reason we do this is some equation types (times tables), the number of equations is pre determined and not a parameter passed in
     *
     * @return Size of equation map
     */
    public int getEquationMapSize () {
        return (this.equationMap.size());
    }

    /**
     * Return specific operand IN equation. Use key to locate the equation and index to locate the operand
     *
     * All objects upcast to Equation class
     *
     * @param key   Key to the map element that contains the desired Equation
     * @param index Index to the operand IN the map element
     * @return      Operand as a String
     */
    public String getOperandForEquation (Integer key, int index) {

        Equation equation;

        try {
            equation = (Equation) this.equationMap.get(key);

            return (String.valueOf(equation.getOperandForIndex(index)));
        } catch (Exception e) {
            return(e.getMessage());
        }
    }

    /**
     * Return specific operator IN equation. Use key to locate the equation and index to locate the operand
     *
     * All objects upcast to Equation class
     *
     * @param key   Key to the map element that contains the desired Equation
     * @param index Index to the operand IN the map element
     * @return      Operator as a String
     */
    public String getOperatorForEquation (Integer key, int index) {

        Equation equation;

        try {
            equation = (Equation) this.equationMap.get(key);

            return (String.valueOf(equation.getOperatorForIndex(index)));

        } catch (Exception e) {
            return(e.getMessage());
        }
    }

    /**
     * Return the next operand IN the next equation in the map.
     *
     * Each time this method is called, the equation index is incremented and the operand for that index is retrieved.
     * When this method is called and we have already reached the last operand for an equation, the key equations key is incremented and we start reading from a new equation
     *
     * All objects upcast to Equation class
     *
     * @return The current operands for the current equation as a string
     */
    public String getNextOperandNextEquation () {

        //Always get the equation for the current key in equation map
        Equation equation;
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        try {
            equation = (Equation) this.equationMap.get(this.currentEquationKey);

            //If operand index and operand length match, we have retrieved all operands for an equation. We should then increment and get the latest equation
            if ((equation.getIndexPosition() + 1) == equation.getOperandsLength()) {

                this.currentEquationKey++;
                equation = (Equation) this.equationMap.get(this.currentEquationKey);
            }

            return (String.valueOf(zeroPrecision.format(equation.getOperandNextIndex())));

        } catch (Exception e) {
            return(e.getMessage());
        }
    }

    /**
     * Return the next operator IN the next equation in the map
     *
     * Each time this method is called, the equation index is incremented and the operand for that index is retrieved.
     * When this method is called and we have already reached the last operand for an equation, the key equations key is incremented and we start reading from a new equation
     *
     * @return The current operator for the current equation
     */
    public String getNextOperatorNextEquation () {

        //Always get the equation for the current key in equation map
        Equation equation;
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        try {
            equation = (Equation) this.equationMap.get(this.currentEquationKey);

            //If operand index and operand length match, we have retrieved all operands for an equation. We should then increment and get the latest equation
            if ((equation.getIndexPosition() + 1) == equation.getOperandsLength()) {

                this.currentEquationKey++;
                equation = (Equation) this.equationMap.get(this.currentEquationKey);
            }

            return (String.valueOf(zeroPrecision.format(equation.getOperatorNextIndex())));

        } catch (Exception e) {
            return(e.getMessage());
        }
    }

    /**
     * Return the current operand index FOR the equation in map.
     *
     * Index is incremented automatically each time getNextOperandNextEquation OR getNextOperatorNextEquation is called
     *
     * All objects upcast to Equation class
     *
     * @return The current operand index for the current equation
     */
    public int getOperandIndexCurrentEquation () {

        Equation equation = (Equation) this.equationMap.get(this.currentEquationKey);

        return (equation.getIndexPosition());
    }

    /**
     * Return the length of the operand array FOR the equation in map
     *
     * All objects upcast to Equation class
     *
     * @return The current operand array length
     */
    public int getOperandLengthCurrentEquation () {

        Equation equation = (Equation) this.equationMap.get(this.currentEquationKey);

        return (equation.getOperandsLength());
    }

    /**
     * Return the calculated answer for a specific equation in map identified by key. Return as a String for easy rendering at the GUI side
     *
     * All objects upcast to Equation class
     *
     * @param key Key to the map element that contains the desired Equation
     * @return    The calculated answer
     */
    public String getAnswerCalcForEquation (Integer key) {

        Equation equation = (Equation) this.equationMap.get(key);
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        return (String.valueOf(zeroPrecision.format(equation.getAnswerCalc())));
    }

    /**
     * Return the calculated answer for the this equation in the map. Return as a String for easy rendering at the GUI side
     *
     * Key is incremented by the getOperandsNextEquation OR getOperatorNextEquation so this must be called before getting the next set of operands
     *
     * All objects upcast to Equation class
     *
     * @return The calculated answer
     */
    public String getAnswerCalcThisEquation () {

        Equation equation = (Equation) this.equationMap.get(this.currentEquationKey);
        DecimalFormat zeroPrecision = new DecimalFormat("#");

        return (String.valueOf(zeroPrecision.format(equation.getAnswerCalc())));
    }

    /**
     * Verify user answer against calculated answer for a specific equation in map identified by key. Return a bool of true for match and false for mismatch
     *
     * All objects upcast to Equation class
     *
     * @param key        Key to the map element that contains the desired Equation
     * @param answerUser User provided answer
     * @return           True for match and false for mismatch
     */
    public boolean verifyAnswerUserForEquation (
            Integer key,
            String answerUser
    ) {
        Equation equation = (Equation) this.equationMap.get(key);

        return (equation.verifyAnswerUser(Double.parseDouble(answerUser)));
    }

    /**
     * Verify user answer against calculated answer for a specific equation in map. Key is incremented by the getOperandsNextEquation so this must be called before getting the next set of operands
     * Return a bool of true for match and false for mismatch
     *
     * Values in map will always be of type TimesTablesEquation
     *
     * @param answerUser User provided answer
     * @return           True for match and false for mismatch
     */
    public boolean verifyAnswerUserThisEquation (String answerUser) {

        Equation equation = (Equation) this.equationMap.get(this.currentEquationKey);

        return (equation.verifyAnswerUser(Double.parseDouble(answerUser)));
    }

    /**
     * Return time taken based on current time when this method was called - time recorded when object was created
     *
     * @return Time taken as a string
     */
    public String getTimeTaken () {

        StringBuilder finishedIn = new StringBuilder();

        LocalTime finishTime = LocalTime.now();
        Duration timeDiff = Duration.between(this.startTime, finishTime);

        //Eventually add logic so if time runs over a 60 seconds, time displayed in minutes
        finishedIn.append(String.valueOf(timeDiff.toMillis() / 1000));
        finishedIn.append(" Seconds");

        return (finishedIn.toString());
    }
}