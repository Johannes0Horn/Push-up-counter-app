/*
package android.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

public class Exercise1Activity extends AppCompatActivity {
    ImageButton startpushup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushups_description);

        startpushup = (ImageButton) findViewById(R.id.playButton);
        startpushup.setOnClickListener(view -> openNewActivity());


    }

    public void openNewActivity(){
        //Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, PushupActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String incMessage = data.getStringExtra("message");
                Toast.makeText(getApplicationContext(), incMessage, Toast.LENGTH_LONG).show();
                //start done activity



            }
        }
    }

}*/
