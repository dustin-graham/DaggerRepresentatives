package com.example.rxandroid;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rxandroid.api.Representative;
import com.example.rxandroid.api.RepresentativeApi;
import com.example.rxandroid.di.component.Graph;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import rx.Observable;
import rx.functions.Func1;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Dustin on 2/17/16.
 */

@RunWith(AndroidJUnit4.class)
public class RepresentativeActivityTest {

    @Inject
    ReverseGeocodeLocationService _reverseGeocodeLocationService;

    @Inject
    RepresentativeApi _representativeApi;

    @Singleton
    @Component(modules = MockAppModule.class)
    public interface MockSampleAppComponent extends Graph {
        void inject(RepresentativeActivityTest activityTest);
    }

    @Rule
    public ActivityTestRule<FunnyRepresentativesActivity> _activityTestRule = new ActivityTestRule<>(FunnyRepresentativesActivity.class, true, false);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockApplication app
                = (MockApplication) instrumentation.getTargetContext().getApplicationContext();
        MockSampleAppComponent component = (MockSampleAppComponent) app.getComponent();
        component.inject(this);
    }

    @Test
    public void testZipCodeLookup() throws Exception {
        Mockito.when(_representativeApi.representativesByZipCode(Mockito.anyString())).thenAnswer(new Answer<Observable<Representative>>() {
            @Override
            public Observable<Representative> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return Observable.range(0,5).map(new Func1<Integer, Representative>() {
                    @Override
                    public Representative call(Integer integer) {
                        Representative representative = new Representative();
                        representative.district = "district 2";
                        representative.isFunny = true;
                        representative.link = "https://google.com";
                        representative.name = "Representative " + integer;
                        representative.party = "In the Back";
                        representative.phone = "867-5309";
                        representative.state = "crazy";
                        return representative;
                    }
                });
            }
        });

        Mockito.when(_reverseGeocodeLocationService.getCurrentZip()).thenAnswer(new Answer<Observable<String>>() {
            @Override
            public Observable<String> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return Observable.just("85308");
            }
        });

        _activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.look_up_zip_button)).perform(click());

        //verify that the zip code is set to the search field
        onView(withId(R.id.searchField)).check(matches(withText("85308")));
    }
}
