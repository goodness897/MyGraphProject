package com.example.goodn.mygraphexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.HorizontalScrollView;
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
        list.add(60);
        list.add(60);

        list.add(60);
        list.add(60);
        list.add(60);
        list.add(60);

        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv_graph);
        HorizontalScrollView horizontalScrollViewS = (HorizontalScrollView) findViewById(R.id.hsv_graph_second);
        HorizontalScrollView horizontalScrollViewT = (HorizontalScrollView) findViewById(R.id.hsv_graph_third);
//        llGraph = (LinearLayout) findViewById(R.id.ll_graph);
        MyGraphView myGraphView = new MyGraphView(this, list);
        MyGraphView myGraphView2 = new MyGraphView(this, list);
        MyGraphView myGraphView3 = new MyGraphView(this, list);
        horizontalScrollView.addView(myGraphView);
        horizontalScrollViewS.addView(myGraphView2);
        horizontalScrollViewT.addView(myGraphView3);
    }
}
