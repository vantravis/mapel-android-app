package com.example.myapplication.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {


    @BindView(R.id.tv_location)
    TextView tv_location;
    String kota;
    SharedPrefManager sharedPrefManager;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_beranda, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);

        kota = getArguments().getString("kota");
        sharedPrefManager = new SharedPrefManager(getActivity());

        if (!kota.isEmpty()) {
            tv_location.setText(kota);
        }

        return view;
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String city);
    }

}