package click.mcgowan.mathsmate.core;

/**
 * Generate and return an equation in accordance with provided parameters
 *
 * Extended class must define a getEquation method to actually produce the equation
 */
public abstract class Equation {

    //Parameters
    protected int operandCount; //Number of operands in the equation
    protected int range;        //Largest number of the operand
    protected int precision;    //Precision of the operand (0 means no decimals)
    protected boolean negative; //Are negative operands allowed?
    protected char[] operators; //Array of operators permitted for equation. Operators are randomly selected based on what is permitted

    //Calculated values
    protected Operand[] operands; //Array of operands
    protected double answerCalc;  //The calculated answer
    protected int index=-1;       //Index of current operand. Since it's incremented immediately, we set to -1

    /**
     * Create a new Equation object. Set parameters from inheriting class
     *
     * Inheriting constructor should call the genEquation method to actually generate the equations
     *
     * @param operandCount Number of operands in the equation
     * @param range        Largest number of the operand
     * @param precision    Precision of the operand (0 means no decimals)
     * @param negative     Are negative operands allowed?
     * @param operators    Array of operators permitted for equation. Operators are randomly selected based on what is permitted during generation of equation
     */
    public Equation(
            int operandCount,
            int range,
            int precision,
            boolean negative,
            char[] operators
    ) {
        this.operandCount = operandCount;
        this.range = range;
        this.precision = precision;
        this.negative = negative;
        this.operators = operators;
    }

    /**
     * Generate the actual equation including the answer. This must be defined in the extended classes
     */
    abstract protected void genEquation ();

    /**
     * Get the user provided answer, check against the calculated answer. If correct, return true, otherwise return false
     *
     * @param answerUser User provided answer
     * @return           True for match, False for mismatch
     */
    protected boolean verifyAnswerUser(double answerUser) {
        if (answerUser == this.answerCalc) {
            return (true);
        }
        else {
            return (false);
        }
    }

    /**
     * Get operand from array of operands based on provided index
     *
     * NOTE: Out of bounds protection should reside in calling method
     *
     * @param index index of the operand object that contains the operand value
     * @return      The operand based on index
     */
    public double getOperandForIndex (int index) {

        return (this.operands[index].getOperand());
    }

    /**
     * Get operator from array of operands based on provided index
     *
     * NOTE: Out of bounds protection should reside in calling method
     *
     * @param index index of the operand object that contains the operator value
     * @return      The operator based on index
     */
    public char getOperatorForIndex (int index) {

        return (this.operands[index].getOperator());
    }

    /**
     * Get next operand from array of operands. Index incremented automatically each time method called
     *
     * NOTE: Out of bounds protection should reside in calling method
     *
     * @return The operand based on the index
     */
    public double getOperandNextIndex () {

        //Increment the index for operand. We need to do this first as other methods may rely on this index AFTER this method was called
        this.index++;

        return (this.operands[this.index].getOperand());
    }

    /**
     * Get next operator from array of operands. Index incremented automatically each time method called
     *
     * NOTE: Out of bounds protection should reside in calling method
     *
     * @return The operator based on the index
     */
    public char getOperatorNextIndex () {

        //Increment the index for operand. We need to do this first as other methods may rely on this index AFTER this method was called
        this.index++;

        return (this.operands[this.index].getOperator());
    }

    /**
     * Get the total size of operands array
     *
     * NOTE: Out of bounds protection should reside in calling method
     *
     * @return Size of the operands array
     */
    public int getOperandsLength () {

        return(this.operands.length);
    }

    /**
     * Get the current operand index position. This is typically used as a reference for the *ForIndex methods
     *
     * @return The current operand index position
     */
    public int getIndexPosition () {

        return (this.index);
    }

    /**
     * Get calculated answer
     *
     * @return The calculated answer
     */
    public double getAnswerCalc () {
        return (this.answerCalc);
    }
}
