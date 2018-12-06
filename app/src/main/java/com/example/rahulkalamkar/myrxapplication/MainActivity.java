package com.example.rahulkalamkar.myrxapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    // To avoid memory leak error when activity finishes before updating UI
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Observable
        Observable<String> animalObservable = getAnimalObservable();
        // Observer
        io.reactivex.Observer<String> animalObserver = getAnimalObserver();

        // Observer subscribing to Observable
        animalObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animalObserver);
    }

    private Observer<String> getAnimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                // Asign disposal
                disposable = d;
                Log.e(TAG, "onSubscribe()");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext()");
                Log.e(TAG, "Name " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete()");
                Log.e(TAG, "All items are emitted");
            }
        };
    }

    private Observable<String> getAnimalObservable() {
        return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // don't send events once the activity is destroyed
        disposable.dispose();
    }
}
