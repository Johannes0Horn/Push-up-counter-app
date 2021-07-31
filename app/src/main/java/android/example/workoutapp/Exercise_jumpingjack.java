package android.example.workoutapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public class Exercise_jumpingjack extends AppCompatActivity {
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private Button buttonPlay;
    private boolean partOfProgramm = false;
    private int jumpingjack_numberrequired = 0;
    private int squat_numberrequired = 0;
    private long starttime = 0L;
    private int musicfile = -1;
    private MediaPlayer musicmediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_video);

        Bundle extras = getIntent().getExtras();
        String partOfProgramString = extras.getString("partOfProgram");
        if (partOfProgramString.equals("yes")){
            partOfProgramm = true;
            jumpingjack_numberrequired = Integer.parseInt(extras.getString("jumpingjack_numberrequired"));
            squat_numberrequired = Integer.parseInt(extras.getString("jumpingjack_numberrequired"));

            ((TextView)findViewById(R.id.textView17)).setText("Do " + jumpingjack_numberrequired + " jumping Jacks.");

        }else{
            jumpingjack_numberrequired = 20;
            ((TextView)findViewById(R.id.textView17)).setText("Do " + jumpingjack_numberrequired + " jumping Jacks.");

        }



        this.videoView = (VideoView) findViewById(R.id.videoView);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        // Set the media controller buttons
        if (this.mediaController == null) {
            this.mediaController = new MediaController(Exercise_jumpingjack.this);

            // Set the videoView that acts as the anchor for the MediaController.
            this.mediaController.setAnchorView(videoView);

            // Set MediaController for VideoView
            this.videoView.setMediaController(mediaController);
        }
        // When the video file ready for playback.
        this.videoView.setOnPreparedListener(new OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {

                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });
        this.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "myvideo.mp4" in directory "raw".
                String resName = VideoViewUtils.RAW_VIDEO_SAMPLE_JUMPING_JACK;
                VideoViewUtils.playRawVideo(Exercise_jumpingjack.this, videoView, resName);
            }
        });

        ((Button) findViewById(R.id.buttonStop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });


        //start music
        //get music config
        //get saved settings

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String set_music = settings.getString("music", "");

        if (set_music.equals(getString(R.string.music1))){
            musicfile = R.raw.linkin_park_in_the_end;

        }
        else if(set_music.equals(getString(R.string.music2))){
            musicfile = R.raw.linkin_park_numb;

        }
        else if(set_music.equals(getString(R.string.music3))){
            musicfile = R.raw.linkin_park_what_ive_done;

        }
        else if(set_music.equals(getString(R.string.nomusic))){
            musicfile = -1;
        }


        if (musicfile != -1){
            musicmediaPlayer = MediaPlayer.create(this, musicfile);
            musicmediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(@NotNull MediaPlayer musicmediaPlayer) {
                    musicmediaPlayer.start();
                }
            });
        }


    //starttime
    starttime = (Long)(System.currentTimeMillis() / 1000);
    }

    public void openNewActivity(){
        long endtime =  (Long)(System.currentTimeMillis() / 1000);
        //duration in seconds
        long duration = endtime - starttime;

        if (partOfProgramm){
            //go to squats now
            Intent intent = new Intent(this, Exercise_squat.class);
            intent.putExtra("partOfProgram", "yes");
            intent.putExtra("squat_numberrequired", String.valueOf(squat_numberrequired));
            intent.putExtra("jumpingjack_numberrequired", String.valueOf(jumpingjack_numberrequired));
            intent.putExtra("programduration", String.valueOf(duration));

            startActivity(intent);
        }
        else{
            ////go to list of exercises
            Intent intent = new Intent(this, Exercise3ActivityDone.class);
            intent.putExtra("number", String.valueOf(jumpingjack_numberrequired));
            intent.putExtra("duration", String.valueOf(duration));
            startActivity(intent);
        }
    }

    public void onPause(){

        super.onPause();
        if (musicmediaPlayer !=null) {
            musicmediaPlayer.pause();
        }
    }
    public void onDestroy(){
        super.onDestroy();
        if (musicmediaPlayer !=null) {
            musicmediaPlayer.stop();
        }
    }
    public void onStart(){
        super.onStart();
        if (musicmediaPlayer !=null) {
            musicmediaPlayer.start();
        }
    }

}


