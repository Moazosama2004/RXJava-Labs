package com.example.day1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.day1.databinding.ActivityMainBinding;
import com.example.day1.presenter.ObserverTestPresenter;
import com.example.day1.presenter.ObserverTestPresenterImpl;
import com.example.day1.presenter.ObserverTestView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements ObserverTestView<Integer> {

    private ActivityMainBinding binding;
    private ObserverTestPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        presenter = new ObserverTestPresenterImpl(this);


        binding.fromArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.observeFromArray();
            }
        });


        binding.fromIterable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.observeFromIterable();
            }
        });

        // .intervalRange()
        Observable<Long> observableIntevalRange = Observable.intervalRange(1, 20, 3000, 3000, TimeUnit.MILLISECONDS);
        observableIntevalRange.subscribe(
                item -> Log.d("Interval Range : ", item.toString()),
                throwable -> Log.d("Error Interval Range : ", throwable.toString()),
                () -> Log.d("Completed", "Interval Completed.")
        );


        //.never()
        Observable neverObservable = Observable.never();
        Disposable disposable = neverObservable.subscribe(
                item -> Log.d("neverObservable Range : ", item.toString()),
                throwable -> Log.d("Error neverObservable Range : ", throwable.toString()),
                () -> Log.d("Completed", "neverObservable Completed.")
        );
        disposable.dispose();

    }


    @Override
    public void onSubscribe(Disposable disposable, int type) {
        if(type == 0) {
            binding.textView.append("From Array - onSubscribe\n");
        }
        else {
            binding.textView.append("From Iterable - onSubscribe\n");
        }
    }

    @Override
    public void onNext(Integer value, int type) {
        if(type == 0) {
            binding.textView.append("From Array - onNext: " + value.toString() + "\n");
        }
        else {
            binding.textView.append("From Iterable - onNext: " + value.toString() + "\n");
        }
    }

    @Override
    public void onError(Throwable error, int type) {
        if(type == 0 ) {
            binding.textView.append("From Array - onError: " + error.toString() + "\n");
        }
        else {
            binding.textView.append("From Iterable - onError: " + error.toString() + "\n");
        }
    }

    @Override
    public void onComplete(int type) {
        if(type == 0) {
            binding.textView.append("From Array - onComplete:" + "\n");
        }
        else {
            binding.textView.append("From Iterable - onComplete:" + "\n");
        }
    }
}