package android.example.workoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Exercise4ActivityDone extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1_done);
        Bundle extras = getIntent().getExtras();
        int duration = Integer.parseInt(extras.getString("duration"));
        int kcal = (int)(duration * 0.1);

        ((TextView)findViewById(R.id.textView5)).setText("PLANK DONE");

        if (duration > 59){
            ((TextView)findViewById(R.id.textView9)).setText("You just did " +
                    (int)(duration/60) +" Minutes and " + duration%60 +" seconds"+ " of plank. ");
        }
        else{
            ((TextView)findViewById(R.id.textView9)).setText("You just did " +  duration +" seconds"+ "of plank.");
        }
        //no overgae per minute becuase cannot be coutned
        ((TextView)findViewById(R.id.textView14)).setText("");
        ((TextView)findViewById(R.id.textView11)).setText("You burned " +kcal+" kcal.\n");

        // Creation of the SharedPreferences
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

        Button donebutton = (Button) findViewById(R.id.button);
        donebutton.setOnClickListener(view -> {
            SavingUserDataUtil obj = new SavingUserDataUtil();
            obj.saveDataWorkout(view, duration, kcal, 0, sharedPreferences);
            openNewActivity();
        });

    }

    public void openNewActivity(){
        //Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, ListExercisesActivity.class);
        startActivityForResult(intent, 1);
    }
/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String incMessage = data.getStringExtra("message");
                Toast.makeText(getApplicationContext(), incMessage, Toast.LENGTH_LONG).show();

            }
        }
    }
*/
}