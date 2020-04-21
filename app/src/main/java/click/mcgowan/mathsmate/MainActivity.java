package click.mcgowan.mathsmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
     * Create intent and start Calculator Activity
     * Determine action to perform based on button ID
     *
     * @param view
     */
    public void startTimesTableCalcActivity (View view) {

        Intent intentTimesTablesCalcActivity = new Intent(this, TimesTablesCalcActivity.class);

        //Eventual code to setup and pass in profile
        intentTimesTablesCalcActivity.putExtra("action_id", "timestables");

        startActivity(intentTimesTablesCalcActivity);
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
