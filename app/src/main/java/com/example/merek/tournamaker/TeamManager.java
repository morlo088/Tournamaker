package com.example.merek.tournamaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TeamManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    public void createTeamClick(View view) {
        //Intent intent = new Intent(getApplicationContext(), TeamCreation.class); //Application Context and Activity
        //startActivityForResult(intent, 0);
    }

    public void editTeamClick (View view) {
        //Intent intent = new Intent(getApplicationContext(), SelectTeam.class); //Application Context and Activity
        //startActivityForResult(intent, 0);
    }
}
