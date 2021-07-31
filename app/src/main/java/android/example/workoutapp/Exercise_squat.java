package android.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

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

import org.jetbrains.annotations.NotNull;

public class Exercise_squat extends AppCompatActivity  {
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private Button buttonPlay;
    private boolean partOfProgramm = false;
    private int squat_numberrequired = 0;
    private int jumpingjack_numberrequired = 0;
    private long starttime = 0L;
    private int program_duration = 0;
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
            squat_numberrequired = Integer.parseInt(extras.getString("squat_numberrequired"));
            jumpingjack_numberrequired = Integer.parseInt(extras.getString("jumpingjack_numberrequired"));
            program_duration = Integer.parseInt(extras.getString("programduration"));


            ((TextView)findViewById(R.id.textView17)).setText("Do " + squat_numberrequired + " Squats.");
        }else{
            squat_numberrequired = 20;
            ((TextView)findViewById(R.id.textView17)).setText("Do " + squat_numberrequired + " Squats.");

        }


        this.videoView = (VideoView) findViewById(R.id.videoView);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        // Set the media controller buttons
        if (this.mediaController == null) {
            this.mediaController = new MediaController(Exercise_squat.this);

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
                String resName = VideoViewUtils.RAW_VIDEO_SAMPLE_SQUAT;
                VideoViewUtils.playRawVideo(Exercise_squat.this, videoView, resName);
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
        program_duration += (int)duration;
        if (partOfProgramm){
            //go to squats now
            Intent intent = new Intent(this, Program1Done.class);
            intent.putExtra("squat_numberrequired", String.valueOf(squat_numberrequired));
            intent.putExtra("jumpingjack_numberrequired", String.valueOf(jumpingjack_numberrequired));
            intent.putExtra("programduration", String.valueOf(program_duration));
            startActivity(intent);
        }
        else{
            ////go to list of exercises
            Intent intent = new Intent(this, Exercise2ActivityDone.class);
            intent.putExtra("number", String.valueOf(squat_numberrequired));
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
