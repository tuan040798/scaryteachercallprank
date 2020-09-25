package com.q.scaryteacherfakecall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;

import java.util.ArrayList;
import java.util.Random;

public class chat extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ArrayList<class_chat> listChat;
    private listChatAdapter listChatAdapter;
    private ListView listViewChat;
    private ImageView video,call;
    private String[] listModel = new String[]{"Hi!","Who are you?","Nice to meet you","Can you be my friends?","What can you do?","No!!!","GET OUT!!!Get away from me!!!","AAAAAAAAAAAAAAAAAAAAAAA..."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listChat = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rcw_1);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        ItemLisChatExampleAdapter mAdapter = new ItemLisChatExampleAdapter(listModel);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        listViewChat = findViewById(R.id.listChat);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        switch(position) {
                            case 0:
                                listChat.add(new class_chat("You","Hi"));
                                listChat.add(new class_chat("Scary Teacher","Hello"));
                                listChatAdapter = new listChatAdapter(listChat);
                                listViewChat.setAdapter(listChatAdapter);
                                break;
                            case 1:
                                listChat.add(new class_chat("You","Who are you?"));
                                listChat.add(new class_chat("Scary Teacher","I am your Scary Teacher"));
                                listChatAdapter = new listChatAdapter(listChat);
                                listViewChat.setAdapter(listChatAdapter);
                                break;
                            case 2:
                                listChat.add(new class_chat("You","Nice to meet you!"));
                                listChat.add(new class_chat("Scary Teacher","Nice to meet you, too!"));
                                listChatAdapter = new listChatAdapter(listChat);
                                listViewChat.setAdapter(listChatAdapter);
                                break;
                            case 3:
                                listChat.add(new class_chat("You","Can you be my friends?"));
                                listChat.add(new class_chat("Scary Teacher","We are friends!"));
                                listChatAdapter = new listChatAdapter(listChat);
                                listViewChat.setAdapter(listChatAdapter);
                                break;
                            case 4:
                                listChat.add(new class_chat("You","What can you do?"));
                                listChat.add(new class_chat("Scary Teacher","I can go to your home"));
                                listChatAdapter = new listChatAdapter(listChat);
                                listViewChat.setAdapter(listChatAdapter);
                                break;
                            case 5:
                                listChat.add(new class_chat("You","No!!!"));
                                listChat.add(new class_chat("Scary Teacher","OPEN YOUR DOOR! NOW"));
                                listChatAdapter = new listChatAdapter(listChat);
                                listViewChat.setAdapter(listChatAdapter);
                                break;
                            case 6:
                                listChat.add(new class_chat("You","GET OUT!!!Get away from me!!!"));
                                listChat.add(new class_chat("Scary Teacher","NO!!! OPEN DOOR NOW!!!"));
                                listChatAdapter = new listChatAdapter(listChat);
                                listViewChat.setAdapter(listChatAdapter);
                                break;
                            case 7:
                                listChat.add(new class_chat("You","AAAAAAAAAAAAAAAAAAAAAAA................"));
                                listChat.add(new class_chat("Scary Teacher","HAHAHAHAHAHAHAHAHAHAHAHAHA!!!"));
                                listChatAdapter = new listChatAdapter(listChat);
                                listViewChat.setAdapter(listChatAdapter);
                                break;
                            default:

                                break;
                        }
                        listViewChat.setSelection(listViewChat.getAdapter().getCount()-1);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        video = findViewById(R.id.btn_video);
        call = findViewById(R.id.btn_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int ads = r.nextInt(100);

                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    MainActivity.showInterstitial(getApplicationContext());
                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(getApplicationContext(),RingCall.class);
                            startActivity(myIntent);
                            finish();
                            MainActivity.loadAds();
                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(getApplicationContext(),RingCall.class);
                    startActivity(myIntent);
                    finish();
                }
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int ads = r.nextInt(100);
                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    MainActivity.showInterstitial(getApplicationContext());
                    MainActivity.mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(getApplicationContext(),ringVideo.class);
                            startActivity(myIntent);
                            finish();
                            MainActivity.loadAds();
                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(getApplicationContext(),ringVideo.class);
                    startActivity(myIntent);
                    finish();
                }
            }
        });


    }
}
