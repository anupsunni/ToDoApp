package com.example.crudopp20;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    String TAG = "RecyclerViewAdapter";

    ArrayList<String> imagesList;
    Context mContext;
    ArrayList<ItemDetails> mainList;

    ArrayList<String> list;
    EditListener editListener;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> imagesList, EditListener editListener, ArrayList<String> list,     ArrayList<ItemDetails> mainList) {
        this.mContext = mContext;
//        this.list = list;
//        this.imagesList = imagesList;
        this.editListener = editListener;
        this.mainList = mainList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_layout,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.i(TAG, "onBindViewHolder: Called... Pos: "+i);
        /*viewHolder.textV.setText(""+mNumbers.get(i));*/

        InputStream inputstream= null;
        try {
            inputstream = mContext.getAssets().open(mainList.get(i).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Drawable drawable = Drawable.createFromStream(inputstream, null);
        viewHolder.imageV.setImageDrawable(drawable);

        viewHolder.imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editListener.edit(i,mainList);
            }
        });

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editListener.showDetails(i,mainList);
            }
        });

        viewHolder.deleteR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editListener.delete(i);
            }
        });
        viewHolder.textV.setText(""+mainList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textV;
        ImageView imageV;
        ImageButton deleteR;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textV = itemView.findViewById(R.id.textV);
            imageV = itemView.findViewById(R.id.imageV);
            layout = itemView.findViewById(R.id.recycler_linear_layout);
            deleteR = itemView.findViewById(R.id.deleteR);
        }
    }

    public interface EditListener {
        public void edit(int position, ArrayList<ItemDetails> mainList);
        public void showDetails(int position, ArrayList<ItemDetails> mainList);
        public void delete(int position);
    }

}
