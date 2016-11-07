package com.rcredhit.sks.chessmoderator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class toss extends Activity {

    boolean turn = false;
    int i = 0;
    Button b3,b4;
    CountDownTimer countDownTimer;
    Context context;
    String s1 = "";
    String s2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toss);

        SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
        String player1 = sp.getString("Player1Name","Player 1");
        String player2 = sp.getString("Player2Name","Player 2");
        s1 = player1 + " takes white. \n" + player2 + " takes black.";
        s2 = player2 + " takes white. \n" + player1 + " takes black.";

        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        b3.setText(player1);
        b4.setText(player2);
        context = this;

        countDownTimer = new MyCountDownTimer(1000,100);
        countDownTimer.start();

    }

    public class MyCountDownTimer extends CountDownTimer
    {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
        if(turn == false)
        {
            b3.setEnabled(false);
            b4.setEnabled(true);
            turn = true;
        }
        else
        {
            b3.setEnabled(true);
            b4.setEnabled(false);
            turn = false;
        }
    }

        @Override
        public void onFinish() {
            i = (int)(Math.random() * 2);
            SharedPreferences sp = getSharedPreferences("Names",MODE_PRIVATE);
            if(i==0) {
                Toast toast = Toast.makeText(context,s1,Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Toss","0");
                editor.commit();
                gotoClock();
            }
            else
            {
                Toast toast = Toast.makeText(context, s2, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Toss","1");
                editor.commit();
                gotoClock();
            }
        }
    };

    public void gotoClock()
    {
        Intent i = new Intent(this,Clock.class);
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
