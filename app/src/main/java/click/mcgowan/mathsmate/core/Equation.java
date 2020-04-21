package click.mcgowan.mathsmate.core;

/**
 * Generate and return an equation in accordance with provided parameters
 */
public abstract class Equation {

    //Parameters
    protected int operandCount; //Number of operands in the equation
    protected int range;        //Largest number of the operand
    protected int precision;    //Precision of the operand (0 means no decimals)
    protected boolean negative; //Are negative operands allowed?

    //Calculated values
    protected Operand[] operands; //Array of operands
    protected double answerCalc;  //The calculated answer
    protected int index=-1;       //Index of current operand. Since it's incremented immediately, we set to -1

    /**
     * Create a new Equation object. Set parameters from inheriting class
     *
     * All inheriting classes should have a step in the constructor to call genEquations
     *
     * @param operandCount Number of operands in the equation
     * @param range        Largest number of the operand
     * @param precision    Precision of the operand (0 means no decimals)
     * @param negative     Are negative operands allowed?
     */
    public Equation(
            int operandCount,
            int range,
            int precision,
            boolean negative
    ) {
        this.operandCount = operandCount;
        this.range = range;
        this.precision = precision;
        this.negative = negative;
    }

    /**
     * Generate the actual equation including the answer. This must be defined in the extended classes and take utilize either genOperands or setOperands
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
     * @param index index of the operand object that contains the operand value
     * @return The operand based on index
     */
    public double getOperandForIndex (int index) {

        return (this.operands[index].getOperand());
    }

    /**
     * Get next operand from array of operands. Index incremented automatically each time method called
     *
     * WARNING! No protection for out of bounds. That really should be added in at some point
     *
     * @return The operand based on the index
     */
    public double getOperandNextIndex () {

        //Increment the index for operand. We need to do this first as other methods may rely on this index AFTER this method was called
        this.index++;

        return (this.operands[this.index].getOperand());

    }

    /**
     * Get the total size of operands array
     *
     * NOTE: Technically we should already know this based on passed in parameters, but it's pretty bad practice to rely on that. This will give you the exact answer
     *
     * @return Size of the operands array
     */
    public int getOperandsLength () {

        return(this.operands.length);
    }

    /**
     * Get the current operand index position
     *
     * @return
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
