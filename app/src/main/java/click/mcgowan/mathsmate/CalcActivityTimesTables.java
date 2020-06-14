package click.mcgowan.mathsmate;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import click.mcgowan.mathsmate.core.Equations;
import click.mcgowan.mathsmate.core.TimesTablesEquations;

/**
 * Class for Times Tables Calculations activities. Extends CalcActivity. Uses the following resources
 *
 * * settings_timestables.xml Layout for the Times Tables settings form. Lets you adjust parameters for Times Tables
 *
 * Specific details about the abstract classes are documented as per normal
 */
public class CalcActivityTimesTables extends CalcActivity{

    //Times Tables Parameters
    final int[] sbTteEqRangeValue = new int[1];        //range of the operands. For times tables, the number of equations is range * range
    final boolean[] rgTteRandomValue = new boolean[1]; //if true, equations will be rendered at random

    /**
     * Load parameters or set defaults if they don't exist so they can be provided to the desired Equations class
     *
     * For times tables, this includes
     *
     * * sbTtsEqRangeValue - range of the operands
     * * rgTteRandomValue  - if true, equations will be rendered at random
     *
     */
    void setParameters() {

        SharedPreferences spr = getSharedPreferences(mathsMateSettings,0);

        sbTteEqRangeValue[0] = spr.getInt("tte_eq_range", 1);
        rgTteRandomValue[0] = spr.getBoolean("tte_eq_random", false);
    }

    /**
     * Set the desired header to be the following value:
     *
     * getString(R.string.timestables)
     *
     * @param calcHeader TextView for the calculation header
     */
    void setCalcHeader(TextView calcHeader) {

        calcHeader.setText(getString(R.string.timestables));
    }

    /**
     * Create TimesTablesEquations object and use the following parameters:
     *
     * * sbTtsEqRangeValue - range of the operands
     * * rgTteRandomValue  - if true, equations will be rendered at random
     *
     */
    void genNewEquations () {

        TimesTablesEquations ttes = new TimesTablesEquations(sbTteEqRangeValue[0] * 2, rgTteRandomValue[0]);

        equations = (Equations) ttes;
    }

    /**
     * Render a form using a resource of settings_timestables.xml so parameters for TimesTablesEquations can be customised
     *
     * @param view Required for onClick
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
     * Saves parameters as configured in calcSettings so they can be loaded for later use.
     *
     * @param view Required for onClick
     */
    public void saveSettings (View view) {

        //Get flipper resource. We need to flip it. Get it!
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);
        TextView tvRenderCount = (TextView) findViewById(R.id.tvRenderCount);
        Button btnBegin = (Button) findViewById(R.id.btnBegin);

        //Save settings and commit them
        SharedPreferences.Editor spe = getSharedPreferences(mathsMateSettings,0).edit();
        spe.putInt("tte_eq_range", sbTteEqRangeValue[0]);
        spe.putBoolean("tte_eq_random", rgTteRandomValue[0]);
        spe.commit(); //Was going to use commit. But Android insisted of apply!

        //Notify settings saved
        Toast.makeText(getApplicationContext(), getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();

        //Reset buttons and displayed child so we can start the equations again
        saveSettings.setVisibility(View.GONE);
        openSettings.setVisibility(View.VISIBLE);
        tvRenderCount.setVisibility(View.GONE);
        btnBegin.setVisibility(View.VISIBLE);
        incBody.setDisplayedChild(0);
    }
}
