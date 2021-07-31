package android.example.workoutapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Program1Done extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int number;
    int kcal;
    private int squat_numberrequired;
    private int jumpingjack_numberrequired;
    private  int  programduration;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1_done);
        Bundle extras = getIntent().getExtras();
        squat_numberrequired = Integer.parseInt(extras.getString("squat_numberrequired"));
        jumpingjack_numberrequired = Integer.parseInt(extras.getString("jumpingjack_numberrequired"));
        programduration = Integer.parseInt(extras.getString("programduration"));
        number = squat_numberrequired + jumpingjack_numberrequired;

        kcal = (int)(squat_numberrequired * 0.3 + (jumpingjack_numberrequired * 0.2));

        ((TextView)findViewById(R.id.textView5)).setText("CARDIO PROGRAM DONE");

        if (programduration > 59){
            ((TextView)findViewById(R.id.textView9)).setText("You just did " + jumpingjack_numberrequired + " jumping jacks and "+
                    squat_numberrequired  +" Squats in " +
                    (int)(programduration/60) +" Minutes and " + programduration%60 +" seconds.");
        }
        else{
            ((TextView)findViewById(R.id.textView9)).setText("You just did " + jumpingjack_numberrequired + " jumping jacks and "  +
                    squat_numberrequired  + " Squats in " +
                    programduration +" seconds.");
        }

        ((TextView)findViewById(R.id.textView14)).setText("");
        ((TextView)findViewById(R.id.textView11)).setText("You burned " +kcal+" kcal.\n");

        // Creation of the SharedPreferences
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

        Button donebutton = (Button) findViewById(R.id.button);
        donebutton.setOnClickListener(view -> {
            SavingUserDataUtil obj = new SavingUserDataUtil();
            obj.saveDataWorkout(view, programduration, kcal, number, sharedPreferences);
            openNewActivity();
        });


    }

    public void openNewActivity(){
        Intent intent = new Intent(this, ListWorkoutPrograms.class);
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