package com.example.day3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.day3.databinding.ActivityMainBinding;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @SuppressLint("CheckResult")
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

        Observable namseObservable =

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Observable myobservable = Observable.create(source -> {
            Log.d("TAG", "Hello MAD");
            source.onNext(1);
            source.onNext(2);
            source.onNext(3);
            source.onNext(4);
            source.onNext(5);
        });

        //noinspection unchecked
        myobservable.subscribeOn(Schedulers.io())
                .doOnNext(Integer -> System.out.println("Emiiting item" + Integer + " on:" + Thread.currentThread().getName()))
                .observeOn(Schedulers.newThread())
                .doOnNext(Integer -> System.out.println("After Emiiting item" + Integer + " on:" + Thread.currentThread().getName()))
                .observeOn(Schedulers.computation())
                .doOnNext(Integer -> System.out.println("After computation Thread Emiiting item" + Integer + " on:" + Thread.currentThread().getName()))
                .subscribe(Integer -> System.out.println("Consuming item" + Integer + " on:" + Thread.currentThread().getName()));
    }

    /*
        My Ans :
            1 - Emiiting item -> io
            2 - After Emiiting item -> new Thread
            3 - After computation Thread Emiiting item -> computation
            4 - Consuming item -> computation
    */
}