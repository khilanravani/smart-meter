package com.example.projectmeter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class PredictionFragment extends Fragment {

    GraphView graph;
    String name;

    public PredictionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prediction, container, false);

        name = getArguments().getString("username");

        graph = view.findViewById(R.id.graph);

        DataPoint[] values = new DataPoint[6];
        DataPoint v = new DataPoint(1131, 0.79);
        values[0] = v;
        v = new DataPoint(1131, 0.79);
        values[1] = v;
        v = new DataPoint(1147, 0.83);
        values[2] = v;
        v = new DataPoint(1201, 0.88);
        values[3] = v;
        v = new DataPoint(1217, 0.84);
        values[4] = v;
        v = new DataPoint(1247, 0.81);
        values[5] = v;

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                return super.formatLabel(value, isValueX);
            }
        });

        graph.addSeries(series);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Prediction");
    }
}
