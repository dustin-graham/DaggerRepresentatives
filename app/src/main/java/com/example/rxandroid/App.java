package com.example.rxandroid;

import android.app.Application;

import com.example.rxandroid.di.component.Graph;
import com.example.rxandroid.di.component.SampleAppComponent;

import timber.log.Timber;

/**
 * Created by Dustin on 2/10/15.
 */
public class App extends Application {

    private Graph _component;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        _component = createComponent();
    }

    protected Graph createComponent() {
        return SampleAppComponent.Initializer.init(this);
    }

    public Graph getComponent() {
        return _component;
    }
}
