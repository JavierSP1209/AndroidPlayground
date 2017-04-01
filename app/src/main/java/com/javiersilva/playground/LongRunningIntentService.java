package com.javiersilva.playground;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.javiersilva.playground.Constants.TAG;

/**
 * Created by JavierSP1209 on 4/1/17.
 */
public class LongRunningIntentService extends IntentService {
    public LongRunningIntentService() {
        super(LongRunningIntentService.class.getName());
        Log.d(TAG, LongRunningIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: Starting...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.e(TAG, "onHandleIntent: " + e.getMessage(), e);
        }
        Log.d(TAG, "onHandleIntent: Done!");
    }
}
