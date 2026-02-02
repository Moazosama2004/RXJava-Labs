package com.example.day1.presenter;

import io.reactivex.rxjava3.disposables.Disposable;

public interface ObserverTestView<T>{
    void onSubscribe(Disposable disposable , int type);

    void onNext(T value , int type);

    void onError(Throwable error , int type);

    void onComplete(int type);
}
