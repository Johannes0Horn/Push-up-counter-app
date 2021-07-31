package android.example.workoutapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JumpingJackDescriptionActivity extends AppCompatActivity {
    ImageButton startjumpingjack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jumping_jack_description);

        startjumpingjack = findViewById(R.id.playButton);
        startjumpingjack.setOnClickListener(view -> openNewActivity());


    }

    public void openNewActivity() {
        //Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, Exercise_jumpingjack.class);
        intent.putExtra("partOfProgram", "no");
        startActivity(intent);
    }


}