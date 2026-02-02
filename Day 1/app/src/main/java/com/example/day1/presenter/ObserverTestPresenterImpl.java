package com.example.day1.presenter;

import com.example.day1.CounterObserver;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class ObserverTestPresenterImpl implements ObserverTestPresenter{
    private static final Integer[] arrays = {0, 1, 2, 3, 4, 5};
    private Observable<Integer> observable;
    private Observable<Integer> observableFromIterable;
    private ObserverTestView<Integer> observerTestView;

    public ObserverTestPresenterImpl(ObserverTestView<Integer> view) {
        this.observerTestView = view;
        observable = Observable.fromArray(arrays);
        observableFromIterable = Observable.fromIterable(List.of(arrays));
    }

    @Override
    public void observeFromArray() {
        observable.subscribe(
                new CounterObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        observerTestView.onSubscribe(disposable, 0);
                    }

                    @Override
                    public void onNext(Integer value) {
                        observerTestView.onNext(value,0);
                    }

                    @Override
                    public void onError(Throwable error) {
                        observerTestView.onError(error,0);
                    }

                    @Override
                    public void onComplete() {
                        observerTestView.onComplete(0);
                    }
                }
        );
    }

    @Override
    public void observeFromIterable() {
        observableFromIterable.subscribe(
                new CounterObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        observerTestView.onSubscribe(disposable, 1);
                    }

                    @Override
                    public void onNext(Integer value) {
                        observerTestView.onNext(value,1);
                    }

                    @Override
                    public void onError(Throwable error) {
                        observerTestView.onError(error,1);
                    }

                    @Override
                    public void onComplete() {
                        observerTestView.onComplete(1);
                    }
                }
        );
    }
}
