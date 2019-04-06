package com.example.projectmeter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    String BASE_URL = "http://smart-meter-guj.herokuapp.com/rest/user/record/";
    String name;

    ExpandableCardView previousReadings;
    CardView predict, profile;
    TextView currentReadings, voltage, ampere, power;
    Button billingButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        name = getArguments().getString("username");
        currentReadings = view.findViewById(R.id.current_reading);
        voltage = view.findViewById(R.id.voltage);
        power = view.findViewById(R.id.power);
        ampere = view.findViewById(R.id.ampere);
        predict = view.findViewById(R.id.predict);
        profile = view.findViewById(R.id.profile);
        billingButton = view.findViewById(R.id.billing_button);

        previousReadings = view.findViewById(R.id.previous_readings);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", name);
                profileFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.layout, profileFragment)
                        .addToBackStack(ProfileFragment.class.getSimpleName())
                        .commit();
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PredictionFragment predictionFragment = new PredictionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", name);
                predictionFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.layout, predictionFragment)
                        .addToBackStack(PredictionFragment.class.getSimpleName())
                        .commit();
            }
        });

        billingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillingFragment billingFragment = new BillingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", name);
                billingFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.layout, billingFragment)
                        .addToBackStack(BillingFragment.class.getSimpleName())
                        .commit();
            }
        });

        String url = BASE_URL + name + "/?format=json";
        loadData(url);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Smart Meter");
    }

    public void loadData(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject userRecord = null;
                try {
                    userRecord = response.getJSONObject(response.length()-1);
                    currentReadings.setText(String.valueOf(userRecord.getDouble("energy")));
                    voltage.setText(String.valueOf(userRecord.getDouble("volt")));
                    ampere.setText(String.valueOf(userRecord.getDouble("current")));
                    power.setText(String.valueOf(userRecord.getDouble("watt")));
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
