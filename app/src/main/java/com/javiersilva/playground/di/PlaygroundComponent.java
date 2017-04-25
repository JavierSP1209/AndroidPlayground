package com.javiersilva.playground.di;

import com.javiersilva.playground.LongRunningObservableFactory;
import com.javiersilva.playground.dagger.Owner;

import dagger.Component;

/**
 * Created by javiersilva on 4/25/17.
 */
@Component(modules = PlaygroundModule.class)
public interface PlaygroundComponent {
    Owner getOwner();

    LongRunningObservableFactory getLongRunningObservableFactory();

}
