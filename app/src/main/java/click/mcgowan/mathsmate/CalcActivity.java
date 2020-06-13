package click.mcgowan.mathsmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import click.mcgowan.mathsmate.core.Equations;
import click.mcgowan.mathsmate.core.TimesTablesEquation;
import click.mcgowan.mathsmate.core.TimesTablesEquations;

/**
 * Class for Times Table Calculator Activity
 *
 * Times Table Calculator Activity provides the interactive screen for all calculations
 * If this works, all custom activities use activity_main.xml, activity_calc.xml and in turn, countdown.xml and calculator.xml
 */

public abstract class CalcActivity extends AppCompatActivity {

    //Shared Preference Settings
    public static final String mathsMateSettings = "maths_mate_settings";

    //Equations
    protected Equations equations;
    protected int currentEquationKey;

    //Calculated Vars
    protected StringBuilder equationString;
    protected String equationAnswer = "?";
    protected boolean equationUserReset = true;
    protected boolean lockKeypad = true;

    /**
     * Render the Calculator Activity
     *
     * Additional Actions:
     *
     * Set the header based on activity type
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        //Read shared settings or set defaults if they don't exist
        SharedPreferences spr = getSharedPreferences(mathsMateSettings,0);

        //Call method to set parameters in extended class
        setParameters(spr);

        //Placeholder for intent. We may not need it

        //Set the Header
        setCalcHeader((TextView)findViewById(R.id.tvCalcHeader));
    }

    /**
     * Triggered by btnBegin in countdown.xml and will perform the following:
     *
     * * Start the countdown
     * * Flip view from countdown.xml to calculator.xml in activity_calc.xml so questions can be rendered and answered
     * * Create TimesTableEquations object and generate new equations
     *
     * @param view
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
     * Triggered by any of the keypad buttons in calculator.xml and will perform the following
     *
     * Set a value based on button ID
     *
     * @param view
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
     * @param view
     */
    public void exitActivity (View view) {

        finish();
    }

    /**
     * Triggered by btnCalcSettings
     *
     * Define a form that will render settings form unique to the equation type
     *
     * @param view
     */
    abstract public void calcSettings (View view);

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //The following functions are not called directly by buttons but by other functions in this class//
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Render new equation based on TimesTableEquation object
     */
    private void renderNewEquation () {

        //Set some vars needed to render equation
        String operandAsString;
        String operatorAsString = "?";
        TextView tvEquation = (TextView)findViewById(R.id.tvEquation);
        TextView tvStatus = (TextView)findViewById(R.id.tvStatus);

        //Reset equation string at the start of every new render equation
        equationString = new StringBuilder();

        //Loop until we have retrieved all operands for the equation
        do {

            //Get the operand and operator
            operandAsString = equations.getNextOperandNextEquation();

            //Set the actual Unicode operator value
            switch (equations.getOperatorForEquation(String.valueOf(equations.getCurrentEquationKey()), equations.getOperandIndexCurrentEquation())) {
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
     * Render use inputted answer alongside equation.
     *
     * In event answer is wrong, render error msg and clear on next input so new answer can be validated
     *
     * In event answer is correct, render success msg and call next equation. If no more equations call equations completed
     *
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
     * Render a finish page
     *
     * At the moment, just a finish message, but eventually will add statistics
     */
    private void renderEquationsComplete () {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(2);
    }

    /**
     * Set parameters for calculation type.
     *
     * For each equation type, define the parameters needed to create the equations object
     *
     * @param spr SharedPreference object
     */
    abstract void setParameters (SharedPreferences spr);

    /**
     * Set the header for the calculation type
     *
     * For each equation type, set the header as desired in the defined parameter
     *
     * @param calcHeader TextView for the calculation header
     */
    abstract void setCalcHeader (TextView calcHeader);

    /**
     * Generate equations
     *
     * For each equation type, create the desired equations object and provide necessary parameters
     */
    abstract void genNewEquations ();
}
