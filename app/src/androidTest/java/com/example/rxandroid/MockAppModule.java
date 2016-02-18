package com.example.rxandroid;

import com.example.rxandroid.api.RepresentativeApi;
import com.example.rxandroid.api.WhoIsMyRep;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dustin on 2/17/16.
 */
@Module
public class MockAppModule {

    @Provides
    @Singleton
    ReverseGeocodeLocationService provideReverseGeocoderService() {
        return Mockito.mock(ReverseGeocodeLocationService.class);
    }

    @Provides
    @Singleton
    RepresentativeApi provideRepresentativeApi() {
        return Mockito.mock(RepresentativeApi.class);
    }
}
