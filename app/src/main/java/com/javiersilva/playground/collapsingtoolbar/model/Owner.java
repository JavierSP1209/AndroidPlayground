package com.javiersilva.playground.collapsingtoolbar.model;

import android.util.Log;

import com.javiersilva.playground.common.Constants;

import javax.inject.Inject;

/**
 * Owner is a class that will be injected by the Playground module at the Playground component
 * Created by javiersilva on 4/25/17.
 */
public class Owner {
    private Pet pet;

    @Inject
    public Owner(Pet pet) {
        this.pet = pet;
    }

    public void instructSomething() {
        Log.d(Constants.DAGGER_TAG, "Do your thing: ");
        pet.doNoice();
    }
}
