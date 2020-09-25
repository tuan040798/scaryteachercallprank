package com.q.scaryteacherfakecall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.q.scaryteacherfakecall.utils.BackUpModel;
import com.q.scaryteacherfakecall.utils.HttpHandler;
import com.qrjyjmquzf.adx.service.InterstitialAdsManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import vn.aib.ratedialog.RatingDialog;

public class MainActivity extends AppCompatActivity {
    private ImageView call,video,chat;
//    public static String INTER_ID = "ca-app-pub-3940256099942544/1033173712";
//    public static String BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
    public static String INTER_ID = "ca-app-pub-1244065319954988/5688158029";
    public static String BANNER_ID = "ca-app-pub-1244065319954988/8314321360";
    public static int PERCENT_SHOW_BANNER_AD = 100;
    public static int PERCENT_SHOW_INTER_AD = 100;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static InterstitialAd mInterstitialAd;
    private InterstitialAdsManager adsManager;
    private BackUpModel backUpModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Void aVoid = new GetBackUp().execute().get();
            if(backUpModel != null){
                if(!backUpModel.isLive){
                    new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("Notice from developer")
                            .setMessage("Please update the new application to continue using it. We are really sorry for this issue.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    showApp(MainActivity.this, backUpModel.newAppPackage);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .show();
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        adsManager = new InterstitialAdsManager();
        adsManager.init(true, this, INTER_ID, "#000000", getString(R.string.app_name));
        SharedPreferences prefs = getSharedPreferences("rate_dialog", MODE_PRIVATE);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId(MainActivity.INTER_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }


            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                loadAds();
            }
        });

        Boolean rated = prefs.getBoolean("rate", false);
        if(!rated){
            showRateDialog();
        }
        View adContainer = findViewById(R.id.adMobView);

        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(BANNER_ID);
        ((LinearLayout)adContainer).addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Random r = new Random();
        int ads = r.nextInt(100);

        if (ads >= MainActivity.PERCENT_SHOW_BANNER_AD){
            mAdView.destroy();
            mAdView.setVisibility(View.GONE);
        }
        call = findViewById(R.id.call);
        video = findViewById(R.id.video);
        chat = findViewById(R.id.chat);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int ads = r.nextInt(100);
                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    showInterstitial(MainActivity.this);
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(MainActivity.this,RingCall.class);
                            MainActivity.this.startActivity(myIntent);
                            loadAds();
                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(MainActivity.this,RingCall.class);
                    MainActivity.this.startActivity(myIntent);
                }
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int ads = r.nextInt(100);

                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    showInterstitial(MainActivity.this);
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(MainActivity.this,ringVideo.class);
                            MainActivity.this.startActivity(myIntent);
                            loadAds();
                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(MainActivity.this,ringVideo.class);
                    MainActivity.this.startActivity(myIntent);
                }
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random r = new Random();
                int ads = r.nextInt(100);

                if (ads < MainActivity.PERCENT_SHOW_INTER_AD){
                    showInterstitial(MainActivity.this);
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent myIntent = new Intent(MainActivity.this,chat.class);
                            MainActivity.this.startActivity(myIntent);
                            loadAds();
                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(MainActivity.this,chat.class);
                    MainActivity.this.startActivity(myIntent);
                }
            }
        });
        if (checkPermission()) {
        } else {
            requestPermission();
        }
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adsManager != null)
            adsManager.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void showRateDialog() {
        RatingDialog ratingDialog = new RatingDialog(this);
        ratingDialog.setRatingDialogListener(new RatingDialog.RatingDialogInterFace() {
            @Override
            public void onDismiss() {
            }

            @Override
            public void onSubmit(float rating) {
                rateApp(MainActivity.this);
                SharedPreferences.Editor editor = getSharedPreferences("rate_dialog", MODE_PRIVATE).edit();
                editor.putBoolean("rate", true);
                editor.commit();
            }

            @Override
            public void onRatingChanged(float rating) {
            }
        });
        ratingDialog.showDialog();
    }

    public static void rateApp(Context context) {
        Intent intent = new Intent(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private class GetBackUp extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://raw.githubusercontent.com/bigstoresglc93/scary_teacher/master/backupdata.json";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String appPackage = jsonObj.getString("appPackage");
                    Boolean isLive = jsonObj.getBoolean("isLive");
                    String newAppPackage = jsonObj.getString("newAppPackage");
                    Boolean isAdsShow = jsonObj.getBoolean("isAdsShow");
                    String inter = jsonObj.getString("inter");
                    String fb_inter = jsonObj.getString("fb_inter");
                    Boolean isShowGG = jsonObj.getBoolean("isShowGG");
                    String banner = jsonObj.getString("banner");
                    String nativeAd = jsonObj.getString("nativeAd");
                    String rewarded = jsonObj.getString("rewarded");
                    int percent_banner = jsonObj.getInt("percent_banner");
                    int percent_inter = jsonObj.getInt("percent_inter");
                    int percent_native = jsonObj.getInt("percent_native");
                    int numberNativeAd = jsonObj.getInt("numberNativeAd");

                    backUpModel = new BackUpModel();
                    backUpModel.appPackage = appPackage;
                    backUpModel.isLive = isLive;
                    backUpModel.newAppPackage = newAppPackage;
                    backUpModel.isAdsShow = isAdsShow;
                    backUpModel.inter = inter;
                    backUpModel.fb_inter = fb_inter;
                    backUpModel.isShowGG = isShowGG;
                    backUpModel.banner = banner;
                    backUpModel.nativeAd = nativeAd;
                    backUpModel.rewarded = rewarded;
                    backUpModel.percent_banner = percent_banner;
                    backUpModel.percent_inter = percent_inter;
                    backUpModel.percent_native = percent_native;
                    backUpModel.numberNativeAd = numberNativeAd;

                    INTER_ID = backUpModel.inter;
                    BANNER_ID = backUpModel.banner;
                    PERCENT_SHOW_BANNER_AD = backUpModel.percent_banner;
                    PERCENT_SHOW_INTER_AD = backUpModel.percent_inter;

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });

                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    public static void showApp(Context context, String pkg) {
        Intent intent = new Intent(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + pkg)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }
    public static void loadAds() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }
    }
    public static void showInterstitial(Context m) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            if (isOnline()) {
                loadAds();
            } else {
                Toast.makeText(m, "Please check network connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
