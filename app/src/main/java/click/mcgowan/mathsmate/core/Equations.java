package click.mcgowan.mathsmate.core;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generate and return a map of equations in accordance with provided parameters
 */
public abstract class Equations {

    //Parameters
    protected int equationCount; //Number of equations
    protected int operandCount;  //Number of operands in the equation
    protected int range;         //Largest number of the operand
    protected int precision;     //Precision of the operand (0 means no decimals)
    protected boolean negative;  //Are negative operands allowed?

    //Calculated Values
    protected Date startDate;                                              //Timestamp of when the equations start
    protected HashMap<String,Object> equationMap = new LinkedHashMap<String,Object>(); //A map of all equations. Using map as eventually, we want to be able to mix and match different types of equations
    protected int currentEquationIndex = -1;                               //Index of current equation. Since it's incremented immediately, we set to -1

    /**
     * Create a new equations object. Set all parameters based on input from inheriting class
     *
     * @param equationCount Number of equations
     * @param operandCount  Largest number of the operand
     * @param range         Precision of the operand (0 means no decimals)
     * @param precision     Precision of the operand (0 means no decimals)
     * @param negative      Are negative operands allowed?
     */
    public Equations (
            int equationCount,
            int operandCount,
            int range,
            int precision,
            boolean negative
    ) {
        this.equationCount = equationCount;
        this.operandCount = operandCount;
        this.range = range;
        this.precision = precision;
        this.negative = negative;
        this.startDate = new Date ();
    }

    /**
     * Create a new equations object. Set all parameters based on input from inheriting class
     *
     * Exclusion: Under this constructor equation count is calculated by the ranges. This is applicable for equations where every possible combination results in an equation (e.g. timestables)
     *
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
     * Return the start date for these equations
     *
     * @return Equation Start Date
     */
    public Date getStartDate () {
        return (this.startDate);
    }

    /**
     * Return the current index of the equations map. Needed for GUI to know when to render the finished page
     *
     * @return Current equation index
     */
    public int getCurrentEquationIndex () {
        return (this.currentEquationIndex);
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
     * Return the operands for a specific equation in map identified by key. Return as a String array for easy rendering at the GUI side
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @param key Key to the map element that contains the desired Equation
     * @return    An array with the operands for the equation
     */
    abstract public String[] getOperandsForEquation (String key);

    /**
     * Return the operands for the next equation in the map. Key is incremented automatically each time this method is called. Return as a String array for easy rendering at the GUI side
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @return An array with the operands for the equation
     */
    abstract public String[] getOperandsNextEquation ();

    /**
     * Return the calculated answer for a specific equation in map identified by key. Return as a String for easy rendering at the GUI side
     *
     * This is abstract as extended classes may implement a mixture of different equation types, and therefore logic to handle it must be defined there
     *
     * @param key Key to the map element that contains the desired Equation
     * @return    The calculated answer
     */
    abstract public String getAnswerCalcForEquation (String key);

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
     * @return           The calculated answer
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
     * @return           The calculated answer
     */
    abstract public boolean verifyAnswerUserThisEquation (String answerUser);
}