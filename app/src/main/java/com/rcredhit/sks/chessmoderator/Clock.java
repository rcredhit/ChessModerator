package com.rcredhit.sks.chessmoderator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Clock extends Activity implements View.OnClickListener{
    long increment = 0;
    long mins = 00;
    long sec = 00;
    boolean firstTime = true;
    String player1,player2;
    Button b1;
    Button b2;
    Button d,cm,s;
    TextView TextView1, TextView2;
    CountDownTimer countDownTimer1,countDownTimer2;
    long startTime1,startTime2;
    final long interval = 1000;
    String time, toss;
    int player_under_action = 2;
    int player1moves = 0;
    int player2moves = 0;
    public MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        mp = MediaPlayer.create(getApplicationContext(),R.raw.tap_sound);

        Toast toast  = Toast.makeText(this,"Tap on the timer to start",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        TextView1 = (TextView) findViewById(R.id.Player1);
        TextView2 = (TextView) findViewById(R.id.Player2);

        b1 = (Button) findViewById(R.id.Timer1);
        b2 = (Button) findViewById(R.id.Timer2);
        d = (Button)findViewById(R.id.Draw);
        cm = (Button)findViewById(R.id.Checkmate);
        s = (Button)findViewById(R.id.Surrender);

        SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
        player1 = sp.getString("Player1Name", "Player 1");
        player2 = sp.getString("Player2Name", "Player 2");
        time = sp.getString("GameIn", "00:00");
        toss = sp.getString("Toss","0");
        if(toss.compareTo("0")==0){
            b1.setEnabled(false);
        }
        else{
            b2.setEnabled(false);
        }

        if(time.charAt(1)=='5')
        {
            if(time.charAt(0)=='0')
                increment = 2000;
            else if(time.charAt(0)=='1')
                increment = 5000;
        }
        else
        {
                increment = 30000;
        }

        String minutes = time.substring(0,2);
        String seconds = time.substring(5,7);
        mins = Integer.parseInt(minutes);
        sec = Integer.parseInt(seconds);
        startTime1 = (mins*60 + sec)*1000;
        startTime2 = (mins*60 + sec)*1000;
        TextView1.setText(player1 + "     Moves: " + player1moves);
        TextView2.setText(player2 + "     Moves: " + player2moves);

        b1.setText(time);
        b2.setText(time);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        cm.setOnClickListener(this);
        s.setOnClickListener(this);
        d.setOnClickListener(this);

        countDownTimer1 = new MyCountDownTimer1(startTime1, interval);
        countDownTimer2 = new MyCountDownTimer2(startTime2, interval);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {

        mp.seekTo(0);
        mp.start();

        if((v.getId()==R.id.Timer1))
        {
            player1moves ++;
            TextView1.setText(player1 + "     Moves: " + player1moves);
            player_under_action = 2;
            d.setRotation(0);
            cm.setRotation(0);
            s.setRotation(0);
            countDownTimer2.start();
            countDownTimer1.cancel();
            countDownTimer1 = new MyCountDownTimer1(startTime1, interval);
            sec = startTime1/1000;
            mins = sec/60;
            sec = sec%60;
            time = String.format("%02d : %02d",mins,sec);
            b1.setText(time);
            b1.setEnabled(false);
            b2.setEnabled(true);
        }
        else if((v.getId()==R.id.Timer2))
        {
            if(firstTime==true){firstTime=false;}
            else
                player2moves ++;
            TextView2.setText(player2 + "     Moves: " + player2moves);
            player_under_action = 1;
            d.setRotation(180);
            cm.setRotation(180);
            s.setRotation(180);
            countDownTimer1.start();
            countDownTimer2.cancel();
            countDownTimer2 = new MyCountDownTimer2(startTime2, interval);
            sec = startTime2/1000;
            mins = sec/60;
            sec = sec%60;
            time = String.format("%02d : %02d",mins,sec);
            b2.setText(time);
            b2.setEnabled(false);
            b1.setEnabled(true);
        }
        else if(v.getId()==R.id.Checkmate)
        {
            if(player_under_action == 1){
                player1moves++;
                DisableAllButtons();
                SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Result",player1+" wins the game!");
                editor.apply();
                gotoWinner();
            }
            else if(player_under_action == 2)
            {
                player2moves++;
                DisableAllButtons();
                SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Result",player2+" wins the game!");
                editor.apply();
                gotoWinner();
            }
        }
        else if(v.getId()==R.id.Surrender)
        {
            if(player_under_action == 1){
                DisableAllButtons();
                SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Result",player2+" wins the game!");
                editor.apply();
                gotoWinner();
            }
            else if(player_under_action == 2)
            {
                DisableAllButtons();
                SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Result",player1+" wins the game!");
                editor.apply();
                gotoWinner();
            }
        }

        else if(v.getId()==R.id.Draw)
        {
            DisableAllButtons();
            SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Result","Draw!");
            editor.commit();
            gotoWinner();
        }
    }
    public void DisableAllButtons()
    {
        countDownTimer1.cancel();
        countDownTimer2.cancel();
        b1.setEnabled(false);
        b2.setEnabled(false);
        s.setEnabled(false);
        cm.setEnabled(false);
        d.setEnabled(false);
        SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("TotalMoves",String.format("Total moves: %d",player1moves+player2moves));
        editor.apply();
    }

    public class MyCountDownTimer1 extends CountDownTimer{
        public MyCountDownTimer1(long startTime, long interval)
        {
            super(startTime,interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sec = millisUntilFinished/1000;
            mins = sec/60;
            sec = sec%60;
            time = String.format("%02d : %02d",mins,sec);
            b1.setText(time);
            startTime1 = millisUntilFinished + increment;
        }

        @Override
        public void onFinish() {
            b1.setText("Time's Up!");
            DisableAllButtons();
            SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Result",player2+" wins the game!");
            editor.commit();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoWinner();
                }
            },500);
        }
    }


    public class MyCountDownTimer2 extends CountDownTimer{
        public MyCountDownTimer2(long startTime, long interval)
        {
            super(startTime,interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sec = millisUntilFinished/1000;
            mins = sec/60;
            sec = sec%60;
            time = String.format("%02d : %02d",mins,sec);
            b2.setText(time);
            startTime2 = millisUntilFinished + increment;
        }

        @Override
        public void onFinish() {
            b2.setText("Time's Up!");
            DisableAllButtons();
            SharedPreferences sp = getSharedPreferences("Names", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Result",player1+" wins the game!");
            editor.commit();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoWinner();
                }
            },500);
        }
    }

    public void gotoWinner()
    {
        Intent intent = new Intent(this,Winner.class);
        startActivity(intent);
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
