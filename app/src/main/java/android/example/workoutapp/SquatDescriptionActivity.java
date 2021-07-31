package android.example.workoutapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SquatDescriptionActivity extends AppCompatActivity {
    ImageButton startsquat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squat_description);

        startsquat = findViewById(R.id.playButton);
        startsquat.setOnClickListener(view -> openNewActivity());


    }

    public void openNewActivity() {
        //Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, Exercise_squat.class);
        intent.putExtra("partOfProgram", "no");
        startActivity(intent);
    }

}