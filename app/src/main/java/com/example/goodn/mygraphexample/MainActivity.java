package com.example.goodn.mygraphexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(80);
        list.add(70);
        list.add(40);
        list.add(30);
        list.add(60);
        llGraph = (LinearLayout) findViewById(R.id.ll_graph);
        llGraph.addView(new MyGraphView(this, list));
    }
}
