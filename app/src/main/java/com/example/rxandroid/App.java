package com.example.rxandroid;

import android.app.Application;

import com.example.rxandroid.di.component.SampleAppComponent;

import timber.log.Timber;

/**
 * Created by Dustin on 2/10/15.
 */
public class App extends Application {

    private SampleAppComponent _component;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        _component = SampleAppComponent.Initializer.init(this);
    }

    public SampleAppComponent getComponent() {
        return _component;
    }
}
