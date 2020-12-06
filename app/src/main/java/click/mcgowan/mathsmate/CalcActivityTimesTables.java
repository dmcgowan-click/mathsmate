package click.mcgowan.mathsmate;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    final int[] sbTteEqRangeHighValue = new int[1];    //high range of the operands. For times tables, the number of equations are (rangeHigh - (rangeLow - 1)) * 12
    final int[] sbTteEqRangeLowValue = new int[1];     //low range of the operands. For times tables, the number of equations are (rangeHigh - (rangeLow - 1)) * 12
    final boolean[] sbTteRandomValue = new boolean[1]; //if true, equations will be rendered at random

    /**
     * Load parameters or set defaults if they don't exist so they can be provided to the desired Equations class
     *
     * For times tables, this includes
     *
     * * sbTtsEqRangeHighValue - Highest number of the operand (applicable for first operand only)
     * * sbTtsEqRangeLowValue  - Lowest number of the operand (applicable for first operand only)
     * * sbTteRandomValue      - if true, equations will be rendered at random
     *
     */
    void setParameters() {

        SharedPreferences spr = getSharedPreferences(mathsMateSettings,0);

        sbTteEqRangeHighValue[0] = spr.getInt("tte_eq_range_high", 1);
        sbTteEqRangeLowValue[0] = spr.getInt("tte_eq_range_low", 1);
        sbTteRandomValue[0] = spr.getBoolean("tte_eq_random", false);
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
     * * sbTteRandomValue  - if true, equations will be rendered at random
     *
     */
    void genNewEquations () {

        TimesTablesEquations ttes = new TimesTablesEquations(
                sbTteEqRangeHighValue[0],
                sbTteEqRangeLowValue[0],
                sbTteRandomValue[0]);

        equations = (Equations) ttes;
    }

    /**
     * Render a form using a resource of settings_timestables.xml so parameters for TimesTablesEquations can be customised
     *
     * @param view Required for onClick
     */
    public void calcSettings (View view) {

        //Legacy code in case it's needed again
        //Include settings_timestables to flipper and ensure its displayed
        //LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //View childLayout        = inflater.inflate(R.layout.settings_timestables, (ViewGroup) findViewById(R.id.test));

        //Setup flipper and add settings_timestables.xml to it. All other element setting will fail otherwise
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        View childLayout = getLayoutInflater().inflate(R.layout.settings_timestables, null);
        incBody.addView(childLayout);
        incBody.setDisplayedChild(3);

        //Setup elements common to all settings
        TextView calcHeader = (TextView) findViewById(R.id.tvCalcHeader);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);

        //Setup elements common to settings_timestables.xml
        final SeekBar sbTteEqRangeHigh = (SeekBar)findViewById(R.id.sbTteEqRangeHigh);
        final TextView tvTteEqRangeHighVal = (TextView)findViewById(R.id.tvTteEqRangeHighVal);
        final SeekBar sbTteEqRangeLow = (SeekBar)findViewById(R.id.sbTteEqRangeLow);
        final TextView tvTteEqRangeLowVal = (TextView)findViewById(R.id.tvTteEqRangeLowVal);
        final SeekBar sbTteRandom = (SeekBar)findViewById(R.id.sbTteRandom);
        final TextView tvTteRandomVal = (TextView) findViewById(R.id.tvTteRandomVal);

        //Set header and save button
        calcHeader.setText(R.string.settings_timestables);
        openSettings.setVisibility(View.GONE);
        saveSettings.setVisibility(View.VISIBLE);

        //Set some default settings for range high seek bar
        sbTteEqRangeHigh.setMin(1);
        sbTteEqRangeHigh.setMax(12);
        sbTteEqRangeHigh.setProgress(sbTteEqRangeHighValue[0]);
        sbTteEqRangeHigh.refreshDrawableState();
        tvTteEqRangeHighVal.setText(String.valueOf(sbTteEqRangeHighValue[0]));

        //Set some default settings for range low seek bar
        sbTteEqRangeLow.setMin(1);
        sbTteEqRangeLow.setMax(12);
        sbTteEqRangeLow.setProgress(sbTteEqRangeLowValue[0]);
        sbTteEqRangeLow.refreshDrawableState();
        tvTteEqRangeLowVal.setText(String.valueOf(sbTteEqRangeLowValue[0]));

        //Set some default settings for random seek bar
        sbTteRandom.setMin(0);
        sbTteRandom.setMax(1);

        if (sbTteRandomValue[0] == false) {
            sbTteRandom.setProgress(0);
            sbTteRandom.refreshDrawableState();
            tvTteRandomVal.setText(String.valueOf('N'));
        }
        else {
            sbTteRandom.setProgress(1);
            sbTteRandom.refreshDrawableState();
            tvTteRandomVal.setText(String.valueOf('Y'));
        }

        //Setup Handlers for the sbTteEqRangeHigh
        sbTteEqRangeHigh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * Act for onProgressChanged
             *
             * * Save value directly into sbTteEqRangeHighValue so it can be saved for future use
             * * Multiply value by 2 and render in tvTteEqRangeHighVal so we know the current value
             *
             * @param seekBar  seekBar object
             * @param progress actual value from the seek bar
             * @param fromUser from user
             */
            @Override
            public void onProgressChanged (
                    SeekBar seekBar,
                    int progress,
                    boolean fromUser
            ) {
                sbTteEqRangeHighValue[0] = progress;
                tvTteEqRangeHighVal.setText(String.valueOf(sbTteEqRangeHighValue[0]));

                //We need to make sure High range is never less than low range
                if (sbTteEqRangeLowValue[0] > progress) {
                    sbTteEqRangeLow.setProgress(progress);
                    sbTteEqRangeLow.refreshDrawableState();
                    tvTteEqRangeLowVal.setText(String.valueOf(progress));
                }
            }

            /**
             * Act on onStartTrackingTouch. Just render the current value as stored in sbTteEqRangeValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

                tvTteEqRangeHighVal.setText(String.valueOf(sbTteEqRangeHighValue[0]));
            }

            /**
             * Act on onStopTrackingTouch. No action needs to be taken
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Setup Handlers for the sbTteEqRangeLow
        sbTteEqRangeLow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * Act for onProgressChanged
             *
             * * Save value directly into sbTteEqRangeLowValue so it can be saved for future use
             * * Multiply value by 2 and render in tvTteEqRangeLowVal so we know the current value
             *
             * @param seekBar  seekBar object
             * @param progress actual value from the seek bar
             * @param fromUser from user
             */
            @Override
            public void onProgressChanged (
                    SeekBar seekBar,
                    int progress,
                    boolean fromUser
            ) {
                sbTteEqRangeLowValue[0] = progress;
                tvTteEqRangeLowVal.setText(String.valueOf(sbTteEqRangeLowValue[0]));

                //We need to make sure High range is never less than low range
                if (sbTteEqRangeHighValue[0] < progress) {
                    sbTteEqRangeHigh.setProgress(progress);
                    sbTteEqRangeHigh.refreshDrawableState();
                    tvTteEqRangeHighVal.setText(String.valueOf(progress));
                }
            }

            /**
             * Act on onStartTrackingTouch. Just render the current value as stored in sbTteEqRangeValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

                tvTteEqRangeLowVal.setText(String.valueOf(sbTteEqRangeLowValue[0]));
            }

            /**
             * Act on onStopTrackingTouch. No action needs to be taken
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Setup Handlers for the sbTteRandom
        sbTteRandom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * * Act for onProgressChanged
             *
             * * Save boolean equivalent value directly into sbTteRandomValue so it can be saved for future use
             * * Render N for no and Y for yes in tvTteRandomVal so what option we have selected
             *
             * @param seekBar  seekBar object
             * @param progress actual value from the seek bar
             * @param fromUser from user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    sbTteRandomValue[0] = false;
                    tvTteRandomVal.setText("N");
                }
                else {
                    sbTteRandomValue[0] = true;
                    tvTteRandomVal.setText("Y");
                }
            }

            /**
             * Act on onStartTrackingTouch. Either a Y or N based on value in sbTteRandomValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (sbTteRandomValue[0] == false) {
                    tvTteRandomVal.setText("N");
                }
                else {
                    tvTteRandomVal.setText("Y");
                }
            }

            /**
             * Act on onStopTrackingTouch. No action needs to be taken
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Saves parameters as configured in calcSettings so they can be loaded for later use.
     *
     * @param view Required for onClick
     */
    public void saveSettings (View view) {

        //Get flipper resource. Once saved, we need to flip it back to times tables
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        TextView tvCalcHeader = (TextView)findViewById(R.id.tvCalcHeader);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);
        TextView tvRenderCount = (TextView) findViewById(R.id.tvRenderCount);
        Button btnBegin = (Button) findViewById(R.id.btnBegin);

        //Save settings and commit them
        SharedPreferences.Editor spe = getSharedPreferences(mathsMateSettings,0).edit();
        spe.putInt("tte_eq_range_high", sbTteEqRangeHighValue[0]);
        spe.putInt("tte_eq_range_low", sbTteEqRangeLowValue[0]);
        spe.putBoolean("tte_eq_random", sbTteRandomValue[0]);
        spe.apply(); //Using apply over commit

        //Notify settings saved
        Toast.makeText(getApplicationContext(), getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();

        //Reset buttons and displayed child so we can start the equations again
        saveSettings.setVisibility(View.GONE);
        openSettings.setVisibility(View.VISIBLE);
        tvRenderCount.setVisibility(View.GONE);
        btnBegin.setVisibility(View.VISIBLE);
        setCalcHeader(tvCalcHeader);
        incBody.setDisplayedChild(0);
    }
}
