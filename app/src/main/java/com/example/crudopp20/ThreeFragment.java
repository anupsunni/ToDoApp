package com.example.crudopp20;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ThreeFragment extends Fragment {

    String TAG = "789456 Three Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_two,container,false);
        Button btn = v.findViewById(R.id.fragment_two_btn);
        Log.i("789456 3rd Fragment", "onCreateView: New Fragment");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new TwoFragment()).addToBackStack(null).commit();
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: ");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");

        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: ");

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: ");

        super.onDetach();
    }


    @Override
    public void onPause() {
        Log.i(TAG, "onPause: ");

        super.onPause();
    }



    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");

        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: ");

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: ");

        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: ");

        super.onStop();

    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Log.i(TAG, "onViewStateRestored: ");

        super.onViewStateRestored(savedInstanceState);
    }


}