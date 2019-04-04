package com.example.projectmeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PredictionActivity extends AppCompatActivity {

    GraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        graph = findViewById(R.id.graph);

//        plotGraph();
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

    }


//    private void plotGraph() {
//
//        String graphURL = BASE_URL + "/data/histoday?fsym=" + "&tsym=USD&limit=7";
//
//        RequestQueue queue = Volley.newRequestQueue(PredictionActivity.this);
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, graphURL, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray js = response.getJSONArray("Data");
//                    ArrayList<String> date = new ArrayList<>();
//                    ArrayList<Double> price = new ArrayList<>();
//                    DataPoint[] values = new DataPoint[js.length()];
//                    for (int i = 0; i < js.length(); i++) {
//                        JSONObject data = js.getJSONObject(i);
//                        date.add(getDate(data.getLong("time")));
//                        price.add(data.getDouble("close"));
//                        DataPoint v = new DataPoint(new Date(data.getLong("time")).getTime(), price.get(i));
//                        values[i] = v;
//                    }
//
//                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);
//
//                    graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//                        @Override
//                        public String formatLabel(double value, boolean isValueX) {
//                            if (isValueX) {
//                                // show normal x values
//                                return getDate(new Double(value).longValue());
//                            } else {
//
//                                // show currency for y values
//                                return super.formatLabel(value, isValueX);
//                            }
//                        }
//                    });
//
//                    graph.addSeries(series);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("******", "Error");
//            }
//        });
//
//        queue.add(jsObjRequest);
//
//    }
//
//    private String getDate(long time) {
//        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//        cal.setTimeInMillis(time * 1000);
//        String date = DateFormat.format("dd-MM", cal).toString();
//        return date;
//    }
}
