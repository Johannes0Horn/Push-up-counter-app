package android.example.workoutapp;

import android.content.Intent;
import android.os.Bundle;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListExercisesActivity extends AppCompatActivity {

    ListView simpleList;
    String[] exerciseList = new String[] {};
    String[] exerciseDescription = new String[] {};



    private Drawer result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);
        exerciseList = new String[] {"Push-ups", "Squats", "Jumping Jacks", "Plank"};
        exerciseDescription = new String[] {
                getString(R.string.pushupdescription),getString(R.string.squatdescription),getString(R.string.jumpingjackdescription),getString(R.string.plankdescription)};


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // DrawerUtil.getDrawer(this, toolbar);

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("List of exercises");
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
                 .withSelectedItem(1)
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
                            // intent = new Intent(ListExercisesActivity.this, PushupDescriptionActivity.class);
                        } else if (drawerItem.getIdentifier() == 2) {
                            intent = new Intent(ListExercisesActivity.this, MainActivity.class);
                        } else if (drawerItem.getIdentifier() == 3) {
                            intent = new Intent(ListExercisesActivity.this, ListWorkoutPrograms.class);
                        }
                        else if (drawerItem.getIdentifier() == 4) {
                            intent = new Intent(ListExercisesActivity.this, PreferencesActivity.class);
                        }
                        if (intent != null) {
                            //finish();
                            ListExercisesActivity.this.startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        }
                        return false;
                    }
                })
                .build();



        CustomAdapter customAdapter = new  CustomAdapter();

        simpleList = (ListView) findViewById(R.id.listViewExercisesID);
        simpleList.setAdapter(customAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), PushupDescriptionActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), SquatDescriptionActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), JumpingJackDescriptionActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), PlankDescriptionActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        });
    }
    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // return ListViewImages.length();
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.activity_list_view, null);
            TextView name = view1.findViewById(R.id.exerciseTitleID);
            TextView descp = view1.findViewById(R.id.exerciseDescriptionID);
            // ImageView image = view1.findViewById(R.id.list_view_images);

            name.setText(exerciseList[i]);
            descp.setText(exerciseDescription[i]);
            // image.setImageResource(ListImage[i]);
            return view1;
        }
    }

    @Override
    public void onBackPressed(){
        if (result != null && result.isDrawerOpen()){
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


}