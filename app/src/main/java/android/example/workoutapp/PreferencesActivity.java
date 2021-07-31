package android.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class PreferencesActivity extends AppCompatActivity {

    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        //get the spinner from the xml.
        Spinner music_dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{getString(R.string.music1), getString(R.string.music2), getString(R.string.music3), getString(R.string.nomusic)};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        music_dropdown.setAdapter(adapter);

        music_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();
                switch(position) {
                    case 0:
                        editor.putString("music",getString(R.string.music1));
                        break;
                    case 1:
                        editor.putString("music",getString(R.string.music2));
                        break;
                    case 2:
                        editor.putString("music",getString(R.string.music3));
                        break;
                    case 3:
                        editor.putString("music",getString(R.string.nomusic));
                        break;
                    default:
                        editor.putString("music",getString(R.string.nomusic));
                }
                editor.commit();
            }


            @Override //mandatory
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        //get saved settings
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String set_music = settings.getString("music", "");
        music_dropdown.setSelection(adapter.getPosition(set_music));



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // DrawerUtil.getDrawer(this, toolbar);

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("List exercises");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Records");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Programs");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Preferences");

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_background)
                .build();


        //create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(4)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = null;
                        if (drawerItem.getIdentifier() == 1) {
                            intent = new Intent(PreferencesActivity.this, ListExercisesActivity.class);
                        } else if (drawerItem.getIdentifier() == 2) {
                            intent = new Intent(PreferencesActivity.this, MainActivity.class);
                        } else if (drawerItem.getIdentifier() == 3) {
                            intent = new Intent(PreferencesActivity.this, ListWorkoutPrograms.class);
                        }
                        else if (drawerItem.getIdentifier() == 4) {
                            // intent = new Intent(PreferencesActivity.this, PreferencesActivity.class);
                        }
                        if (intent != null) {
                            PreferencesActivity.this.startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        }
                        return false;
                    }
                })
                .build();


    }

    @Override
    public void onBackPressed(){
        if (result != null && result.isDrawerOpen()){
            result.closeDrawer();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
    }



}