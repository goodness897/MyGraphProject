package com.example.goodn.mygraphexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llGraph = (LinearLayout) findViewById(R.id.ll_graph);
        llGraph.addView(new MyGraphView(this));
    }
}
