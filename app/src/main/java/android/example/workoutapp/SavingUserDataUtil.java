package android.example.workoutapp;

import android.content.SharedPreferences;
import android.view.View;

public class SavingUserDataUtil {


    public void saveDataWorkout(View view, int duration, int kcal, int number, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("duration", "0");
        editor.putString("kcal", "0");
        editor.putString("numberWorkout", "0");

        String newDuration = String.valueOf(duration + Integer.valueOf(sharedPreferences.getString("duration", "0")));
        String newKcal = String.valueOf(kcal + Integer.valueOf(sharedPreferences.getString("kcal", "0")));
        String newNumber = String.valueOf(1 + Integer.valueOf(sharedPreferences.getString("numberWorkout", "0")));

        editor.putString("duration", newDuration);
        editor.putString("kcal", newKcal);
        editor.putString("numberWorkout", newNumber);
        editor.commit();
    }
}
