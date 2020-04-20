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

import click.mcgowan.mathsmate.core.TimesTablesEquations;

/**
 * Class for Times Table Calculator Activity
 *
 * Times Table Calculator Activity provides the interactive screen for all calculations
 * If this works, all custom activities use activity_main.xml, activity_calc.xml and in turn, countdown.xml and calculator.xml
 */

public class TimesTablesCalcActivity extends AppCompatActivity {

    //Shared Preference Settings
    public static final String mathsMateSettings = "maths_mate_settings";

    //Times Tables Settings
    final int[] sbTteEqRangeValue = new int[1];
    final boolean[] rgTteRandomValue = new boolean[1];

    //Times Tables Equations
    private TimesTablesEquations ttes;
    private int currentEquationKey;

    //Calculated Vars
    private StringBuilder equationString;
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

        //Read shared settings or set defaults if they don't exist
        SharedPreferences spr = getSharedPreferences(mathsMateSettings,0);
        sbTteEqRangeValue[0] = spr.getInt("tte_eq_range", 1);
        rgTteRandomValue[0] = spr.getBoolean("tte_eq_random", false);

        //For Times Tables, we actually don't need to use intent. Yet

        //Set the Header
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
                //ttes = new TimesTablesEquations(4, false);
                ttes = new TimesTablesEquations(sbTteEqRangeValue[0] * 2, rgTteRandomValue[0]);

                //Call private method to actually render the equation
                renderNewEquation();
                //Log.d("DEBUG_NEXT_GEN",ttes.getOperandForEquation("0",0));
                Log.d("DEBUG_NEXT_GEN",String.valueOf(ttes.getEquationMapSize()));
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
     * Triggered by btnCalcSettings
     *
     * Will render a configuration form unique to this calculation time and populated based on saved values
     *
     * @param view
     */
    public void calcSettings (View view) {

        //Setup elements we need to work with
        TextView calcHeader = (TextView) findViewById(R.id.tvCalcHeader);
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        SeekBar sbTteEqRange = (SeekBar)findViewById(R.id.sbTteEqRange);
        RadioGroup rgTteRandom = (RadioGroup)findViewById(R.id.rgTteRandom);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);
        final String[] finalSeekStatus = {""};

        //Set header and save button
        calcHeader.setText(R.string.settings_timestables);
        openSettings.setVisibility(View.GONE);
        saveSettings.setVisibility(View.VISIBLE);
        incBody.setDisplayedChild(3);

        //Set some default settings. First two will eventually be customizable
        sbTteEqRange.setMin(1);
        sbTteEqRange.setMax(12 / 2); //We want to increment this in values of 2. So first we actually reduce the range by half
        sbTteEqRange.setProgress(sbTteEqRangeValue[0]);
        sbTteEqRange.refreshDrawableState();

        //Setup Handlers for the Seek Bar
        sbTteEqRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //Act for onProgressChanged
            //Take what ever value is entered amd multiply so it's always in increments of 2
            //Set it in sbTteEqRangeValue and render value in Toast
            @Override
            public void onProgressChanged (
                    SeekBar seekBar,
                    int progress,
                    boolean fromUser
            ) {
                sbTteEqRangeValue[0] = progress;

                StringBuilder seekStatus = new StringBuilder();
                seekStatus.append(getString(R.string.settings_range));
                seekStatus.append(" is ");
                seekStatus.append(sbTteEqRangeValue[0] * 2); //We want to increment this in values of 2, so we double
                finalSeekStatus[0] = seekStatus.toString();

                Toast.makeText(getApplicationContext(), finalSeekStatus[0], Toast.LENGTH_SHORT).show();
            }

            //Act for onStartTrackingTouch. Display the set value
            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

                if (finalSeekStatus[0].length() > 0 ) {
                    Toast.makeText(getApplicationContext(), finalSeekStatus[0], Toast.LENGTH_SHORT).show();
                }
            }

            //No Action Required for onStopTrackingTouch
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Set some default settings
        if (rgTteRandomValue[0] == false) {
            rgTteRandom.check(R.id.rbTteRandomFalse);
        }
        else {
            rgTteRandom.check(R.id.rbTteRandomTrue);
        }

        //Setup handlers for the radio buttons
        rgTteRandom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            //Run when a change is made to radio buttons
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == 2131165330) {
                    rgTteRandomValue[0] = false;
                }
                else {
                    rgTteRandomValue[0] = true;
                }
            }
        });
    }

    /**
     * Save settings and go back to times tables beginning page
     *
     * @param view
     */
    public void saveSettings (View view) {

        //Get flipper resource. We need to flip it. Get it!
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);

        //Save settings and commit them
        SharedPreferences.Editor spe = getSharedPreferences(mathsMateSettings,0).edit();
        spe.putInt("tte_eq_range", sbTteEqRangeValue[0]);
        spe.putBoolean("tte_eq_random", rgTteRandomValue[0]);
        spe.commit(); //Was going to use commit. But Android insisted of apply!

        //Notify settings saved
        Toast.makeText(getApplicationContext(), getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();

        //Reset buttons and displayed child
        saveSettings.setVisibility(View.GONE);
        openSettings.setVisibility(View.VISIBLE);
        incBody.setDisplayedChild(0);
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

        String operandAsString;
        TextView tvEquation = (TextView)findViewById(R.id.tvEquation);
        TextView tvStatus = (TextView)findViewById(R.id.tvStatus);

        //Loop until we have retrieved all operands for the equation
        do {
            operandAsString = ttes.getNextOperandNextEquation();

            //For first run, set first operand AND append the multiplication symbol
            if (ttes.getOperandIndexCurrentEquation() < 1) {
                equationString = new StringBuilder();
                equationString.append(operandAsString);
                equationString.append(getString(R.string.multiplication_symbol));
            }
            //For second run, set the second operand AND append the equals symbol
            else if (ttes.getOperandIndexCurrentEquation() < 2) {
                equationString.append(operandAsString);
                equationString.append(getString(R.string.equals_symbol));
            }
        } while ((ttes.getOperandIndexCurrentEquation() + 1) < ttes.getOperandLengthCurrentEquation());

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
        if (equationAnswer.length() == ttes.getAnswerCalcThisEquation().length()) {


            //If answer is correct, perform necessary steps to inform the user and prepare a new equation
            if (ttes.verifyAnswerUserThisEquation(equationAnswer) == true) {

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
                        if ((ttes.getCurrentEquationKey() + 1) < ttes.getEquationMapSize()) {
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
}
