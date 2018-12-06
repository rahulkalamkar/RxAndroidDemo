package com.example.rahulkalamkar.myrxapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CompositeDisposable extends AppCompatActivity {

    private String TAG = CompositeDisposable.class.getSimpleName();
    private io.reactivex.disposables.CompositeDisposable compositeDisposable = new io.reactivex.disposables.CompositeDisposable();

    private DisposableObserver<String> getListWithStartingLetter() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                Log.e(TAG, "Name " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "All items are emitted");
            }
        };
    }

    private DisposableObserver<String> getListWithCapsLetters() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.e(TAG, "Name " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "All items are emitted");
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_disposable);
        //Observable
        Observable<String> stringObservable = getAnimalObservable();

        //Observer
        DisposableObserver<String> filterObserver = getListWithStartingLetter();

        DisposableObserver<String> capsObserver = getListWithCapsLetters();

        // Filter list with letter
        compositeDisposable.add(
                stringObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("b");
                            }
                        })
                        .subscribeWith(filterObserver));

        // Filter list with caps long
        compositeDisposable.add(
                stringObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("c");
                            }
                        })
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) throws Exception {
                                return s.toUpperCase();
                            }
                        })
                        .subscribeWith(capsObserver));


    }

    private Observable<String> getAnimalObservable() {
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "Butterfly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog"
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
