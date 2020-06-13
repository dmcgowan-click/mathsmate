package click.mcgowan.mathsmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
     * Create intent and start Times Table Calculator Activity
     * Determine action to perform based on button ID
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
     * Determine action to perform based on button ID
     *
     * @param view
     */
    public void startAddSubMulDivActivity (View view) {

        Intent intentAddSuMulDivActivity = new Intent(this, CalcActivityAddSubMulDiv.class);

        //Eventual code to setup and pass in profile
        intentAddSuMulDivActivity.putExtra("action_id", "addsubmuldiv");

        startActivity(intentAddSuMulDivActivity);
    }

    /**
     * TEMP METHOD FOR DEBUGGING
     */
    public void startDebug (View view) {

        String whatwegot="";

        AddSubMulDivEquations equations = new AddSubMulDivEquations(
                5,
                3,
                10,
                0,
                false,
                new char[]{'+','-','*'});

//        for (int counter = 0; counter < equations.; counter++) {
//
//            whatwegot = whatwegot + "Operand and Operator : " + counter + "; " + String.valueOf(equation.getOperandNextIndex()) + " and " + String.valueOf(equation.getOperatorForIndex(counter)) + ";; ";
//        }
//        Toast.makeText(getApplicationContext(),whatwegot,Toast.LENGTH_LONG).show();
    }

    /**
     * Open master settings activity for settings that effect the whole program
     *
     * @param view
     */
    public void masterSettings (View view) {

        Toast.makeText(getApplicationContext(), "Under Construction",Toast.LENGTH_LONG).show();
    }
}
