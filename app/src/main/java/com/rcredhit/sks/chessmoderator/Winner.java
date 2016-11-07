package com.rcredhit.sks.chessmoderator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Winner extends Activity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        SharedPreferences sp = getSharedPreferences("Names",MODE_PRIVATE);
        String result = sp.getString("Result","No result");
        String moves = sp.getString("TotalMoves","0");
        textView = (TextView)findViewById(R.id.ResultMessage);
        textView.setText(result+"\n"+moves);
    }

    public void gotoStart(View v)
    {
        Intent intent = new Intent(this,Start.class);
        startActivity(intent);
        finish();
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
