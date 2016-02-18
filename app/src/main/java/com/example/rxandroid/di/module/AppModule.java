package com.example.rxandroid.di.module;

import android.app.Application;
import android.content.Context;
import android.location.Geocoder;
import android.location.LocationManager;

import com.example.rxandroid.api.RepresentativeApi;
import com.example.rxandroid.api.WhoIsMyRep;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by Dustin on 2/17/16.
 */
@Module
public class AppModule {

    private final Application _context;

    public AppModule(Application context) {
        _context = context;
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    Geocoder provideGeocoder() {
        return new Geocoder(_context);
    }

    @Provides
    @Singleton
    WhoIsMyRep provideApi() {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl("http://whoismyrepresentative.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return restAdapter.create(WhoIsMyRep.class);
    }

    @Provides
    @Singleton
    RepresentativeApi provideRepresentativeApi(WhoIsMyRep api) {
        return new RepresentativeApi(api);
    }
}
