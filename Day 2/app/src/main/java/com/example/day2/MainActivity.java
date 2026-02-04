package com.example.day2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.day2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;


public class MainActivity extends AppCompatActivity {

    private static final List<String> names = List.of("Patric Ross", "Kelly Wood", "James Moore", "Janice Coleman", "Mary Carter");

    ArrayList<String> myNames = new ArrayList<>(
            List.of(
                    "Ahmed Ali",
                    "Mohamed Hassan",
                    "Youssef Ibrahim",
                    "Omar Khaled",
                    "Mahmoud Adel",
                    "Mostafa Saeed",
                    "Hany Fathy",
                    "Islam Nabil",
                    "Ali Gamal",
                    "Muaz Osama"
            )
    );

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private NamesAdapter adapter;

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

        adapter = new NamesAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setNamesList(myNames);

        // Lab 1
        // Get the count of strings that length is <12 and start with "J"
        long countNamesStartWithJ = names.stream()
                .filter(e -> (e.length() < 12) && (e.startsWith("J")))
                .count();

        Log.d("TAG1", String.valueOf(countNamesStartWithJ));

        // Lab 2
        Stream<String> stream = Stream.of("one", "two", "three", "four");
        boolean match = stream.anyMatch(s -> s.contains("four")); // expected : true

        Log.d("TAG2", String.valueOf(match));

        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        binding.search.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void afterTextChanged(Editable s) {

                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                emitter.onNext(s.toString());
                            }
                        });
                    }
                }).doOnNext(e -> Log.d("TAG3", "moaz upstream request"))
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .map(q -> {
                    if (q.isEmpty()) return myNames;
                    ArrayList<String> filteredList = new ArrayList<>();

                    for (String name : myNames) {
                        if (name.toLowerCase().startsWith(q.toLowerCase())) {
                            filteredList.add(name);
                        }
                    }
                    return filteredList;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.d("TAG3", "moaz downstream hitted");
                    adapter.setNamesList(result);
                });

    }
}