package com.javiersilva.playground.dagger;

import android.util.Log;

import com.javiersilva.playground.Constants;

import javax.inject.Inject;

/**
 * Created by javiersilva on 4/25/17.
 */

public class Dog implements Pet {

    @Inject
    public Dog() {

    }

    @Override
    public void doNoice() {
        Log.d(Constants.DAGGER_TAG, "Wouf!!");
    }
}
