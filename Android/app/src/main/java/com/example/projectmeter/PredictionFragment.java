package com.example.projectmeter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PredictionFragment extends Fragment {

    GraphView graph;
    String url, BASE_URL = "http://smart-meter-guj.herokuapp.com/rest/user/record/";

    public PredictionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prediction, container, false);

        String name = getArguments().getString("username");

        url = BASE_URL+ name +"/?format=json";

        graph = view.findViewById(R.id.graph);

        plotGraph(url);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Statistics");
    }

    public void plotGraph(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    DataPoint[] values = new DataPoint[response.length()];
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for(int i = 0; i < response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);
                        Date date = format.parse(data.getString("time"));
                        DataPoint v = new DataPoint(date, data.getDouble("current"));
                        values[i] = v;
                        Log.i("***", String.valueOf(values[i].getX()));
                    }
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);

                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
                    graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

                    // set manual x bounds to have nice steps
                    graph.getViewport().setMinX(values[0].getX());
                    graph.getViewport().setMaxX(values[values.length - 1].getX());
                    graph.getViewport().setXAxisBoundsManual(true);

                    // as we use dates as labels, the human rounding to nice readable numbers
                    // is not necessary
                    graph.getGridLabelRenderer().setHumanRounding(false);

                    graph.addSeries(series);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("******", "Error" + error.toString());
            }
        });

        queue.add(jsonArrayRequest);
    }
}
