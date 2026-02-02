package com.example.day1;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public interface CounterObserver<T>  extends Observer<T> {
    void onSubscribe(Disposable disposable);
    void onNext(T value);
    void onError(Throwable error);
    void onComplete();

}
