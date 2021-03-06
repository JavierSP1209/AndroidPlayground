package com.javiersilva.playground.di;

import com.javiersilva.playground.collapsingtoolbar.LongRunningObservableFactory;
import com.javiersilva.playground.collapsingtoolbar.model.Dog;
import com.javiersilva.playground.collapsingtoolbar.model.Pet;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javiersilva on 4/25/17.
 */
@Module
public class PlaygroundModule {

    public PlaygroundModule() {
    }

    @Provides
    static LongRunningObservableFactory provideLongRunningObservableFactory() {
        return new LongRunningObservableFactory();
    }

    @Provides
    static String provideSound() {
        return "Mi sonido del modulo";
    }

    @Provides
    Pet providePet(Dog dog) {
        return dog;
    }
}
