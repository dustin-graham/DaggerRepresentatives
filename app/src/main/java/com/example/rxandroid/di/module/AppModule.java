package com.example.rxandroid.di.module;

import com.example.rxandroid.api.RepresentativeApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dustin on 2/17/16.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    RepresentativeApi provideRepresentativeApi() {
        return new RepresentativeApi();
    }
}
