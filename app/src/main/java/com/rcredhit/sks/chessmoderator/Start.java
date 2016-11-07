package com.rcredhit.sks.chessmoderator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Start extends Activity {

    EditText player1,player2;
    Button b;
    String s3,s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        player1 = (EditText)findViewById(R.id.player1Name);
        player2 = (EditText)findViewById(R.id.player2Name);

        b = (Button)findViewById(R.id.proceed);
    }

    public void gotoTimer(View v) {
        String s1 = player1.getText().toString();
        String s2 = player2.getText().toString();
        if(s1.length()==0)
            s1 = "Player 1";
        else {
            StringBuilder s3 = new StringBuilder(s1);
            int i = 0;
            while (i<s1.length() && s1.charAt(i) == ' ') {
                i++;
            }
            if (i==s1.length())
                s1 = "Player 1";
            else {
                s3.delete(0, i);
                i = s3.length()-1;
                while(s3.charAt(i)==' ')
                    i--;
                i++;
                s3.delete(i,s3.length());
                s1 = s3.toString();
            }
        }
        if(s2.length()==0)
            s2 = "Player 2";
        else {
            StringBuilder s3 = new StringBuilder(s2);
            int i = 0;
            while (i<s2.length() && s2.charAt(i) == ' ') {
                i++;
            }

            if (i==s2.length())
                s2 = "Player 2";
            else {
                s3.delete(0, i);
                i = s3.length()-1;
                while(s3.charAt(i)==' ')
                    i--;
                i++;
                s3.delete(i,s3.length());
                s2 = s3.toString();
            }
        }

        if(s1.compareTo(s2)==0)
        {
            s1 += "_1";
            s2 += "_2";
        }

        SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Player1Name",s1);
        editor.putString("Player2Name",s2);
        editor.commit();

        Intent intent = new Intent(this,TimerSettings.class);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void exit(View v)
    {
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
