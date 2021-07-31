package android.example.workoutapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public class Exercise_plank extends AppCompatActivity{
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private Button buttonPlay;
    private boolean partOfProgramm = false;
    private int pushups_numberrequired = 0;
    private int plank_durationreequired = 0;
    private long starttime = 0L;
    private int programduration = 0;
    private CountDownTimer countdowntimer;
    private int musicfile = -1;
    private MediaPlayer musicmediaPlayer;
    private Exercise_plank currentClass = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_video);

        Bundle extras = getIntent().getExtras();
        String partOfProgramString = extras.getString("partOfProgram");
        if (partOfProgramString.equals("yes")){
            partOfProgramm = true;
            pushups_numberrequired = Integer.parseInt(extras.getString("pushups_numberrequired"));
            plank_durationreequired = Integer.parseInt(extras.getString("plank_durationreequired"));
            programduration = Integer.parseInt(extras.getString("programduration"));

        }else{
            plank_durationreequired = 60;
        }

        //set countdown timer
        countdowntimer = new CountDownTimer(plank_durationreequired * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {

                int minutes = (int)(millisUntilFinished/1000L) / 60;
                String minutes_string = String.valueOf(minutes);
                int seconds = (int)(millisUntilFinished/1000) % 60;
                String seconds_string = String.valueOf(seconds);


                if(minutes<10){
                    minutes_string = "0"+minutes_string;
                }
                if(seconds<10){
                    seconds_string = "0"+seconds;
                }


                ((TextView)findViewById(R.id.textView17)).setText("Hold the plank!\n" + minutes_string +
                        ":" + seconds_string);
            }

            public void onFinish() {
                openNewActivity();
            }

        };



        this.videoView = (VideoView) findViewById(R.id.videoView);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        //rename finish button for planks
        ((Button) findViewById(R.id.buttonStop)).setText("STOP");
        //different text for plank since you cannot finish
        ((TextView) findViewById(R.id.textView13)).setText("Please click on STOP if you want to stop earlier");
        //different text for plank to start timer
        buttonPlay.setText("START");


        // Set the media controller buttons
        if (this.mediaController == null) {
            this.mediaController = new MediaController(Exercise_plank.this);

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
                String resName = VideoViewUtils.RAW_VIDEO_SAMPLE_PLANK;
                VideoViewUtils.playRawVideo(Exercise_plank.this, videoView, resName);
                countdowntimer.start();

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
                    musicmediaPlayer = MediaPlayer.create(currentClass, musicfile);
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
        });

        ((Button) findViewById(R.id.buttonStop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });

    }


    public void openNewActivity(){
        long endtime =  (Long)(System.currentTimeMillis() / 1000);
        //duration in seconds
        long duration = endtime - starttime;
        programduration += (int)duration;
        if (partOfProgramm){
            //go to squats now
            Intent intent = new Intent(this, Program2Done.class);
            intent.putExtra("pushups_numberrequired", String.valueOf(pushups_numberrequired));
            intent.putExtra("plank_duration", String.valueOf(duration));
            intent.putExtra("programduration", String.valueOf(programduration));
            startActivity(intent);
        }
        else{
            ////go to list of exercises
            Intent intent = new Intent(this, Exercise4ActivityDone.class);
            intent.putExtra("number", "0");
            intent.putExtra("duration", String.valueOf(duration));
            startActivity(intent);
        }
    }
    public void onPause(){
        super.onPause();
        if (musicmediaPlayer !=null) {
            musicmediaPlayer.pause();
        }
        countdowntimer.cancel();
    }
    public void onDestroy(){
        super.onDestroy();

        countdowntimer.cancel();
    }
    public void onStart(){
        super.onStart();
        if (musicmediaPlayer !=null) {
            musicmediaPlayer.start();
        }
    }
}
