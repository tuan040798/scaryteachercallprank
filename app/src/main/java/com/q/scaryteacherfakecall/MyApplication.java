package com.q.scaryteacherfakecall;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;
import com.flurry.android.FlurryAgent;
import com.qrjyjmquzf.adx.service.AdsExchange;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AudienceNetworkAds.initialize(this);
        AdsExchange.init(this, "5f5892d2232f685b395c3df0");
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "GM4F33HFF6CHGV58HQ27");

    }
}