package click.mcgowan.mathsmate;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
 * * settings_tor_addsubmuldiv.xml Layout for the Tournament Addition, Subtraction, Multiplication, Division settings form. Main options will be to reset progress
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
    int asmdLevel;

    //Shared preference settings
    final boolean[] asmdLevel1Prg = new boolean[1];
    final boolean[] asmdLevel2Prg = new boolean[1];
    final boolean[] asmdLevel3Prg = new boolean[1];
    final boolean[] asmdLevel4Prg = new boolean[1];
    final boolean[] asmdLevel5Prg = new boolean[1];
    final boolean[] asmdLevel6Prg = new boolean[1];
    final boolean[] asmdLevel7Prg = new boolean[1];
    final boolean[] asmdLevel8Prg = new boolean[1];
    final boolean[] asmdLevel9Prg = new boolean[1];
    final int[] asmdTotalPrg = new int[1];

    /**
     * Load parameters or set defaults if they don't exist
     *
     * For tournament addition, subtraction, multiplication and division, this is just the progress of the various levels:
     *
     * * asmdLevel1Prg - completion of the first level
     *
     */
    void setParameters() {

        SharedPreferences spr = getSharedPreferences(mathsMateSettings,0);

        asmdLevel1Prg[0] = spr.getBoolean("asmd_tor_level1", false);
        asmdLevel2Prg[0] = spr.getBoolean("asmd_tor_level2", false);
        asmdLevel3Prg[0] = spr.getBoolean("asmd_tor_level3", false);
        asmdLevel4Prg[0] = spr.getBoolean("asmd_tor_level4", false);
        asmdLevel5Prg[0] = spr.getBoolean("asmd_tor_level5", false);
        asmdLevel6Prg[0] = spr.getBoolean("asmd_tor_level6", false);
        asmdLevel7Prg[0] = spr.getBoolean("asmd_tor_level7", false);
        asmdLevel8Prg[0] = spr.getBoolean("asmd_tor_level8", false);
        asmdLevel9Prg[0] = spr.getBoolean("asmd_tor_level9", false);
        asmdTotalPrg[0] = spr.getInt("asmd_tor_total", 0);
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

        ProgressBar prgLevel1 = (ProgressBar)findViewById(R.id.prgLevel1);
        if (asmdLevel1Prg[0] == true) {
            prgLevel1.setProgress(1);
        }
        else {
            prgLevel1.setProgress(0);
        }

        ProgressBar prgLevel2 = (ProgressBar)findViewById(R.id.prgLevel2);
        if (asmdLevel2Prg[0] == true) {
            prgLevel2.setProgress(1);
        }
        else {
            prgLevel2.setProgress(0);
        }

        ProgressBar prgLevel3 = (ProgressBar)findViewById(R.id.prgLevel3);
        if (asmdLevel3Prg[0] == true) {
            prgLevel3.setProgress(1);
        }
        else {
            prgLevel3.setProgress(0);
        }

        ProgressBar prgLevel4 = (ProgressBar)findViewById(R.id.prgLevel4);
        if (asmdLevel4Prg[0] == true) {
            prgLevel4.setProgress(1);
        }
        else {
            prgLevel4.setProgress(0);
        }

        ProgressBar prgLevel5 = (ProgressBar)findViewById(R.id.prgLevel5);
        if (asmdLevel5Prg[0] == true) {
            prgLevel5.setProgress(1);
        }
        else {
            prgLevel5.setProgress(0);
        }

        ProgressBar prgLevel6 = (ProgressBar)findViewById(R.id.prgLevel6);
        if (asmdLevel6Prg[0] == true) {
            prgLevel6.setProgress(1);
        }
        else {
            prgLevel6.setProgress(0);
        }

        ProgressBar prgLevel7 = (ProgressBar)findViewById(R.id.prgLevel7);
        if (asmdLevel7Prg[0] == true) {
            prgLevel7.setProgress(1);
        }
        else {
            prgLevel7.setProgress(0);
        }

        ProgressBar prgLevel8 = (ProgressBar)findViewById(R.id.prgLevel8);
        if (asmdLevel8Prg[0] == true) {
            prgLevel8.setProgress(1);
        }
        else {
            prgLevel8.setProgress(0);
        }

        ProgressBar prgLevel9 = (ProgressBar)findViewById(R.id.prgLevel9);
        if (asmdLevel9Prg[0] == true) {
            prgLevel9.setProgress(1);
        }
        else {
            prgLevel9.setProgress(0);
        }

        ProgressBar prgTotal = (ProgressBar)findViewById(R.id.prgTotal);
        prgTotal.setProgress(asmdTotalPrg[0]);
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
     * For tournament mode, we need to record the progress, save it and re-render the tournament menu
     *
     * We will display the next button which will allow this activity to occur
     */
    void postComplete () {

        Button btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                setFlipperInitLayout();
            }
        });

        //Code to save and render progress
        SharedPreferences.Editor spe = getSharedPreferences(mathsMateSettings,0).edit();

        //Alter progress based on the level that was chosen. Only adjust where a levels existing status has actually changed
        switch (asmdLevel) {
            case 1 :
                if (asmdLevel1Prg[0] == false) {

                    asmdLevel1Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level1", asmdLevel1Prg[0]);
                }
                break;
            case 2 :
                if (asmdLevel2Prg[0] == false) {

                    asmdLevel2Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level2", asmdLevel2Prg[0]);
                }
                break;
            case 3 :
                if (asmdLevel3Prg[0] == false) {

                    asmdLevel3Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level3", asmdLevel3Prg[0]);
                }
                break;
            case 4 :
                if (asmdLevel4Prg[0] == false) {

                    asmdLevel4Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level4", asmdLevel4Prg[0]);
                }
                break;
            case 5 :
                if (asmdLevel5Prg[0] == false) {

                    asmdLevel5Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level5", asmdLevel5Prg[0]);
                }
                break;
            case 6 :
                if (asmdLevel6Prg[0] == false) {

                    asmdLevel6Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level6", asmdLevel6Prg[0]);
                }
                break;
            case 7 :
                if (asmdLevel7Prg[0] == false) {

                    asmdLevel7Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level7", asmdLevel7Prg[0]);
                }
                break;
            case 8 :
                if (asmdLevel8Prg[0] == false) {

                    asmdLevel8Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level8", asmdLevel8Prg[0]);
                }
                break;
            case 9 :
                if (asmdLevel9Prg[0] == false) {

                    asmdLevel9Prg[0] = true;
                    asmdTotalPrg[0] = asmdTotalPrg[0] + 1;
                    spe.putBoolean("asmd_tor_level9", asmdLevel9Prg[0]);
            }
        }

        spe.putInt("asmd_tor_total", asmdTotalPrg[0]);
        spe.apply();
    }

    /**
     * For Addition Easy, set appropriate parameters for genNewEquations and render countdown.xml
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
        asmdLevel = 1;
    }

    /**
     * For Subtraction Easy, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startSubEasy (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 1;
        asmdRangeHigh = 10;
        asmdOperators = new char[]{'-'};
        asmdLevel = 2;
    }

    /**
     * For Addition Medium, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startAddMedium (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 5;
        asmdRangeHigh = 20;
        asmdOperators = new char[]{'+'};
        asmdLevel = 3;
    }

    /**
     * For Subtraction Medium, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startSubMedium (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 5;
        asmdRangeHigh = 20;
        asmdOperators = new char[]{'-'};
        asmdLevel = 4;
    }

    /**
     * For Multiplication Easy, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startMulEasy (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 1;
        asmdRangeHigh = 5;
        asmdOperators = new char[]{'*'};
        asmdLevel = 5;
    }

    /**
     * For Multiplication Medium, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startMulMedium (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 2;
        asmdRangeHigh = 10;
        asmdOperators = new char[]{'*'};
        asmdLevel = 6;
    }

    /**
     * For Division Easy, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startDivEasy (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 1;
        asmdRangeHigh = 10;
        asmdOperators = new char[]{'/'};
        asmdLevel = 7;
    }

    /**
     * For Division Medium, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startDivMedium (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 1;
        asmdRangeHigh = 20;
        asmdOperators = new char[]{'/'};
        asmdLevel = 8;
    }

    /**
     * For Mix Medium, set appropriate parameters for genNewEquations and render countdown.xml
     *
     * @param view Required for onClick
     */
    public void startMixMedium (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        asmdEqCount = 10;
        asmdOpCount = 2;
        asmdRangeLow = 1;
        asmdRangeHigh = 10;
        asmdOperators = new char[]{'+','-','*','/'};
        asmdLevel = 9;
    }

    /**
     * Render a form using a resource of settings_tor_addsubmuldiv.xml so parameters for AddSubMulDivEquations can be customised
     *
     * @param view Required for onClick
     */
    public void calcSettings (View view) {

        //Setup flipper and add settings_addsubmuldiv.xml to it. All other element setting will fail otherwise
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        View childLayout = getLayoutInflater().inflate(R.layout.settings_tor_addsubmuldiv, null);
        incBody.addView(childLayout);
        incBody.setDisplayedChild(4);

        //Setup elements common to all settings
        TextView calcHeader = (TextView) findViewById(R.id.tvCalcHeader);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);

        //Set header and save button
        calcHeader.setText(R.string.settings_tor_addsubmuldiv);
        openSettings.setVisibility(View.GONE);
        saveSettings.setVisibility(View.VISIBLE);
    }

    /**
     * Triggered by btnTorAsmdClear. Clear settings
     *
     * @param view Required for onClick
     */
    public void clearProgress (View view) {

        //Code to save and render progress
        SharedPreferences.Editor spe = getSharedPreferences(mathsMateSettings,0).edit();

        asmdLevel1Prg[0] = false;
        asmdLevel2Prg[0] = false;
        asmdLevel3Prg[0] = false;
        asmdLevel4Prg[0] = false;
        asmdLevel5Prg[0] = false;
        asmdLevel6Prg[0] = false;
        asmdLevel7Prg[0] = false;
        asmdLevel8Prg[0] = false;
        asmdLevel9Prg[0] = false;
        asmdTotalPrg[0] = 0;

        spe.putBoolean("asmd_tor_level1", asmdLevel1Prg[0]);
        spe.putBoolean("asmd_tor_level2", asmdLevel2Prg[0]);
        spe.putBoolean("asmd_tor_level3", asmdLevel3Prg[0]);
        spe.putBoolean("asmd_tor_level4", asmdLevel4Prg[0]);
        spe.putBoolean("asmd_tor_level5", asmdLevel5Prg[0]);
        spe.putBoolean("asmd_tor_level6", asmdLevel6Prg[0]);
        spe.putBoolean("asmd_tor_level7", asmdLevel7Prg[0]);
        spe.putBoolean("asmd_tor_level8", asmdLevel8Prg[0]);
        spe.putBoolean("asmd_tor_level9", asmdLevel9Prg[0]);
        spe.putInt("asmd_tor_total", asmdTotalPrg[0]);

        spe.apply();

        //Setup flipper and add tor_menu_addsubmuldiv.xml to it. All other element setting will fail otherwise
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(3);

        //Setup elements common to all settings
        TextView calcHeader = (TextView) findViewById(R.id.tvCalcHeader);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);

        //Set header and save button
        calcHeader.setText(R.string.tor_addsubmuldiv);
        openSettings.setVisibility(View.VISIBLE);
        saveSettings.setVisibility(View.GONE);
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
