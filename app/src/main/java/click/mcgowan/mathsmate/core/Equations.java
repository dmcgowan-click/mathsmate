package click.mcgowan.mathsmate.core;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generate and return a map of equations in accordance with provided parameters
 *
 * Not happy about this design but I lack enough understanding of Java to do it better
 * Ideally, I want this class to be able to retrieve objects
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
    protected Date startDate;                                              //Timestamp of when the equations start
    protected HashMap<String,Object> equationMap = new LinkedHashMap<String,Object>(); //A map of all equations. Using map as eventually, we want to be able to mix and match different types of equations
    protected int currentEquationKey = 0;                                  //Index of current equation
    protected int operandIndexLength;                                      //Size of the operand array IN the equation MAY NOT NEED

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
        this.startDate = new Date ();
    }

    /**
     * Create a new equations object. Set all parameters based on input from inheriting class
     *
     * Inheriting constructor should call the genEquations method to actually generate the equations
     *
     * Exclusion: Under this constructor equation count is calculated by the ranges. This is applicable for equations where every possible combination results in an equation (e.g. timestables)
     *
     * NOTE: May not be needed. Look at removing
     * @param operandCount  Largest number of the operand
     * @param range         Precision of the operand (0 means no decimals)
     * @param precision     Precision of the operand (0 means no decimals)
     * @param negative      Are negative operands allowed?
     */
    public Equations (
            int operandCount,
            int range,
            int precision,
            boolean negative
    ) {
        this.equationCount = range * range;
        this.operandCount = operandCount;
        this.range = range;
        this.precision = precision;
        this.negative = negative;
        this.startDate = new Date ();
    }

    /**
     * Generate the actual equations. Must be defined in the extended classes
     *
     * genEquations is protected and called directly by the inheriting constructor
     */
    abstract protected void genEquations ();

    /**
     * Return time taken based on current time when this method was called - time recorded when object was created
     *
     * @return Time taken as a string
     */
    public String getTimeTaken () {

        Date currentDate = new Date ();
        Long dateDifference = currentDate.getTime() - this.startDate.getTime();

        return (String.valueOf(dateDifference));
    }

    /**
     * Return the current index of the equations map. Needed for GUI to know when to render the finished page
     *
     * @return Current equation index
     */
    public int getCurrentEquationKey () {
        return (this.currentEquationKey);
    }

    /**
     * Return the size of the equation map. Needed for GUI to know when to render the finished page
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
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @param key   Key to the map element that contains the desired Equation
     * @param index Index to the operand IN the map element
     * @return      Operand as a String
     */
    abstract public String getOperandForEquation (String key, int index);

    /**
     * Return specific operator IN equation. Use key to locate the equation and index to locate the operator
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @param key   Key to the map element that contains the desired Equation
     * @param index Index to the operator IN the map element
     * @return      Operator as a String
     */
    abstract public String getOperatorForEquation (String key, int index);

    /**
     * Return the next operand IN the next equation in map. Key is incremented automatically each time this method is called
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @return Operand as a String
     */
    abstract public String getNextOperandNextEquation ();

    /**
     * Return the next operator IN the next equation in map. Key is incremented automatically each time this method is called
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @return Operator as a String
     */
    abstract public String getNextOperatorNextEquation ();

    /**
     * Return the current operand index FOR the equation in map. Index is incremented automatically each time getNextOperandNextEquation is called
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @return The current operand index for the current equation
     */
    abstract public int getOperandIndexCurrentEquation ();

    /**
     * Return the length of the operand array FOR the equation in map
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @return The current operand array length
     */
    abstract public int getOperandLengthCurrentEquation ();

    /**
     * Return the calculated answer for a specific equation in map identified by key. Return as a String for easy rendering at the GUI side
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @param key Key to the map element that contains the desired Equation
     * @return    The calculated answer
     */
    abstract public String getAnswerCalcForEquation (int key);

    /**
     * Return the calculated answer for the this equation in the map. Key is incremented by the getOperandsNextEquation so this must be called before getting the next set of operands
     * Return as a String for easy rendering at the GUI side
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @return The calculated answer
     */
    abstract public String getAnswerCalcThisEquation ();

    /**
     * Verify user answer against calculated answer for a specific equation in map identified by key. Return a bool of true for match and false for mismatch
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @param key        Key to the map element that contains the desired Equation
     * @param answerUser User provided answer
     * @return           True for match and false for mismatch
     */
    abstract public boolean verifyAnswerUserForEquation (
            String key,
            String answerUser
    );

    /**
     * Verify user answer against calculated answer for a specific equation in map. Key is incremented by the getOperandsNextEquation so this must be called before getting the next set of operands
     * Return a bool of true for match and false for mismatch
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @param answerUser User provided answer
     * @return           True for match and false for mismatch
     */
    abstract public boolean verifyAnswerUserThisEquation (String answerUser);
}