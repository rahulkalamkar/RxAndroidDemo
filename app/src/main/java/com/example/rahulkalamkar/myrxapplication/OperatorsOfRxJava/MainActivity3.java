package com.example.rahulkalamkar.myrxapplication.OperatorsOfRxJava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.rahulkalamkar.myrxapplication.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity3 extends AppCompatActivity {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String TAG = MainActivity3.class.getSimpleName();
    private Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        simpleNumberWithFilter();
        simpleNumberWithRange();
    }

    private void simpleNumberWithFilter() {

        Observable.fromArray(numbers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new DisposableObserver<Integer>() {

                    @Override
                    public void onNext(Integer o) {
                        Log.e(TAG, "number is even " + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "All items are emitted");
                    }
                });
    }

    private void simpleNumberWithRange() {
        Observable.range(0, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer o) {
                        Log.e(TAG, "number is even " + o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "All items are emitted");
                    }
                });
    }
}
