package click.mcgowan.mathsmate;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import click.mcgowan.mathsmate.core.AddSubMulDivEquations;
import click.mcgowan.mathsmate.core.Equations;

/**
 * Class for Tournament Addition, Subtraction, Multiplication, Division activities. Extends CalcActivity. Uses the following resources
 *
 * * tor_menu_addsubmuldiv.xml Layout for the sub menu where you can select equations from easy to hard
 * * tor_settings_addsubmuldiv.xml Layout for the Addition, Subtraction, Multiplication, Division settings form. Lets you adjust parameters for Addition, Subtraction, Multiplication, Division equations
 *
 * Specific details about the abstract classes are documented as per normal
 */
public class CalcTorActivityAddSubMulDiv extends CalcActivity {

    //Add Sub Mul Div Parameters for Tournament mode
    int asmdEqCount;
    int asmdOpCount;
    int asmdRangeHigh;
    int asmdRangeLow;
    char[] asmdOperators;

    /**
     * Load parameters or set defaults if they don't exist so they can be provided to the desired Equations class
     *
     * For addition, subtraction, multiplication and division, this includes:
     *
     * * sbAsmdEqCountValue    - number of equations
     *
     */
    void setParameters() {

        SharedPreferences spr = getSharedPreferences(mathsMateSettings,0);

        //sbAsmdDivValue[0] = spr.getBoolean("asmd_div", false);
    }

    /**
     * Render a menu in flipper using resource of tor_menu_addsubmuldiv.xml
     */
    void setFlipperInitLayout () {

        //Setup initial flipper. Use layout inflater if needed
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        View childLayout = getLayoutInflater().inflate(R.layout.tor_menu_addsubmuldiv, null);
        incBody.addView(childLayout);
        incBody.setDisplayedChild(3); //prefer this to be an ID, but don't know how to retrieve it
    }

    /**
     * Set the desired header to be the following value:
     *
     * getString(R.string.tor_addsubmuldiv)
     *
     * @param calcHeader TextView for the calculation header
     */
    void setCalcHeader(TextView calcHeader) {

        calcHeader.setText(getString(R.string.tor_addsubmuldiv));
    }

    //Following under construction
    /**
     * Create AddSubMulDivEquations object and use parameters set by difficulty options in tor_menu_addsubmuldiv.xml:
     */
    void genNewEquations () {

        AddSubMulDivEquations asmds = new AddSubMulDivEquations(
                asmdEqCount,
                asmdOpCount,
                asmdRangeHigh,
                asmdRangeLow,
                0,
                false, //not yet customisable
                asmdOperators);

        equations = (Equations) asmds;
    }

    /**
     * No post processing required. Yet (eventually will save progress here)
     */
    void postComplete () {

        //Start countdown timer and render calculator.xml on countdown completion
        new CountDownTimer(4000, 1000) {

            //No need to display progress
            public void onTick(long millisUntilFinished) {}

            //Save progress and reload initial layout
            public void onFinish() {

                //Code to save progress yet to be added

                //Reload initial layout
                setFlipperInitLayout();
            }
        }.start();
    }

    /**
     * for Addition Easy, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startAddEasy (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 1;
        asmdRangeHigh = 10;
        asmdOperators = new char[]{'+'};
    }

    /**
     * Render a form using a resource of settings_addsubmuldiv.xml so parameters for AddSubMulDivEquations can be customised
     *
     * @param view Required for onClick
     */
    public void calcSettings (View view) {

        //Setup flipper and add settings_addsubmuldiv.xml to it. All other element setting will fail otherwise
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);

    }

    /**
     * Triggered by btnCalcSaveSettings
     *
     * Save settings from times tables configuration from to shared preference
     *
     * @param view
     */
    public void saveSettings (View view) {

        //Get flipper resource. We need to flip it. Get it!
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);

    }
}
