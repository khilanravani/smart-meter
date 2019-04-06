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
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        Log.i("URL:", url);
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
                    for(int i = 0; i < response.length(); i++) {
                        JSONObject data = response.getJSONObject(i);
                        long epoch = new java.text.SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
                                .parse(data.getString("time")).getTime() / 1000;
                        DataPoint v = new DataPoint(epoch, data.getDouble("current"));
                        values[i] = v;
                    }

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);

                    graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {
                            if (isValueX) {
                                // show normal x values
                                return getDate(new Double(value).longValue());
                            } else {

                                // show currency for y values
                                return super.formatLabel(value, isValueX);
                            }
                        }
                    });

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
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("hh:mm", cal).toString();
        return date;
    }
}
