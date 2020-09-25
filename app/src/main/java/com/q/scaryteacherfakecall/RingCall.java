package com.q.scaryteacherfakecall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageView;

public class RingCall extends AppCompatActivity {
    private MediaPlayer player = new MediaPlayer();
    private ImageView cancelCall,okCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_call);
        startSound(R.raw.ipcal);
        cancelCall = findViewById(R.id.cancelCall);
        okCall = findViewById(R.id.okCall);
        cancelCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        okCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                Intent myIntent = new Intent(getApplicationContext(), call.class);
                startActivity(myIntent);
                finish();
            }
        });
    }
    private void startSound(int resourceID) {
        // player = new MediaPlayer();
        player = MediaPlayer.create(this, resourceID);
        player.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
