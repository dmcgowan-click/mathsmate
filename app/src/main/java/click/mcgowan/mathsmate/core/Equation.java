package click.mcgowan.mathsmate.core;

/**
 * Generate and return an equation in accordance with provided parameters
 */
public abstract class Equation {

    //Parameters
    private int operandCount; //Number of operands in the equation
    private int range;        //Largest number of the operand
    private int precision;    //Precision of the operand (0 means no decimals)
    private boolean negative; //Are negative operands allowed?

    //Calculated values
    Operand[] operands;
    double answerCalc;

    /**
     * Create a new Equation object. Set parameters
     *
     * @param operandCount Number of operands in the equation
     * @param range        Largest number of the operand
     * @param precision    Precision of the operand (0 means no decimals)
     * @param negative     Are negative operands allowed?
     */
    Equation(
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
     * Generate all operands for the equation. Internal and to be called by the genEquation method if applicable for this type of equation
     */
    protected void genOperands () {

        //Create an operand based on operandCount
        this.operands = new Operand[this.operandCount];

        for (int counter=0; counter < this.operands.length; counter++) {

            this.operands[counter] = new Operand(
                    this.range,
                    this.precision,
                    this.negative
            );

            this.operands[counter].genOperand();
        }
    }

    /**
     * Set all operands for the equation. Internal and to be called by the genEquation method if applicable for this type of equation
     *
     * Number of elements in operands and size of operandCount must match or this method will die!
     *
     * @param operands An array of the operands you want to set
     */
    void setOperands(double[] operands) {

        //Set operands for the size of operandCount. operands must match in length
        this.operands = new Operand[this.operandCount];

        for (int counter=0; counter < this.operands.length; counter++) {

            this.operands[counter] = new Operand(
                    this.range,
                    this.precision,
                    this.negative
            );

            this.operands[counter].setOperand(operands[counter]);
        }
    }

    /**
     * Generate the actual equation. This must be defined in the extended classes and take utilize either genOperands or setOperands
     */
    abstract public void genEquation ();

    /**
     * Get the user provided answer, check against the calculated answer. If correct, return true, otherwise return false
     *
     * @param answerUser User provided answer
     * @return           True for match, False for mismatch
     */
    public boolean verifyAnswerUser (double answerUser) {
        if (answerUser == this.answerCalc) {
            return (true);
        }
        else {
            return (false);
        }
    }

    /**
     * Get the array of operands. Required for rendering the answer correctly
     *
     * @return Array of the operands in this equation
     */
    public Operand[] getOperands () {
        return (this.operands);
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
