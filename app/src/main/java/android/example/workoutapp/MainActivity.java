package android.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Drawer resultDrawer;
    private TextView userWeight;
    private EditText height;
    private EditText weight;
    private TextView result;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);
        TextView workout = (TextView) findViewById(R.id.workoutID);
        TextView kcal = (TextView) findViewById(R.id.kcalID);
        TextView minute = (TextView) findViewById(R.id.minuteID);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // DrawerUtil.getDrawer(this, toolbar);

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("List exercises");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Records");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Programs");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Preferences");

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_background)
                .build();


        //create the drawer and remember the `Drawer` result object
        resultDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(2)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = null;
                        if (drawerItem.getIdentifier() == 1) {
                            intent = new Intent(MainActivity.this, ListExercisesActivity.class);
                        } else if (drawerItem.getIdentifier() == 2) {
                            // intent = new Intent(MainActivity.this, MainActivity.class);
                        } else if (drawerItem.getIdentifier() == 3) {
                            intent = new Intent(MainActivity.this, ListWorkoutPrograms.class);
                        }
                        else if (drawerItem.getIdentifier() == 4) {
                            intent = new Intent(MainActivity.this, PreferencesActivity.class);
                        }
                        if (intent != null) {
                            MainActivity.this.startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        }
                        return false;
                    }
                })
                .build();

        // Creation of the SharedPreferences
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        if (sharedPreferences.contains("heightUser")) {
            height.setText(sharedPreferences.getString("heightUser", ""));
        }
        if (sharedPreferences.contains("weightUser")) {
            weight.setText(sharedPreferences.getString("weightUser", ""));
        }
        if (sharedPreferences.contains("BMI")) {
            result.setText(sharedPreferences.getString("BMI", ""));
        }
        if (sharedPreferences.contains("numberWorkout")) {
            workout.setText(sharedPreferences.getString("numberWorkout", ""));
        }
        if (sharedPreferences.contains("kcal")) {
            kcal.setText(sharedPreferences.getString("kcal", ""));
        }
        if (sharedPreferences.contains("duration")) {
            int nbMinutes = (int) Integer.parseInt(sharedPreferences.getString("duration", ""))/ 60;
            int nbSeconds = Integer.parseInt(sharedPreferences.getString("duration", "")) % 60;

            String nbminutesStr = String.valueOf(nbMinutes), nbSecondsStr = String.valueOf(nbSeconds);

            if (String.valueOf(nbMinutes).length() == 1) {
                nbminutesStr = "0" + nbminutesStr;
            }
            if (String.valueOf(nbSeconds).length() == 1) {
                nbSecondsStr = "0" + nbSecondsStr;
            }

            minute.setText(nbminutesStr + ":" + nbSecondsStr);
        }

    }

/*    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }*/

    public void calculateBMI(View v) {
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        if (heightStr != null && !"".equals(heightStr)
                && weightStr != null  &&  !"".equals(weightStr)) {
            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);

            float bmi = weightValue / (heightValue * heightValue);

            displayBMI(bmi);
        }
    }

    private void displayBMI(float bmi) {
        String bmiLabel = "";

        if (Float.compare(bmi, 15f) <= 0) {
            bmiLabel = getString(R.string.very_severely_underweight);
        } else if (Float.compare(bmi, 15f) > 0  &&  Float.compare(bmi, 16f) <= 0) {
            bmiLabel = getString(R.string.severely_underweight);
        } else if (Float.compare(bmi, 16f) > 0  &&  Float.compare(bmi, 18.5f) <= 0) {
            bmiLabel = getString(R.string.underweight);
        } else if (Float.compare(bmi, 18.5f) > 0  &&  Float.compare(bmi, 25f) <= 0) {
            bmiLabel = getString(R.string.normal);
        } else if (Float.compare(bmi, 25f) > 0  &&  Float.compare(bmi, 30f) <= 0) {
            bmiLabel = getString(R.string.overweight);
        } else if (Float.compare(bmi, 30f) > 0  &&  Float.compare(bmi, 35f) <= 0) {
            bmiLabel = getString(R.string.obese_class_i);
        } else if (Float.compare(bmi, 35f) > 0  &&  Float.compare(bmi, 40f) <= 0) {
            bmiLabel = getString(R.string.obese_class_ii);
        } else {
            bmiLabel = getString(R.string.obese_class_iii);
        }

        bmiLabel = bmi + "\n\n" + bmiLabel;
        result.setText(bmiLabel);
    }

    public void saveData(View view){
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        if (heightStr != null && !"".equals(heightStr)
                && weightStr != null  &&  !"".equals(weightStr)) {
            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);

            float bmi = weightValue / (heightValue * heightValue);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("heightUser", heightStr);
            editor.putString("weightUser", weightStr);
            editor.putString("BMI", String.valueOf(bmi));
            editor.commit();

        }

    }

    public void clear(View view) {
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);

        height.setText("");
        weight.setText("");
        result.setText("");
    }

    public void getData(View view) {
        float defValue = 0;
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

        if (sharedPreferences.contains("heightUser")) {
            height.setText(sharedPreferences.getString("heightUser", ""));
        }
        if (sharedPreferences.contains("weightUser")) {
            weight.setText(sharedPreferences.getString("weightUser", ""));
        }
        if (sharedPreferences.contains("BMI")) {
            result.setText(sharedPreferences.getString("BMI", ""));
        }
    }

    @Override
    public void onBackPressed(){
        if (resultDrawer != null && resultDrawer.isDrawerOpen()){
            resultDrawer.closeDrawer();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
    }

}