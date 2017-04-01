package com.javiersilva.playground;

import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;

import static com.javiersilva.playground.Constants.TAG;

/**
 * Created by javiersilva on 4/1/17.
 */

public class LongRunningObservableFactory {

    public Observable<String> start() {
        Log.d(TAG, "start...");
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.d(TAG, "call: Starting...");
                try {
                    Thread.sleep(5000);
                    Log.d(TAG, "call: Done!");
                    return "Termine!";
                } catch (InterruptedException e) {
                    Log.e(TAG, "onHandleIntent: " + e.getMessage(), e);
                }
                return "No Termine!";
            }
        });
    }
}
