package com.example.rxandroid.di.component;

import com.example.rxandroid.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dustin on 2/17/16.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface SampleAppComponent extends Graph {
    final class Initializer {
        public static SampleAppComponent init() {
            return DaggerSampleAppComponent.builder().build();
        }
    }
}
