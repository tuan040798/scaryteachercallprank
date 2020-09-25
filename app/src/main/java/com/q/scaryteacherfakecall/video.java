package com.q.scaryteacherfakecall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.ads.AdListener;

import java.util.Random;

public class video extends AppCompatActivity {
    private VideoView videoView;
    private ImageView cancleVideo;
    int listId[] = {R.raw.video1,R.raw.video2,R.raw.video3};
    Random r = new Random();
    int i = r.nextInt(3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoo);
        videoView = findViewById(R.id.videoView);
        cancleVideo = findViewById(R.id.cancelVideo);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        //specify the location of media file
        String path = "android.resource://" + getPackageName() + "/" + listId[i];

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(null);
        videoView.setVideoURI(Uri.parse(path));
        videoView.requestFocus();

        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        cancleVideo.setOnClickListener(new View.OnClickListener() {
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
//
//                    finish();
//                }
            }
        });
    }
}
