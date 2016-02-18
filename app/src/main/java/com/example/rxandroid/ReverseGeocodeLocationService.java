package com.example.rxandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.example.rxandroid.error.MissingLocationPermissionException;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Dustin on 2/12/15.
 */
public class ReverseGeocodeLocationService {

    private final Context _context;
    private final LocationManager _locationManager;
    private final Geocoder _geocoder;

    public ReverseGeocodeLocationService(Context context, LocationManager locationManager, Geocoder geocoder) {
        _context = context;
        _locationManager = locationManager;
        _geocoder = geocoder;
    }

    public Observable<String> getCurrentZip() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (!_locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    subscriber.onError(new Exception("GPS turned off"));
                } else {
                    int permissionCheck = ContextCompat.checkSelfPermission(_context,
                            Manifest.permission.ACCESS_FINE_LOCATION);
                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        _locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                try {
                                    Observable.from(_geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1)).take(1).subscribe(new Subscriber<Address>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            subscriber.onError(e);
                                        }

                                        @Override
                                        public void onNext(Address address) {
                                            subscriber.onNext(address.getPostalCode());
                                            subscriber.onCompleted();
                                        }
                                    });
                                } catch (IOException e) {
                                    subscriber.onError(e);
                                }
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {
                                subscriber.onError(new Exception("provider was disabled"));
                            }
                        }, null);
                    } else {
                        // permission denied return error
                        subscriber.onError(new MissingLocationPermissionException());
                    }
                }
            }
        });
    }
}
