package com.example.rahulkalamkar.myrxapplication.CompositeData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.rahulkalamkar.myrxapplication.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CompositeDataExample extends AppCompatActivity {

    private String TAG = CompositeDataExample.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private List<NoteModel> prepareNote() {
        List<NoteModel> noteModelList = new ArrayList<NoteModel>();
        noteModelList.add(new NoteModel("1", "buy tooth paste!"));
        noteModelList.add(new NoteModel("2", "call brother!"));
        noteModelList.add(new NoteModel("3", "watch narcos tonight!"));
        noteModelList.add(new NoteModel("4", "pay power bill!"));
        return noteModelList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_data_example);

        Observable<NoteModel> noteModelObservable = getObservable();
        compositeDisposable.add(
                noteModelObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new io.reactivex.functions.Function<NoteModel, NoteModel>() {
                            @Override
                            public NoteModel apply(NoteModel noteModel) throws Exception {
                                noteModel.setName(noteModel.getName().toUpperCase());
                                return noteModel;
                            }
                        })
                        .subscribeWith(getObserver())
        );
    }

    private DisposableObserver<NoteModel> getObserver() {
        return new DisposableObserver<NoteModel>() {
            @Override
            public void onNext(NoteModel noteModel) {
                Log.e(TAG, "onNext " + noteModel.getName());
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

    private Observable<NoteModel> getObservable() {
        final List<NoteModel> noteModelList = prepareNote();
        return Observable.create(new ObservableOnSubscribe<NoteModel>() {
            @Override
            public void subscribe(ObservableEmitter<NoteModel> emitter) throws Exception {
                for (NoteModel noteModel : noteModelList) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(noteModel);
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
