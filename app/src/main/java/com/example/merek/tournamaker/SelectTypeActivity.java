package com.example.merek.tournamaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SelectTypeActivity extends AppCompatActivity {

    //declare variables
    Button btnTxt;
    String type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);
        new Thread(new Runnable() {
            public void run() {
                RelativeLayout layout =(RelativeLayout)findViewById(R.id.tournamentTypeBackgroundRelativeLayout);
                layout.setBackgroundResource(R.drawable.adidas_soccer_ball);
            }
        }).start();

    }

    public void roundRobin(View view){

        //initialize variables
        btnTxt = (Button) findViewById(R.id.roundRobinBtn);
        type = btnTxt.getText().toString();

        //create round-robin tournament
        Intent intent = new Intent(this, CreateTournament.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void knockout(View view){

        //initialize variables
        btnTxt = (Button) findViewById(R.id.knockoutBtn);
        type = btnTxt.getText().toString();

        //create knockout tournament
        Intent intent = new Intent(this, CreateTournament.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void roundRobinKnockout(View view){

        //initialize variables
        btnTxt = (Button) findViewById(R.id.roundRobinKnockoutBtn);
        type = btnTxt.getText().toString();

        //create round-robin & knockout tournament
        Intent intent = new Intent(this, CreateTournament.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}



