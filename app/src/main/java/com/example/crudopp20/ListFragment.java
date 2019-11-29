package com.example.crudopp20;

import android.animation.LayoutTransition;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ListFragment extends Fragment {

    ArrayList<String> listImages;
    RecyclerView recyclerV;
    RecyclerViewAdapter adapter;
    String[] list1;
    ArrayList<String> list ;
    Button addB;
    ArrayList<ItemDetails> mainList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();
        list1 = mainActivity.list1;
        list = mainActivity.list;
        mainList = mainActivity.mainList;

        View v = inflater.inflate(R.layout.fragment_one,container,false);
        recyclerV = v.findViewById(R.id.recyclerV);

        if (savedInstanceState!=null){
//              list = savedInstanceState.getStringArrayList("list");
                mainList = savedInstanceState.getParcelableArrayList("mainlist");
        }


        initRecyclerView();

        return v;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("list",list);
        outState.putParcelableArrayList("mainlist",mainList);
    }


    public void updateRecyclerView(){
        adapter.notifyDataSetChanged();
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void initRecyclerView(){
        String[] images;
        try {
            images = getActivity().getAssets().list("images");
            listImages = new ArrayList<>(Arrays.asList(images));
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new RecyclerViewAdapter(getActivity(),listImages,editListener,list,mainList);
        recyclerV.setAdapter(adapter);
        recyclerV.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }



    RecyclerViewAdapter.EditListener editListener = new RecyclerViewAdapter.EditListener() {
        @Override
        public void edit(final int position, final ArrayList<ItemDetails> mainList) {

            final Dialog builder = new Dialog(getActivity());
            builder.setContentView(R.layout.alert_label_editor);

            builder.setTitle("Information");
            Button okB,cancelB;
            ImageView imageV2;
            final EditText nameE;
            LinearLayout linearL;


            okB = builder.findViewById(R.id.okB);
            linearL = builder.findViewById(R.id.linearL);
            nameE = builder.findViewById(R.id.nameE);
            cancelB = builder.findViewById(R.id.cancelB);
            imageV2 = builder.findViewById(R.id.imageV2);

            InputStream inputstream= null;
            try {
                inputstream = getActivity().getAssets().open(mainList.get(position).getImage());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Drawable drawable = Drawable.createFromStream(inputstream, null);
            imageV2.setImageDrawable(drawable);

            nameE.setText(mainList.get(position).getName());

            okB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (nameE.getText().toString().matches(mainList.get(position).getName())) {
                        builder.dismiss();

                        return;
                    }

/*
                    list.set(position,nameE.getText().toString());
*/

                    mainList.get(position).setName(nameE.getText().toString());

                    adapter.notifyDataSetChanged();

                    Toast.makeText(getActivity(), "List Updated", Toast.LENGTH_SHORT).show();
                    builder.dismiss();
                }
            });

            cancelB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.dismiss();
                }
            });

            builder.setCancelable(false);

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                linearL.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            }

/*
            linearL.animate().scaleX(2).setDuration(500).start();
*/

            builder.getWindow().setLayout((6 * width)/7, RecyclerView.LayoutParams.WRAP_CONTENT);
            builder.show();
        }

        @Override
        public void showDetails(int position, ArrayList<ItemDetails> mainList) {
            DetailsFragment detailsFragment = new DetailsFragment();



//            BitmapDrawable drawable = (BitmapDrawable) viewHolder.imageV.getDrawable();
//            Bitmap bitmap = drawable.getBitmap();
//            byte[] byte_image = getBytesFromBitmap(bitmap);
            Bundle b = new Bundle();
            b.putString("image",mainList.get(position).getImage());
            b.putInt("position", position);
            detailsFragment.setArguments(b);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,detailsFragment).addToBackStack(null).commit();
        }

        @Override
        public void delete(int position) {
            mainList.remove(position);
            adapter.notifyDataSetChanged();
        }
    };

}
