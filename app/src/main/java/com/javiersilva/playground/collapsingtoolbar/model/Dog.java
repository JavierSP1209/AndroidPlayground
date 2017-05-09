package com.javiersilva.playground.collapsingtoolbar.model;

import android.util.Log;

import com.javiersilva.playground.common.Constants;

import javax.inject.Inject;

/**
 * Created by javiersilva on 4/25/17.
 */

public class Dog implements Pet {

    private String sound;

    @Inject
    public Dog(String sound) {
        this.sound = sound;
    }

    @Override
    public void doNoice() {
        Log.d(Constants.DAGGER_TAG, sound);
    }
}
