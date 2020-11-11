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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import click.mcgowan.mathsmate.core.AddSubMulDivEquations;
import click.mcgowan.mathsmate.core.Equations;
import click.mcgowan.mathsmate.core.TimesTablesEquations;

/**
 * Class for Addition, Subtraction, Multiplication, Division activities. Extends CalcActivity. Uses the following resources
 *
 * * settings_addsubmuldiv.xml Layout for the Times Tables settings form. Lets you adjust parameters for Addition, Subtraction, Multiplication, Division equations
 *
 * Specific details about the abstract classes are documented as per normal
 */
public class CalcActivityAddSubMulDiv extends CalcActivity{

    //Add Sub Mul Div Parameters
    final int[] sbAsmdEqCountValue = new int[1];
    final int[] sbAsmdOpCountValue = new int[1];
    final int[] sbAsmdRangeCountValue = new int[1];
    final boolean[] sbAsmdAddValue = new boolean[1];
    final boolean[] sbAsmdSubValue = new boolean[1];
    final boolean[] sbAsmdMulValue = new boolean[1];
    final boolean[] sbAsmdDivValue = new boolean[1];

    /**
     * Load parameters or set defaults if they don't exist so they can be provided to the desired Equations class
     *
     * For addition, subtraction, multiplication and division, this includes:
     *
     * * sbAsmdEqCountValue    - number of equations
     * * sbAsmdOpCountValue    - number of operands
     * * sbAsmdRangeCountValue - range of operands
     * * sbAsmdAddValue        - addition operators included
     * * sbAsmdSubValue        - subtraction operators included
     * * sbAsmdMulValue        - multiplication operators included
     * * sbAsmdDivValue        - division operators included
     *
     */
    void setParameters() {

        SharedPreferences spr = getSharedPreferences(mathsMateSettings,0);

        sbAsmdEqCountValue[0] = spr.getInt("asmd_eq_count", 1);
        sbAsmdOpCountValue[0] = spr.getInt("asmd_op_count", 2);
        sbAsmdRangeCountValue[0] = spr.getInt("asmd_op_range", 5);
        sbAsmdAddValue[0] = spr.getBoolean("asmd_add", true);
        sbAsmdSubValue[0] = spr.getBoolean("asmd_sub", false);
        sbAsmdMulValue[0] = spr.getBoolean("asmd_mul", false);
        sbAsmdDivValue[0] = spr.getBoolean("asmd_div", false);
    }

    /**
     * Set the desired header to be the following value:
     *
     * getString(R.string.addsubmuldiv)
     *
     * @param calcHeader TextView for the calculation header
     */
    void setCalcHeader(TextView calcHeader) {

        calcHeader.setText(getString(R.string.addsubmuldiv));
    }

    /**
     * Create TimesTablesEquations object and use the following parameters:
     *
     * * sbAsmdEqCountValue    - number of equations
     * * sbAsmdOpCountValue    - number of operands
     * * sbAsmdRangeCountValue - range of operands
     * * sbAsmdAddValue        - addition operators included
     * * sbAsmdSubValue        - subtraction operators included
     * * sbAsmdMulValue        - multiplication operators included
     * * sbAsmdDivValue        - division operators included
     *
     */
    void genNewEquations () {

        char operators[];
        List<Character> operatorsList = new ArrayList<Character>();

        //Read parameters to determine desired operators
        if (sbAsmdAddValue[0] == true) {
            operatorsList.add('+');
        }
        if (sbAsmdSubValue[0] == true) {
            operatorsList.add('-');
        }
        if (sbAsmdMulValue[0] == true) {
            operatorsList.add('*');
        }
        if (sbAsmdDivValue[0] == true) {
            operatorsList.add('/');
        }

        //This garbage takes the values and adds them to a regular char array. If there is a better way of doing this, please let me know!
        operators = new char[operatorsList.size()];

        for (int counter = 0; counter < operatorsList.size(); counter++) {
            operators[counter] = operatorsList.get(counter);
        }

        AddSubMulDivEquations asmds = new AddSubMulDivEquations(
                sbAsmdEqCountValue[0] * 10,
                sbAsmdOpCountValue[0],
                sbAsmdRangeCountValue[0],
                0, //not yet customisable
                false, //not yet customisable
                operators);

        equations = (Equations) asmds;
    }

    /**
     * Render a form using a resource of settings_addsubmuldiv.xml so parameters for AddSubMulDivEquations can be customised
     *
     * @param view Required for onClick
     */
    public void calcSettings (View view) {

        //Setup flipper and add settings_addsubmuldiv.xml to it. All other element setting will fail otherwise
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        View childLayout = getLayoutInflater().inflate(R.layout.settings_addsubmuldiv, null);
        incBody.addView(childLayout);
        incBody.setDisplayedChild(3);

        //Setup elements common to all settings
        TextView calcHeader = (TextView) findViewById(R.id.tvCalcHeader);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);

        //Setup elements common to settings_addsubmuldiv.xml
        SeekBar sbAsmdEqCount = (SeekBar)findViewById(R.id.sbAsmdEqCount);
        final TextView tvAsmdEqCountVal = (TextView)findViewById(R.id.tvAsmdEqCountVal);
        SeekBar sbAsmdOpCount = (SeekBar)findViewById(R.id.sbAsmdOpCount);
        final TextView tvAsmdOpCountVal = (TextView) findViewById(R.id.tvAsmdOpCountVal);
        SeekBar sbAsmdRangeCount = (SeekBar)findViewById(R.id.sbAsmdRangeCount);
        final TextView tvAsmdRangeCountVal = (TextView) findViewById(R.id.tvAsmdRangeCountVal);
        final SeekBar sbAsmdAdd = (SeekBar)findViewById(R.id.sbAsmdAdd);
        final TextView tvAsmdAddVal = (TextView) findViewById(R.id.tvAsmdAddVal);
        SeekBar sbAsmdSub = (SeekBar)findViewById(R.id.sbAsmdSub);
        final TextView tvAsmdSubVal = (TextView) findViewById(R.id.tvAsmdSubVal);
        SeekBar sbAsmdMul = (SeekBar)findViewById(R.id.sbAsmdMul);
        final TextView tvAsmdMulVal = (TextView) findViewById(R.id.tvAsmdMulVal);
        SeekBar sbAsmdDiv = (SeekBar)findViewById(R.id.sbAsmdDiv);
        final TextView tvAsmdDivVal = (TextView) findViewById(R.id.tvAsmdDivVal);

        //Set header and save button
        calcHeader.setText(R.string.settings_addsubmuldiv);
        openSettings.setVisibility(View.GONE);
        saveSettings.setVisibility(View.VISIBLE);

        //Set some default settings for range seek bars
        sbAsmdEqCount.setMin(1);
        sbAsmdEqCount.setMax(100 / 10); //We want to increment this in values of 10. So first we actually reduce the range by half
        sbAsmdEqCount.setProgress(sbAsmdEqCountValue[0]);
        sbAsmdEqCount.refreshDrawableState();
        tvAsmdEqCountVal.setText(String.valueOf(sbAsmdEqCountValue[0] * 10));

        sbAsmdOpCount.setMin(2);
        sbAsmdOpCount.setMax(3); //Will eventually allow for more
        sbAsmdOpCount.setProgress(sbAsmdOpCountValue[0]);
        sbAsmdOpCount.refreshDrawableState();
        tvAsmdOpCountVal.setText(String.valueOf(sbAsmdOpCountValue[0]));

        sbAsmdRangeCount.setMin(5);
        sbAsmdRangeCount.setMax(20); //Will eventually allow for more
        sbAsmdRangeCount.setProgress(sbAsmdRangeCountValue[0]);
        sbAsmdRangeCount.refreshDrawableState();
        tvAsmdRangeCountVal.setText(String.valueOf(sbAsmdRangeCountValue[0]));

        //Set some defaults settings for operator seek bars
        sbAsmdAdd.setMin(0);
        sbAsmdAdd.setMax(1);

        if (sbAsmdAddValue[0] == false) {
            sbAsmdAdd.setProgress(0);
            sbAsmdAdd.refreshDrawableState();
            tvAsmdAddVal.setText(String.valueOf('N'));
        }
        else {
            sbAsmdAdd.setProgress(1);
            sbAsmdAdd.refreshDrawableState();
            tvAsmdAddVal.setText(String.valueOf('Y'));
        }

        sbAsmdSub.setMin(0);
        sbAsmdSub.setMax(1);

        if (sbAsmdSubValue[0] == false) {
            sbAsmdSub.setProgress(0);
            sbAsmdSub.refreshDrawableState();
            tvAsmdSubVal.setText(String.valueOf('N'));
        }
        else {
            sbAsmdSub.setProgress(1);
            sbAsmdSub.refreshDrawableState();
            tvAsmdSubVal.setText(String.valueOf('Y'));
        }

        sbAsmdMul.setMin(0);
        sbAsmdMul.setMax(1);

        if (sbAsmdMulValue[0] == false) {
            sbAsmdMul.setProgress(0);
            sbAsmdMul.refreshDrawableState();
            tvAsmdMulVal.setText(String.valueOf('N'));
        }
        else {
            sbAsmdMul.setProgress(1);
            sbAsmdMul.refreshDrawableState();
            tvAsmdMulVal.setText(String.valueOf('Y'));
        }

        sbAsmdDiv.setMin(0);
        sbAsmdDiv.setMax(1);

        if (sbAsmdDivValue[0] == false) {
            sbAsmdDiv.setProgress(0);
            sbAsmdDiv.refreshDrawableState();
            tvAsmdDivVal.setText(String.valueOf('N'));
        }
        else {
            sbAsmdDiv.setProgress(1);
            sbAsmdDiv.refreshDrawableState();
            tvAsmdDivVal.setText(String.valueOf('Y'));
        }

        //Setup Handlers for the sbAsmdEqCount
        sbAsmdEqCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * Act for onProgressChanged
             *
             * * Save value directly into sbAsmdEqCountValue so it can be saved for future use
             * * Multiply value by 10 and render in tvAsmdEqCountVal so we know the current value
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
                sbAsmdEqCountValue[0] = progress;
                tvAsmdEqCountVal.setText(String.valueOf(sbAsmdEqCountValue[0] * 10));
            }

            /**
             * Act on onStartTrackingTouch. Just render the current value as stored in sbAsmdEqCountValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

                tvAsmdEqCountVal.setText(String.valueOf(sbAsmdEqCountValue[0] * 10));
            }

            /**
             * Act on onStopTrackingTouch. No action needs to be taken
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Setup Handlers for the sbAsmdOpCount
        sbAsmdOpCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * Act for onProgressChanged
             *
             * * Save value directly into sbAsmdOpCountValue so it can be saved for future use
             * * Multiply value by 10 and render in tvAsmdOpCountVal so we know the current value
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
                sbAsmdOpCountValue[0] = progress;
                tvAsmdOpCountVal.setText(String.valueOf(sbAsmdOpCountValue[0]));
            }

            /**
             * Act on onStartTrackingTouch. Just render the current value as stored in sbAsmdOpCountValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

                tvAsmdOpCountVal.setText(String.valueOf(sbAsmdOpCountValue[0]));
            }

            /**
             * Act on onStopTrackingTouch. No action needs to be taken
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Setup Handlers for the sbAsmdRangeCount
        sbAsmdRangeCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * Act for onProgressChanged
             *
             * * Save value directly into sbAsmdRangeCountValue so it can be saved for future use
             * * Multiply value by 10 and render in tvAsmdRangeCountVal so we know the current value
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
                sbAsmdRangeCountValue[0] = progress;
                tvAsmdRangeCountVal.setText(String.valueOf(sbAsmdRangeCountValue[0]));
            }

            /**
             * Act on onStartTrackingTouch. Just render the current value as stored in sbAsmdRangeCountValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch (SeekBar seekBar) {

                tvAsmdRangeCountVal.setText(String.valueOf(sbAsmdRangeCountValue[0]));
            }

            /**
             * Act on onStopTrackingTouch. No action needs to be taken
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Setup Handlers for the sbAsmdAdd
        sbAsmdAdd.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * * Act for onProgressChanged
             *
             * * Save boolean equivalent value directly into sbAsmdAddValue so it can be saved for future use
             * * Render N for no and Y for yes in tvAsmdAddVal so what option we have selected
             *
             * @param seekBar  seekBar object
             * @param progress actual value from the seek bar
             * @param fromUser from user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    sbAsmdAddValue[0] = false;
                    tvAsmdAddVal.setText("N");
                }
                else {
                    sbAsmdAddValue[0] = true;
                    tvAsmdAddVal.setText("Y");
                }
            }

            /**
             * Act on onStartTrackingTouch. Either a Y or N based on value in sbAsmdAddValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (sbAsmdAddValue[0] == false) {
                    tvAsmdAddVal.setText("N");
                }
                else {
                    tvAsmdAddVal.setText("Y");
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

        //Setup Handlers for the sbAsmdSub
        sbAsmdSub.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * * Act for onProgressChanged
             *
             * * Save boolean equivalent value directly into sbAsmdSubValue so it can be saved for future use
             * * Render N for no and Y for yes in tvAsmdSubVal so what option we have selected
             * * In event sbAsmdSub, sbAsmdMul, sbAsmdMul and sbAsmdDiv are false, force sbAsmdAdd to true (or we have no operators!)
             *
             * @param seekBar  seekBar object
             * @param progress actual value from the seek bar
             * @param fromUser from user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    sbAsmdSubValue[0] = false;
                    tvAsmdSubVal.setText("N");
                }
                else {
                    sbAsmdSubValue[0] = true;
                    tvAsmdSubVal.setText("Y");
                }

                if (sbAsmdSubValue[0] == false && sbAsmdMulValue[0] == false && sbAsmdDivValue[0] == false) {
                    sbAsmdAdd.setProgress(1);
                    sbAsmdAdd.refreshDrawableState();
                    tvAsmdAddVal.setText(String.valueOf('Y'));
                }
            }

            /**
             * Act on onStartTrackingTouch. Either a Y or N based on value in sbAsmdSubValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (sbAsmdSubValue[0] == false) {
                    tvAsmdSubVal.setText("N");
                }
                else {
                    tvAsmdSubVal.setText("Y");
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

        //Setup Handlers for the sbAsmdMul
        sbAsmdMul.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * * Act for onProgressChanged
             *
             * * Save boolean equivalent value directly into sbAsmdMulValue so it can be saved for future use
             * * Render N for no and Y for yes in tvAsmdMulVal so what option we have selected
             *
             * @param seekBar  seekBar object
             * @param progress actual value from the seek bar
             * @param fromUser from user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    sbAsmdMulValue[0] = false;
                    tvAsmdMulVal.setText("N");
                }
                else {
                    sbAsmdMulValue[0] = true;
                    tvAsmdMulVal.setText("Y");
                }

                if (sbAsmdSubValue[0] == false && sbAsmdMulValue[0] == false && sbAsmdDivValue[0] == false) {
                    sbAsmdAdd.setProgress(1);
                    sbAsmdAdd.refreshDrawableState();
                    tvAsmdAddVal.setText(String.valueOf('Y'));
                }
            }

            /**
             * Act on onStartTrackingTouch. Either a Y or N based on value in sbAsmdMulValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (sbAsmdMulValue[0] == false) {
                    tvAsmdMulVal.setText("N");
                }
                else {
                    tvAsmdSubVal.setText("Y");
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

        //Setup Handlers for the sbAsmdDiv
        sbAsmdDiv.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * * Act for onProgressChanged
             *
             * * Save boolean equivalent value directly into sbAsmdDivValue so it can be saved for future use
             * * Render N for no and Y for yes in tvAsmdDivVal so what option we have selected
             *
             * @param seekBar  seekBar object
             * @param progress actual value from the seek bar
             * @param fromUser from user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    sbAsmdDivValue[0] = false;
                    tvAsmdDivVal.setText("N");
                }
                else {
                    sbAsmdDivValue[0] = true;
                    tvAsmdDivVal.setText("Y");
                }

                if (sbAsmdSubValue[0] == false && sbAsmdMulValue[0] == false && sbAsmdDivValue[0] == false) {
                    sbAsmdAdd.setProgress(1);
                    sbAsmdAdd.refreshDrawableState();
                    tvAsmdAddVal.setText(String.valueOf('Y'));
                }
            }

            /**
             * Act on onStartTrackingTouch. Either a Y or N based on value in sbAsmdDivValue
             *
             * @param seekBar seekBar object
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                if (sbAsmdDivValue[0] == false) {
                    tvAsmdDivVal.setText("N");
                }
                else {
                    tvAsmdDivVal.setText("Y");
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
     * Triggered by btnCalcSaveSettings
     *
     * Save settings from times tables configuration from to shared preference
     *
     * @param view
     */
    public void saveSettings (View view) {

        //Get flipper resource. We need to flip it. Get it!
        ViewFlipper incBody = (ViewFlipper)findViewById(R.id.incBody);
        TextView tvCalcHeader = (TextView)findViewById(R.id.tvCalcHeader);
        ImageButton openSettings = (ImageButton) findViewById(R.id.btnCalcSettings);
        Button saveSettings = (Button) findViewById(R.id.btnCalcSettingsSave);
        TextView tvRenderCount = (TextView) findViewById(R.id.tvRenderCount);
        Button btnBegin = (Button) findViewById(R.id.btnBegin);

        //Save settings and commit them
        SharedPreferences.Editor spe = getSharedPreferences(mathsMateSettings,0).edit();
        spe.putInt("asmd_eq_count", sbAsmdEqCountValue[0]);
        spe.putInt("asmd_op_count", sbAsmdOpCountValue[0]);
        spe.putInt("asmd_op_range", sbAsmdRangeCountValue[0]);
        spe.putBoolean("asmd_add", sbAsmdAddValue[0]);
        spe.putBoolean("asmd_sub", sbAsmdSubValue[0]);
        spe.putBoolean("asmd_mul", sbAsmdMulValue[0]);
        spe.putBoolean("asmd_div", sbAsmdDivValue[0]);
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
