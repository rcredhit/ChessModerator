package com.rcredhit.sks.chessmoderator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class TimerSettings extends Activity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_settings);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Timings, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        s = "Tap here to choose timer options";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView setDescription = (TextView)findViewById(R.id.description);
        if(position == 1) {
            setDescription.setText(R.string.description2);
            s = "15 : 00";
        }
        else if(position ==2) {
            setDescription.setText(R.string.description3);
            s = "30 : 00";
        }
        else if(position ==3) {
            setDescription.setText(R.string.description4);
            s = "60 : 00";
        }
        else if(position ==0) {
            setDescription.setText(R.string.description1);
            s = "05 : 00";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void gotoToss(View v)
    {
        SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("GameIn",s);
        editor.commit();

        Intent i = new Intent(this,Clock.class);
       // i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
