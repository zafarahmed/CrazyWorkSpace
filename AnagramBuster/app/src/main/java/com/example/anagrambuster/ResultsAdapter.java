package com.example.anagrambuster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter {

    JSONArray results;
    Context context;

    public ResultsAdapter( Context context, JSONArray results )
    {
        this.context = context;
        this.results = results;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carditem, viewGroup, false);
// set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder viewHolder, int i) {
        try {
            ((MyViewHolder) viewHolder).name.setText( results.getString(i) );
        }
        catch ( JSONException e)
        {

        }

    }


    @Override
    public int getItemCount() {
        return results.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);

// get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);

        }
    }
}
