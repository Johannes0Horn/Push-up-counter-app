package android.example.workoutapp;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewUtils {
    public static final String RAW_VIDEO_SAMPLE_PUSH_UP = "pushup_video";
    public static final String RAW_VIDEO_SAMPLE_SQUAT = "squats";
    public static final String RAW_VIDEO_SAMPLE_JUMPING_JACK = "jumping_jack";
    public static final String RAW_VIDEO_SAMPLE_PLANK = "plank";
    public static final String LOG_TAG= "AndroidVideoView";
    // Play a video in directory RAW.
    // Video name = "myvideo.mp4" ==> resName = "myvideo".
    public static void playRawVideo(Context context, VideoView videoView, String resName)  {
        try {
            // ID of video file.
            int id = VideoViewUtils.getRawResIdByName( context, resName);

            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + id);
            Log.i(LOG_TAG, "Video URI: "+ uri);

            videoView.setVideoURI(uri);
            videoView.requestFocus();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error Play Raw Video: "+e.getMessage());
            Toast.makeText(context,"Error Play Raw Video: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    // Find ID corresponding to the name of the resource (in the directory RAW).
    public static int getRawResIdByName(Context context, String resName) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName, "raw", pkgName);

        Log.i(LOG_TAG, "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
}
