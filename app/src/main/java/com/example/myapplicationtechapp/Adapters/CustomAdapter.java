package com.example.myapplicationtechapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.myapplicationtechapp.DetailsTechActivity;
import com.example.myapplicationtechapp.Pojodata.Pojoclass;
import com.example.myapplicationtechapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Pojoclass> data= Collections.emptyList();
    Pojoclass current;
    int currentPos=0;



    public CustomAdapter(Context context, List<Pojoclass> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlelayout_recy,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Pojoclass current=data.get(i);
        myViewHolder.textView.setText(current.name);
        myViewHolder.textView1.setText(current.cources);

        // load image into imageview using glide
        Glide.with(context).load("http://96.125.162.228/Technology/uploads/cms/" + current.images)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(myViewHolder.imageView);


        myViewHolder.ll_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewfriend = new Intent(context, DetailsTechActivity.class);
                context.startActivity(viewfriend);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView textView1;
        LinearLayout ll_click;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_php);
            textView = itemView.findViewById(R.id.tv_phptext);
            textView1 = itemView.findViewById(R.id.tv_descrip);
            ll_click = itemView.findViewById(R.id.ll_click);
        }
    }
}
