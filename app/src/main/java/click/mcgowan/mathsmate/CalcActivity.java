package click.mcgowan.mathsmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import click.mcgowan.mathsmate.core.Equations;

/**
 * Class for all common calculation activities. Uses the following resources
 *
 * * activity_calc.xml - the main calculation layout. Includes a flipper so the following internal resources can be enabled and disabled
 * * countdown.xml     - countdown layout before starting the render of questions
 * * calculator.xml    - calculator layout to render equations and provide a keypad for user input
 * * complete.xml      - complete layout displays when equations are complete and displays some statistics
 * * settings_*        - a placeholder for settings layouts. These will be unique to each Calculation Activity Type
 *
 * Extended classed must implement abstract class to do the following tasks which are unique to each Calculation Activity type
 *
 * * Set the header
 * * Read parameters for the calculation type and set defaults where they don't exist
 * * Create the desired equations object and pass in required parameters
 * * Render a settings form and facilitate saving of that form so parameters can be customised
 *
 * Specific details about the abstract classes are documented as per normal
 */

public abstract class CalcActivity extends AppCompatActivity {

    //Shared Preference Settings
    public static final String mathsMateSettings = "maths_mate_settings";

    //Equations
    protected Equations equations;
    protected int currentEquationKey; //May not be needed

    //Calculated Vars
    protected StringBuilder equationString;
    protected String equationAnswer = "?";
    protected boolean equationUserReset = true;
    protected boolean lockKeypad = true;

    /**
     * Render the Calculator Activity as per standard activity onCreate method
     *
     * Additional Actions:
     *
     * * Call setParameters method defined in inheriting class to load parameters or set defaults where they don't exist
     * * Call setCalcHeader method defined in inheriting class to set the activity header as desired
     *
     * @param savedInstanceState Required for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        //Call method to set parameters in extended class
        setParameters();

        //Placeholder for intent. We may not need it

        //Set the Header
        setCalcHeader((TextView)findViewById(R.id.tvCalcHeader));
    }

    /**
     * Triggered by btnBegin in countdown.xml
     *
     * Perform the following actions:
     *
     * * Start the countdown and render the countdown on the calculator.xml resource
     * * Flip view from countdown.xml to calculator.xml in activity_calc.xml so questions can be rendered and answered
     * * Call genNewEquations method defined in inheriting class to create desired equations object and pass in parameters as configured in inheriting class
     * * Call renderNewEquation method to render the first equation
     *
     * @param view Required for onClick
     */
    public void startCalculator (View view) {

        Button btnBegin = (Button)findViewById(R.id.btnBegin);
        final TextView tvRenderCount = (TextView)findViewById(R.id.tvRenderCount);

        //Hide button and enable textview
        btnBegin.setVisibility(View.GONE);
        tvRenderCount.setVisibility(View.VISIBLE);

        //Start countdown timer and render calculator.xml on countdown completion
        new CountDownTimer(3000, 1000) {

            //Display current interval in seconds
            public void onTick(long millisUntilFinished) {

                tvRenderCount.setText(String.valueOf((millisUntilFinished / 1000) + 1));
            }

            public void onFinish() {

                //Render the calculator view
                ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
                incBody.setDisplayedChild(1);

                //Generate equations
                genNewEquations();

                //Call private method to actually render the equation
                renderNewEquation();
            }
        }.start();
    }

    /**
     * Triggered by any of the keypad buttons in calculator.xml
     *
     * Perform the following actions:
     *
     * * Set a value based on the keypad that was clicked
     * * Where keypad is not locked, call renderAddThisEquation to display this value or answer this equation if enough input provided
     *
     * @param view Required for onClick
     */
    public void keypad (View view) {

        String answerCalc;
        String btnValue = "0";

        //Get ID and set value accordingly
        switch (view.getId()) {

            case R.id.btnOne:
                btnValue = "1";
                break;
            case R.id.btnTwo:
                btnValue = "2";
                break;
            case R.id.btnThree:
                btnValue = "3";
                break;
            case R.id.btnFour:
                btnValue = "4";
                break;
            case R.id.btnFive:
                btnValue = "5";
                break;
            case R.id.btnSix:
                btnValue = "6";
                break;
            case R.id.btnSeven:
                btnValue = "7";
                break;
            case R.id.btnEight:
                btnValue = "8";
                break;
            case R.id.btnNine:
                btnValue = "9";
                break;
            case R.id.btnZero:
                btnValue = "0";
                break;
        }

        //Do not process input if lockKeypad is true
        if (lockKeypad != true) {
            renderAddThisEquation(btnValue);
        }
    }

    /**
     * Triggered by btnExit in activity_calc.xml
     *
     * Exit view. User will be returned to the MainActivity
     *
     * @param view Required for onClick
     */
    public void exitActivity (View view) {

        finish();
    }

    /**
     * Triggered by btnCalcSettings
     *
     * Render a form using a resource of settings_* so parameters for the equation type can be customised. This must be defined in the extended classes
     *
     * @param view Required for onClick
     */
    abstract public void calcSettings (View view);

    /**
     * Triggered by btnCalcSaveSettings
     *
     * Saves parameters as configured in calcSettings so they can be loaded for later use. If using standard android shared preferences, see following definition
     *
     * public static final String mathsMateSettings = "maths_mate_settings";
     *
     * @param view Required for onClick
     */
    abstract public void saveSettings (View view);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //The following functions are not called directly by activity actions but by other functions in this class//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Called by startCalculator or renderAddThisEquation methods
     *
     * Perform the following actions:
     *
     * * Loop through each operand in the current equation and build the equation into a string
     * * Render equation in the calculator.xml resource with ? as the placeholder for the answer
     * * Set flags to unlock the keypad and set expectation that any input from keypad is for a brand new equation
     *
     * NOTE: each call to getNextOperandNextEquation increases the index for the operands within an equation and key for equation in equations so indexes do not need to be managed in this class itself
     */
    private void renderNewEquation () {

        //Set some vars needed to render equation
        String operandAsString;
        String operatorAsString = "?";
        TextView tvEquation = (TextView)findViewById(R.id.tvEquation);
        TextView tvStatus = (TextView)findViewById(R.id.tvStatus);

        //Reset equation string at the start of every new render equation
        equationString = new StringBuilder();

        //Loop until we have retrieved all operands for the equation. Done once current equation index == total number of operands
        do {

            //Get the operand
            operandAsString = equations.getNextOperandNextEquation();

            //Get the operator and set the actual Unicode operator value
            switch (equations.getOperatorForEquation(equations.getCurrentEquationKey(), equations.getOperandIndexCurrentEquation())) {
                case "+" : operatorAsString = getString(R.string.addition_symbol);
                break;
                case "-" : operatorAsString = getString(R.string.subtraction_symbol);
                break;
                case "*" : operatorAsString = getString(R.string.multiplication_symbol);
                break;
                case "/" : operatorAsString = getString(R.string.division_symbol);
            }

            //If current index + 1 == total number of operands, this is out last loop and we should render the = symbol and discard the final operator
            if ((equations.getOperandIndexCurrentEquation() + 1) == equations.getOperandLengthCurrentEquation()) {
                equationString.append(operandAsString);
                equationString.append(getString(R.string.equals_symbol));
            }
            //Otherwise, we still have portions of the equations to render and we need to include the operator
            else {
                equationString.append(operandAsString);
                equationString.append(operatorAsString);
            }
        } while ((equations.getOperandIndexCurrentEquation() + 1) < equations.getOperandLengthCurrentEquation());

        equationAnswer = "?";

        //Render equation and status
        tvEquation.setText(getString(R.string.equation_mask, equationString.toString(), equationAnswer));
        tvStatus.setText(getString(R.string.status_input));
        tvStatus.setTextColor(getColor(R.color.colorNeutral));

        //Set equationUserRest flag to true telling renderAddThisEquation to expect brand new input
        equationUserReset = true;
        //Unlock the keypad so it can call renderAddThsEquation
        lockKeypad = false;
    }

    /**
     * Called by the keypad method
     *
     * Perform the following actions:
     *
     * * Render the keypad entry to the calculator.xml alongside the equation
     * * Keep accepting keypad entries and appending to the existing keypad entries until total number of keypad entries aligns with length of calculated answer
     * * Once number of keypad entries matches length of calculated answer:
     * * Success:
     * * * Lock keypad and display render success message for 500 milliseconds
     * * * Call renderNewEquation where there are still equations to be answered
     * * * Call renderEquationsComplete where there are no more equations to answer
     * * Failure:
     * * * Display failure and reset equation answer so user can attempt again
     */
    private void renderAddThisEquation (String btnValue) {

        TextView tvEquation = (TextView)findViewById(R.id.tvEquation);
        TextView tvStatus = (TextView)findViewById(R.id.tvStatus);

        //Always set status to input when this method is called
        tvStatus.setText(getString(R.string.status_input));
        tvStatus.setTextColor(getColor(R.color.colorNeutral));

        //If equationUserReset is set to true, this is the first entry
        if (equationUserReset == true) {

            //Set first entry in equation answer
            equationAnswer = btnValue;
            tvEquation.setText(getString(R.string.equation_mask, equationString, equationAnswer));

            //Set equationUserReset to false so if more entries are required, they will be appended and not replace
            equationUserReset = false;
        }
        //If this method was called again and equationUserReset is still false, then additional entries are needed in equation answer. Append rather than replace
        else {

            //Append entry in equation answer
            equationAnswer = equationAnswer + btnValue;
            tvEquation.setText(getString(R.string.equation_mask, equationString, equationAnswer));
        }

        //If length of equationAnswer and calculated answer match, where ready to actually check the answer
        if (equationAnswer.length() == equations.getAnswerCalcThisEquation().length()) {

            //If answer is correct, perform necessary steps to inform the user and prepare a new equation
            if (equations.verifyAnswerUserThisEquation(equationAnswer) == true) {

                //Lock the keypad so new input cant be added while success is been rendered
                lockKeypad = true;

                //Render success msg
                tvStatus.setText(getString(R.string.status_pass));
                tvStatus.setTextColor(getColor(R.color.colorPass));

                //Set a quick timer so users can salivate in there success!
                new CountDownTimer(500, 500) {

                    //No need for any action
                    public void onTick(long millisUntilFinished) { }

                    //On finishing, check the current equation index for next actions
                    public void onFinish() {

                        //If index is less than equation map size, move to next equation
                        if ((equations.getCurrentEquationKey() + 1) < equations.getEquationMapSize()) {
                            renderNewEquation();
                        }
                        //Otherwise, we render the completed page
                        else {
                            renderEquationsComplete();
                        }
                    }
                }.start();
            }
            //If answer is incorrect, inform user and set flags so further input will replace previous input. We do not change the question
            else {
                tvStatus.setText(getString(R.string.status_fail));
                tvStatus.setTextColor(getColor(R.color.colorFail));

                //Set equationUserReset to true so next input erases previous answer
                equationUserReset = true;
            }
        }
    }

    /**
     * Called by renderAddThisEquation
     *
     * Render the complete.xml resource along with some statistics on your effort
     */
    private void renderEquationsComplete () {

        //StringBuilder completeMeta = new StringBuilder();
        TextView completeIn = (TextView)findViewById(R.id.tvCompleteIn);
        //completeMeta

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(2);
        completeIn.setText(getString(R.string.time_taken, equations.getTimeTaken()));
    }

    /**
     * Called by onCreate method
     *
     * Load parameters or set defaults if they don't exist so they can be provided to the desired Equations class. If using standard android shared preferences, see following definition
     *
     * public static final String mathsMateSettings = "maths_mate_settings";
     *
     * Must be set by inheriting class as number and type of values will vary depending on equation type
     */
    abstract void setParameters ();

    /**
     * Called by onCreate method
     *
     * Set the desired header to be displayed in teh calculator.xml resource
     *
     * Must be set by the inheriting class to represent the type of equations we are creating
     *
     * @param calcHeader TextView for the calculation header
     */
    abstract void setCalcHeader (TextView calcHeader);

    /**
     * Called by startCalculator
     *
     * Create desired Equations object and pass in required parameters as set by setParameters
     *
     * Must be created by the inheriting class as each Equations class is different and has different parameter requirements
     */
    abstract void genNewEquations ();
}
