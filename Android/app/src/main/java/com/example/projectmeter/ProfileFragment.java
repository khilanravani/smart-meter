package com.example.projectmeter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment {

    TextView userName, emailId, phoneNumber, meter_status, fullName;
    TextView meterId;

    String BASE_URL = "http://smart-meter-guj.herokuapp.com/rest/user/";
    String url;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        String name = getArguments().getString("username");

        url = BASE_URL + name + "/?format=json";

        meterId = view.findViewById(R.id.user_id);
        userName = view.findViewById(R.id.user_name);
        fullName = view.findViewById(R.id.full_name);
        emailId = view.findViewById(R.id.email_id);
        phoneNumber = view.findViewById(R.id.phone_number);
        meter_status = view.findViewById(R.id.meter_status);

        loadProfile(url);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("User Profile");
    }

    public void loadProfile(String url) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("*****", response.toString());
                    meterId.setText(String.valueOf(response.getString("meter_id")));
                    userName.setText(String.valueOf(response.getJSONObject("user").getString("username")));
                    String temp = String.valueOf(response.getJSONObject("user").getString("first_name")
                            + " " + response.getJSONObject("user").getString("last_name"));
                    fullName.setText(temp);
                    emailId.setText(String.valueOf(response.getJSONObject("user").getString("email")));
                    phoneNumber.setText(String.valueOf(response.getString("contact_number")));
                    meter_status.setText(response.getString("meter_status"));
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

        queue.add(jsonObjectRequest);
    }

}
