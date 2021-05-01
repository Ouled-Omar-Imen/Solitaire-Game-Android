package com.example.solitaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private gameview gameview;
    private Button btn_restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameview = findViewById(R.id.gameview);
        btn_restart=findViewById(R.id.btn_restart);
        btn_restart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                finish();
                startActivity(getIntent());
                overridePendingTransition(0,0);
            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //sauvgarder la partie en cours
    }



    }
