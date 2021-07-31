package android.example.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PushupDescriptionActivity extends AppCompatActivity {
    ImageButton startpushup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushupdescription);
        startpushup = findViewById(R.id.playButton );
        startpushup.setOnClickListener(view -> openNewActivity());


    }

    public void openNewActivity(){
        //Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, PushupActivity.class);
        intent.putExtra("partOfProgram", "no");
        startActivity(intent);
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