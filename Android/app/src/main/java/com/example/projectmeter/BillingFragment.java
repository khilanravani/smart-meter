package com.example.projectmeter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BillingFragment extends Fragment {

    String name, url;
    String BASE_URL = "http://smart-meter-guj.herokuapp.com/rest/user/bill/";
    List<Bill> billList;
    private RecyclerView recyclerView;
    private BillAdapter adapter;

    private TextView total, most_recent_time, energy;

    public BillingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_billing, container, false);

        name = getArguments().getString("username");
        url = BASE_URL + name + "/?format=json";
        recyclerView = view.findViewById(R.id.recycler_view);
        total = view.findViewById(R.id.total);
        energy = view.findViewById(R.id.energy);
        most_recent_time = view.findViewById(R.id.most_recent_time);

        fetchMostRecent();
        billList = new ArrayList<>();
        adapter = new BillAdapter(getContext(), billList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareBills(url);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Bills and History");
    }

    private void prepareBills(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = response.length() - 2; i >= 0; i--) {
                        JSONObject object = response.getJSONObject(i);
                        Bill b = new Bill(object.getString("time"),
                                object.getDouble("cost"), object.getBoolean("is_paid"));
                        billList.add(b);
                    }
                    adapter.notifyDataSetChanged();
                    JSONObject most_recent = response.getJSONObject(response.length() - 1);
                    total.setText("Rs." + String.valueOf(most_recent.getDouble("cost")));
                } catch (JSONException e) {
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

    private void fetchMostRecent() {
        String str = "http://smart-meter-guj.herokuapp.com/rest/user/record/" + name + "/?format=json";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, str, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject most_recent = response.getJSONObject(response.length() - 1);
                    energy.setText(String.valueOf(most_recent.getDouble("energy")));
                    most_recent_time.setText(String.valueOf(most_recent.getString("time")));
                } catch (JSONException e) {
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
