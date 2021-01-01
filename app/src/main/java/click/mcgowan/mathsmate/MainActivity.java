package click.mcgowan.mathsmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import click.mcgowan.mathsmate.core.AddSubMulDivEquations;

/**
 * Class for Main Activity
 *
 * Main Activity is effectively the main menu and also gives the users the ability to select a profile
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Render the Main Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Create intent and start Addition, Subtraction, Multiplication, Division Activity
     *
     * @param view
     */
    public void startTorTimestablesActivity (View view) {

        Intent intentTorTimestablesActivity = new Intent(this, CalcTorActivityTimesTables.class);

        //Eventual code to setup and pass in profile
        intentTorTimestablesActivity.putExtra("action_id", "tortimestables");

        startActivity(intentTorTimestablesActivity);
    }

    /**
     * Create intent and start Addition, Subtraction, Multiplication, Division Activity
     *
     * @param view
     */
    public void startTorAddSubMulDivActivity (View view) {

        Intent intentTorAddSuMulDivActivity = new Intent(this, CalcTorActivityAddSubMulDiv.class);

        //Eventual code to setup and pass in profile
        intentTorAddSuMulDivActivity.putExtra("action_id", "toraddsubmuldiv");

        startActivity(intentTorAddSuMulDivActivity);
    }

    /**
     * Create intent and start Times Table Calculator Activity
     *
     * @param view
     */
    public void startTimesTableCalcActivity (View view) {

        Intent intentTimesTablesCalcActivity = new Intent(this, CalcActivityTimesTables.class);

        //Eventual code to setup and pass in profile
        intentTimesTablesCalcActivity.putExtra("action_id", "timestables");

        startActivity(intentTimesTablesCalcActivity);
    }

    /**
     * Create intent and start Addition, Subtraction, Multiplication, Division Activity
     *
     * @param view
     */
    public void startAddSubMulDivActivity (View view) {

        Intent intentAddSuMulDivActivity = new Intent(this, CalcActivityAddSubMulDiv.class);

        //Eventual code to setup and pass in profile
        intentAddSuMulDivActivity.putExtra("action_id", "addsubmuldiv");

        startActivity(intentAddSuMulDivActivity);
    }
}
