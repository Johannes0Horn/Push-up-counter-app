package android.example.workoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Exercise1ActivityDone extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int duration;
    int number;
    int kcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1_done);
        Bundle extras = getIntent().getExtras();
        duration = Integer.parseInt(extras.getString("duration"));
        number = Integer.parseInt(extras.getString("number"));
        kcal = (int)(number * 0.5);

        if (duration > 59){
            ((TextView)findViewById(R.id.textView9)).setText("You just did " + number + " push-ups in " +
                    (int)(duration/60) +" Minutes and " + duration%60 +" seconds.");
        }
        else{
            ((TextView)findViewById(R.id.textView9)).setText("You just did " + number + " push-ups in " + duration +" seconds.");
        }
        int average  = 0;
        if (duration==0){
            average = 0;
        }else{
            average = (60*number)/duration;
        }
        ((TextView)findViewById(R.id.textView14)).setText("That's "+ average +" pushups per minute on average.");
        ((TextView)findViewById(R.id.textView11)).setText("You burned " +kcal+" kcal.\n");

        // Creation of the SharedPreferences
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

        Button donebutton = (Button) findViewById(R.id.button);
        donebutton.setOnClickListener(view -> {
            SavingUserDataUtil obj = new SavingUserDataUtil();
            obj.saveDataWorkout(view, duration, kcal, number, sharedPreferences);
            openNewActivity();
        });


    }

    public void openNewActivity(){
        //Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, ListExercisesActivity.class);
        startActivity(intent);
    }

/*    public void saveDataWorkout(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String newDuration = String.valueOf(duration + Integer.valueOf(sharedPreferences.getString("duration", "")));
        String newKcal = String.valueOf(kcal + Integer.valueOf(sharedPreferences.getString("kcal", "")));
        String newNumber = String.valueOf(number + Integer.valueOf(sharedPreferences.getString("numberWorkout", "")));

        editor.putString("duration", newDuration);
        editor.putString("kcal", newKcal);
        editor.putString("numberWorkout", newNumber);
        editor.commit();
    }*/
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