package com.example.notemakingapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;


public class MyAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<String> listPojo;



    public MyAdapter(Context context, List<String> listPojo) {
        this.context = context;
        this.listPojo = listPojo;
    }

    @Override
    public int getCount() {
        return listPojo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_design, parent, false);
        TextView content = convertView.findViewById(R.id.note_sample_id);
        content.setText(listPojo.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is the note", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, NoteTakingActivity.class);
                intent.putExtra("noteId", position);
                context.startActivity(intent);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_delete).setTitle("Are you sure u want to delete?")
                        .setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listPojo.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });
        return convertView;
    }


    public Filter getFilter() {
        return new Filter() {
             @Override
             protected FilterResults performFiltering(CharSequence constraint) {
                 FilterResults filterResults = new FilterResults();
                 if (constraint == null || constraint.length() == 0) {
                     filterResults.count = listPojo.size();
                     filterResults.values = listPojo;
                 } else {
                     String searchStr = constraint.toString().toLowerCase();
                     List<String> resultData = new ArrayList<>();

                     for (String s : listPojo) {
                         if (s.contains(searchStr)) {
                             resultData.add(s);
                         }
                         filterResults.count = resultData.size();
                         filterResults.values = resultData;
                     }
                 }
                 return filterResults;
             }

             @Override
             protected void publishResults(CharSequence constraint, FilterResults results) {

                listPojo= (List<String>)results.values;
                 notifyDataSetChanged();
             }
         };
    }
}
