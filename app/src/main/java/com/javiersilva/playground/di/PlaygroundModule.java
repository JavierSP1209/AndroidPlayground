package com.javiersilva.playground.di;

import com.javiersilva.playground.LongRunningObservableFactory;
import com.javiersilva.playground.dagger.Dog;
import com.javiersilva.playground.dagger.Owner;
import com.javiersilva.playground.dagger.Pet;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javiersilva on 4/25/17.
 */
@Module
public class PlaygroundModule {

    @Provides
    static LongRunningObservableFactory provideLongRunningObservableFactory() {
        return new LongRunningObservableFactory();
    }

    @Provides
    static Pet providePet(Dog dog) {
        return dog;
    }
}
