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


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView userName, emailId, phoneNumber, address;
    TextView meterId;

    String name;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = getArguments().getString("username");

        meterId = view.findViewById(R.id.user_id);
        userName = view.findViewById(R.id.user_name);
        emailId = view.findViewById(R.id.email_id);
        phoneNumber = view.findViewById(R.id.phone_number);
        address = view.findViewById(R.id.address);

        String eid = "harshendrashah@abc.com";
        int position = 0;
        for (int x = 0; x < UserData.emailId.length; x++) {
            if(UserData.emailId[x].equals(eid)) {
                Log.i("----", "" + x);
                position = x;
                break;
            }
        }

        meterId.setText(String.valueOf(UserData.meterId[position]));
        emailId.setText(UserData.emailId[position]);
        userName.setText(UserData.userName[position]);
        phoneNumber.setText(UserData.phoneNumber[position]);

        address.setText(UserData.address[position]);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("User Profile");
    }

}
