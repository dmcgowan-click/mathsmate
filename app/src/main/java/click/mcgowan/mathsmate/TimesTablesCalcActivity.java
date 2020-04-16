package click.mcgowan.mathsmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import click.mcgowan.mathsmate.core.TimesTablesEquations;

/**
 * Class for Times Table Calculator Activity
 *
 * Times Table Calculator Activity provides the interactive screen for all calculations
 * If this works, all custom activities use activity_main.xml, activity_calc.xml and in turn, countdown.xml and calculator.xml
 */

public class TimesTablesCalcActivity extends AppCompatActivity {

    //Times Tables Equations
    private TimesTablesEquations tte;

    //Calculated Vars
    private String equationString = "";
    private String equationAnswer = "?";
    private boolean equationUserReset = true;
    private boolean lockKeypad = true;

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

        //For Times Tables, we actually don't need to use intent. Yet

        TextView calcHeader = (TextView)findViewById(R.id.tvCalcHeader);
        calcHeader.setText(getString(R.string.timestables));
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

                //Create new times table equations object and generate equations
                tte = new TimesTablesEquations(4, true);

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //The following functions are not called directly by buttons but by other functions in this class//
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Render new equation based on TimesTableEquation object
     */
    private void renderNewEquation () {

        String[] operandAsString;
        TextView tvEquation = (TextView)findViewById(R.id.tvEquation);
        TextView tvStatus = (TextView)findViewById(R.id.tvStatus);

        operandAsString = tte.getOperandsNextEquation();

        for (int counter = 0; counter < operandAsString.length; counter++) {

            //Set the First Operand and append Multiplication Symbol during the first loop
            if (counter == 0) {
                equationString = operandAsString[counter];
                equationString = equationString + getString(R.string.multiplication_symbol);
            }
            //Set the Second Operand and Equal Symbol during the second loop
            else if ( counter == 1) {
                equationString = equationString + operandAsString[counter];
                equationString = equationString + getString(R.string.equals_symbol);
            }
        }

        //Set the user answer to ?
        equationAnswer = "?";

        //Render equation and status
        tvEquation.setText(getString(R.string.equation_mask, equationString, equationAnswer));
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
        if (equationAnswer.length() == tte.getAnswerCalcThisEquation().length()) {

            if (tte.verifyAnswerUserThisEquation(equationAnswer) == true) {

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

                        //If index + 1 is a match to equation map size, where all done. Render the finish page
                        if ((tte.getCurrentEquationIndex() + 1) == tte.getEquationMapSize()) {
                            renderEquationsComplete();
                        }
                        //Otherwise, we render the next equation
                        else {
                            renderNewEquation();
                        }
                    }
                }.start();
            }
            else {
                tvStatus.setText(getString(R.string.status_fail));
                tvStatus.setTextColor(getColor(R.color.colorFail));

                //Set equationUserReset to true so next input erases previous answer
                equationUserReset = true;
            }
        }

        Log.d("SIZE",String.valueOf(tte.getAnswerCalcThisEquation().length()));
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
}
