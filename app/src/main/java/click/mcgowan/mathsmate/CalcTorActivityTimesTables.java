package click.mcgowan.mathsmate;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import click.mcgowan.mathsmate.core.AddSubMulDivEquations;
import click.mcgowan.mathsmate.core.Equations;
import click.mcgowan.mathsmate.core.TimesTablesEquations;

/**
 * Class for Tournament Timestables activities. Extends CalcActivity. Uses the following resources
 *
 * * tor_menu_timestables.xml Layout for the sub menu where you can select equations from easy to hard
 * * settings_tor_timestables.xml Layout for the Tournament Timestables settings form. Main options will be to reset progress
 *
 * Specific details about the abstract classes are documented as per normal
 */
public class CalcTorActivityTimesTables extends CalcActivity {

    //Timestables Parameters for Tournament mode
    int ttesXRangeHigh;
    int ttesXRangeLow;
    int ttesYRangeHigh;
    int ttesYRangeLow;
    int ttesLevel;

    //Shared preference settings
    final boolean[] ttesLevel1Prg = new boolean[1];
    final boolean[] ttesLevel2Prg = new boolean[1];
    final boolean[] ttesLevel3Prg = new boolean[1];
    final boolean[] ttesLevel4Prg = new boolean[1];
    final boolean[] ttesLevel5Prg = new boolean[1];
    final boolean[] ttesLevel6Prg = new boolean[1];
    final boolean[] ttesLevel7Prg = new boolean[1];
    final boolean[] ttesLevel8Prg = new boolean[1];
    final boolean[] ttesLevel9Prg = new boolean[1];
    final int[] ttesTotalPrg = new int[1];

    /**
     * Load parameters or set defaults if they don't exist
     *
     * For tournament timestables, this is just the progress of the various levels:
     *
     * * ttesLevel1Prg - completion of the first level
     * * ttesLevel2Prg - completion of the second level
     * * ttesLevel3Prg - completion of the third level
     * * ttesLevel4Prg - completion of the fourth level
     * * ttesLevel5Prg - completion of the fifth level
     * * ttesLevel6Prg - completion of the sixth level
     * * ttesLevel7Prg - completion of the seventh level
     * * ttesLevel8Prg - completion of the eighth level
     * * ttesLevel9Prg - completion of the ninth level
     *
     */
    void setParameters() {

        SharedPreferences spr = getSharedPreferences(mathsMateSettings,0);

        ttesLevel1Prg[0] = spr.getBoolean("ttes_tor_level1", false);
        ttesLevel2Prg[0] = spr.getBoolean("ttes_tor_level2", false);
        ttesLevel3Prg[0] = spr.getBoolean("ttes_tor_level3", false);
        ttesLevel4Prg[0] = spr.getBoolean("ttes_tor_level4", false);
        ttesLevel5Prg[0] = spr.getBoolean("ttes_tor_level5", false);
        ttesLevel6Prg[0] = spr.getBoolean("ttes_tor_level6", false);
        ttesLevel7Prg[0] = spr.getBoolean("ttes_tor_level7", false);
        ttesLevel8Prg[0] = spr.getBoolean("ttes_tor_level8", false);
        ttesLevel9Prg[0] = spr.getBoolean("ttes_tor_level9", false);
        ttesTotalPrg[0] = spr.getInt("ttes_tor_total", 0);
    }

    /**
     * Render a menu in flipper using resource of tor_menu_timestables.xml
     */
    void setFlipperInitLayout () {

        //Setup initial flipper. Use layout inflater if needed
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        View childLayout = getLayoutInflater().inflate(R.layout.tor_menu_timestables, null);
        incBody.addView(childLayout);
        incBody.setDisplayedChild(3); //prefer this to be an ID, but don't know how to retrieve it

        ProgressBar prgLevel1 = (ProgressBar)findViewById(R.id.prgLevel1);
        if (ttesLevel1Prg[0] == true) {
            prgLevel1.setProgress(1);
        }
        else {
            prgLevel1.setProgress(0);
        }

        ProgressBar prgLevel2 = (ProgressBar)findViewById(R.id.prgLevel2);
        if (ttesLevel2Prg[0] == true) {
            prgLevel2.setProgress(1);
        }
        else {
            prgLevel2.setProgress(0);
        }

        ProgressBar prgLevel3 = (ProgressBar)findViewById(R.id.prgLevel3);
        if (ttesLevel3Prg[0] == true) {
            prgLevel3.setProgress(1);
        }
        else {
            prgLevel3.setProgress(0);
        }

        ProgressBar prgLevel4 = (ProgressBar)findViewById(R.id.prgLevel4);
        if (ttesLevel4Prg[0] == true) {
            prgLevel4.setProgress(1);
        }
        else {
            prgLevel4.setProgress(0);
        }

        ProgressBar prgLevel5 = (ProgressBar)findViewById(R.id.prgLevel5);
        if (ttesLevel5Prg[0] == true) {
            prgLevel5.setProgress(1);
        }
        else {
            prgLevel5.setProgress(0);
        }

        ProgressBar prgLevel6 = (ProgressBar)findViewById(R.id.prgLevel6);
        if (ttesLevel6Prg[0] == true) {
            prgLevel6.setProgress(1);
        }
        else {
            prgLevel6.setProgress(0);
        }

        ProgressBar prgLevel7 = (ProgressBar)findViewById(R.id.prgLevel7);
        if (ttesLevel7Prg[0] == true) {
            prgLevel7.setProgress(1);
        }
        else {
            prgLevel7.setProgress(0);
        }

        ProgressBar prgLevel8 = (ProgressBar)findViewById(R.id.prgLevel8);
        if (ttesLevel8Prg[0] == true) {
            prgLevel8.setProgress(1);
        }
        else {
            prgLevel8.setProgress(0);
        }

        ProgressBar prgLevel9 = (ProgressBar)findViewById(R.id.prgLevel9);
        if (ttesLevel9Prg[0] == true) {
            prgLevel9.setProgress(1);
        }
        else {
            prgLevel9.setProgress(0);
        }

        ProgressBar prgTotal = (ProgressBar)findViewById(R.id.prgTotal);
        prgTotal.setProgress(ttesTotalPrg[0]);
    }

    /**
     * Set the desired header to be the following value:
     *
     * getString(R.string.tor_timestables)
     *
     * @param calcHeader TextView for the calculation header
     */
    void setCalcHeader(TextView calcHeader) {

        calcHeader.setText(getString(R.string.tor_timestables));
    }

    /**
     * Create AddSubMulDivEquations object and use parameters set by difficulty options in tor_menu_addsubmuldiv.xml:
     */
    void genNewEquations () {

        TimesTablesEquations ttes = new TimesTablesEquations(
                ttesXRangeHigh,
                ttesXRangeLow,
                ttesYRangeHigh,
                ttesYRangeLow,
                false);

        equations = (Equations) ttes;
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
        switch (ttesLevel) {
            case 1 :
                if (ttesLevel1Prg[0] == false) {

                    ttesLevel1Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level1", ttesLevel1Prg[0]);
                }
                break;
            case 2 :
                if (ttesLevel2Prg[0] == false) {

                    ttesLevel2Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level2", ttesLevel2Prg[0]);
                }
                break;
            case 3 :
                if (ttesLevel3Prg[0] == false) {

                    ttesLevel3Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level3", ttesLevel3Prg[0]);
                }
                break;
            case 4 :
                if (ttesLevel4Prg[0] == false) {

                    ttesLevel4Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level4", ttesLevel4Prg[0]);
                }
                break;
            case 5 :
                if (ttesLevel5Prg[0] == false) {

                    ttesLevel5Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level5", ttesLevel5Prg[0]);
                }
                break;
            case 6 :
                if (ttesLevel6Prg[0] == false) {

                    ttesLevel6Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level6", ttesLevel6Prg[0]);
                }
                break;
            case 7 :
                if (ttesLevel7Prg[0] == false) {

                    ttesLevel7Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level7", ttesLevel7Prg[0]);
                }
                break;
            case 8 :
                if (ttesLevel8Prg[0] == false) {

                    ttesLevel8Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level8", ttesLevel8Prg[0]);
                }
                break;
            case 9 :
                if (ttesLevel9Prg[0] == false) {

                    ttesLevel9Prg[0] = true;
                    ttesTotalPrg[0] = ttesTotalPrg[0] + 1;
                    spe.putBoolean("ttes_tor_level9", ttesLevel9Prg[0]);
            }
        }

        spe.putInt("ttes_tor_total", ttesTotalPrg[0]);
        spe.apply();
    }

    /**
     * 1x Timestables
     *
     * @param view Required for onClick
     */
    public void startOneX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 1;
        ttesXRangeLow = 1;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 1;
    }

    /**
     * 2x Timestables
     *
     * @param view Required for onClick
     */
    public void startTwoX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 2;
        ttesXRangeLow = 2;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 2;
    }

    /**
     * 3x Timestables
     *
     * @param view Required for onClick
     */
    public void startThreeX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 3;
        ttesXRangeLow = 3;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 3;
    }

    /**
     * 4x Timestables
     *
     * @param view Required for onClick
     */
    public void startFourX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 4;
        ttesXRangeLow = 4;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 4;
    }

    /**
     * 5x Timestables
     *
     * @param view Required for onClick
     */
    public void startFiveX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 5;
        ttesXRangeLow = 5;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 5;
    }

    /**
     * 6x Timestables
     *
     * @param view Required for onClick
     */
    public void startSixX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 6;
        ttesXRangeLow = 6;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 6;
    }

    /**
     * 7x Timestables
     *
     * @param view Required for onClick
     */
    public void startSevenX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 7;
        ttesXRangeLow = 7;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 7;
    }

    /**
     * 8x Timestables
     *
     * @param view Required for onClick
     */
    public void startEightX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 8;
        ttesXRangeLow = 8;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 8;
    }

    /**
     * 9x Timestables
     *
     * @param view Required for onClick
     */
    public void startNineX (View view) {

        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        incBody.setDisplayedChild(0);

        ttesXRangeHigh = 9;
        ttesXRangeLow = 9;
        ttesYRangeHigh = 10;
        ttesYRangeLow = 1;
        ttesLevel = 9;
    }

    /**
     * Render a form using a resource of settings_tor_timestables.xml so parameters for TimestablesEquations can be customised
     *
     * @param view Required for onClick
     */
    public void calcSettings (View view) {
        Toast.makeText(getApplicationContext(), "DEBUG", Toast.LENGTH_SHORT).show();
        //Setup flipper and add settings_addsubmuldiv.xml to it. All other element setting will fail otherwise
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        View childLayout = getLayoutInflater().inflate(R.layout.settings_tor_timestables, null);
        incBody.addView(childLayout);
        incBody.setDisplayedChild(4);

        //Setup elements common to all settings
        TextView calcHeader = (TextView) findViewById(R.id.tvCalcHeader);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);

        //Set header and save button
        calcHeader.setText(R.string.settings_tor_timestables);
        openSettings.setVisibility(View.GONE);
        saveSettings.setVisibility(View.VISIBLE);
    }

    /**
     * Triggered by btnTorTtesClear. Clear settings
     *
     * @param view Required for onClick
     */
    public void clearProgress (View view) {

        //Code to save and render progress
        SharedPreferences.Editor spe = getSharedPreferences(mathsMateSettings,0).edit();

        ttesLevel1Prg[0] = false;
        ttesLevel2Prg[0] = false;
        ttesLevel3Prg[0] = false;
        ttesLevel4Prg[0] = false;
        ttesLevel5Prg[0] = false;
        ttesLevel6Prg[0] = false;
        ttesLevel7Prg[0] = false;
        ttesLevel8Prg[0] = false;
        ttesLevel9Prg[0] = false;
        ttesTotalPrg[0] = 0;

        spe.putBoolean("ttes_tor_level1", ttesLevel1Prg[0]);
        spe.putBoolean("ttes_tor_level2", ttesLevel2Prg[0]);
        spe.putBoolean("ttes_tor_level3", ttesLevel3Prg[0]);
        spe.putBoolean("ttes_tor_level4", ttesLevel4Prg[0]);
        spe.putBoolean("ttes_tor_level5", ttesLevel5Prg[0]);
        spe.putBoolean("ttes_tor_level6", ttesLevel6Prg[0]);
        spe.putBoolean("ttes_tor_level7", ttesLevel7Prg[0]);
        spe.putBoolean("ttes_tor_level8", ttesLevel8Prg[0]);
        spe.putBoolean("ttes_tor_level9", ttesLevel9Prg[0]);
        spe.putInt("ttes_tor_total", ttesTotalPrg[0]);

        spe.apply();

        //Notify settings saved
        Toast.makeText(getApplicationContext(), getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();

        //Setup elements common to all settings
        TextView calcHeader = (TextView) findViewById(R.id.tvCalcHeader);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);

        //Set header and save button
        calcHeader.setText(R.string.tor_timestables);
        openSettings.setVisibility(View.VISIBLE);
        saveSettings.setVisibility(View.GONE);

        //Now load initial flipper layout
        setFlipperInitLayout();
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
