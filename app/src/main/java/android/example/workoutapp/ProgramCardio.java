package android.example.workoutapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProgramCardio extends AppCompatActivity {

    ImageButton startjumpingjack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_cardio);
        startjumpingjack = findViewById(R.id.playButton);
        startjumpingjack.setOnClickListener(view -> openNewActivity());
    }
    public void openNewActivity() {
        //Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, Exercise_jumpingjack.class);
        intent.putExtra("partOfProgram", "yes");
        intent.putExtra("jumpingjack_numberrequired", ((EditText)findViewById(R.id.editTextNumber2)).getText().toString());
        intent.putExtra("squat_numberrequired", ((EditText)findViewById(R.id.editTextNumber)).getText().toString());
        startActivity(intent);
    }


}









