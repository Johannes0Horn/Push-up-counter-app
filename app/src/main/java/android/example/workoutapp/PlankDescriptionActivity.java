package android.example.workoutapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlankDescriptionActivity extends AppCompatActivity {
    ImageButton startplank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank_description);

        startplank = findViewById(R.id.playButton);
        startplank.setOnClickListener(view -> openNewActivity());
    }

    public void openNewActivity() {
        //Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, Exercise_plank.class);
        intent.putExtra("partOfProgram", "no");
        startActivity(intent);
    }


}
