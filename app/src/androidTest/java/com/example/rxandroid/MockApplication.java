package com.example.rxandroid;

import com.example.rxandroid.di.component.Graph;

/**
 * Created by Dustin on 2/17/16.
 */
public class MockApplication extends App {

    @Override
    protected Graph createComponent() {
        return DaggerRepresentativeActivityTest_MockSampleAppComponent.builder().build();
    }
}
