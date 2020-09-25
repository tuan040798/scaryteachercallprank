package com.q.scaryteacherfakecall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;

import java.util.Random;

public class call extends AppCompatActivity {
    private TextView time;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public static int num ;
    public static boolean is = false;
    public static Thread run_seekBar ;
    private MediaPlayer player = new MediaPlayer();
    private ImageView cancelCall2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        time = findViewById(R.id.time);
        cancelCall2 = findViewById(R.id.cancel);
        is =true;
        run_seekBar = new Thread() {
            @SuppressLint("SetTextI18n")
            public void run() {
                num = 0;
                while (is) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            num +=1000;
                            int c = num / 1000;
                            time.setText(c / 60 + ":" + c % 60);
                        }
                    });
                }
            }
        };
        run_seekBar.start();
        startSound(R.raw.talk);
        cancelCall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Random r = new Random();
//                int ads = r.nextInt(100);
//                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
//                    MainActivity.showInterstitial(getApplicationContext());
//                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
//                        @Override
//                        public void onAdClosed() {
//                            finish();
//                            MainActivity.loadAds();
//                        }
//                    });
//                }
//                else {
//                    finish();
//                }
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
        is = false;
        player.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        is = true;
    }
}
