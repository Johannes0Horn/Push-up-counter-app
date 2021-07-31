package android.example.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProgramAbs extends AppCompatActivity {

    ImageButton startpushups;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_abs);
        ImageButton startpushups = findViewById(R.id.playButton);
        startpushups.setOnClickListener(view -> openNewActivity());

    }
    public void openNewActivity(){
        Intent intent = new Intent(this, PushupActivity.class);
        intent.putExtra("partOfProgram", "yes");
        intent.putExtra("pushups_numberrequired", ((EditText)findViewById(R.id.editTextNumber)).getText().toString());
        intent.putExtra("plank_durationreequired", ((EditText)findViewById(R.id.editTextNumber2)).getText().toString());
        startActivity(intent);
    }
}