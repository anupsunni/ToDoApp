package com.example.crudopp20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    String TAG = "789456 Detail Fragment";
    ArrayList<String> list ;
    ArrayList<ItemDetails> mainList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail,container,false);
        Button save = v.findViewById(R.id.fragment_detail_save);
        ImageView image = v.findViewById(R.id.fragment_detail_image);
        final EditText name = v.findViewById(R.id.fragment_detail_name);
        Button cancel = v.findViewById(R.id.fragment_detail_cancel);


        MainActivity mainActivity = (MainActivity) getActivity();
//        list = mainActivity.list;
        mainList = mainActivity.mainList;

        /*byte[] byteArray = getArguments().getByteArray("image");
        String displayName = list.get(getArguments().getInt("position"));
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image.setImageBitmap(bmp);*/



        InputStream inputstream= null;
        try {
            inputstream = getActivity().getAssets().open(mainList.get(getArguments().getInt("position")).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Drawable drawable = Drawable.createFromStream(inputstream, null);
        image.setImageDrawable(drawable);

        name.setText(mainList.get(getArguments().getInt("position")).getName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().matches(mainList.get(getArguments().getInt("position")).getName())){ getActivity().getSupportFragmentManager().popBackStack(); return;}
/*
                list.set(getArguments().getInt("position"),name.getText().toString());
*/

                mainList.get(getArguments().getInt("position")).setName(name.getText().toString());

                Toast.makeText(getActivity(), "List Updated", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return v;
    }



}