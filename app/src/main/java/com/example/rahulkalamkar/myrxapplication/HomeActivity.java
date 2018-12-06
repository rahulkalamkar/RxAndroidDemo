package com.example.rahulkalamkar.myrxapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    HomeRecyclerAdapter homeRecyclerAdapter;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        initializeList();
        homeRecyclerAdapter = new HomeRecyclerAdapter(this, list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeRecyclerAdapter);
    }

    private void initializeList() {
        list = new ArrayList<>();
        list.add("MainActivity");
        list.add("CompositeDisposableActivity");
        list.add("MainActivity2");
        list.add("WebServiceActivity");
        list.add("FlightApp");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
