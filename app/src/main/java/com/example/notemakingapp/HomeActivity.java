package com.example.notemakingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    static List<String> list = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_actitivty);

        SearchView mySearch = findViewById(R.id.search_view_id);
        GridView gridView = findViewById(R.id.gridView_id);
        SharedPreferences sp = getSharedPreferences("file", MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sp.getStringSet("Notes", null);

        if (set == null) {
            list.add("Example note");
        } else {
            list = new ArrayList<>(set);
        }
        FloatingActionButton fab = findViewById(R.id.add_note_id);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NoteTakingActivity.class);
                startActivity(intent);
            }
        });
        myAdapter = new MyAdapter(this, list);
        gridView.setAdapter(myAdapter);


        mySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(list.contains(query)){
                myAdapter.getFilter().filter(query);}
                else{
                    Toast.makeText(HomeActivity.this,"No text found",Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*myAdapter.getFilter().filter(newText);
                return true;*/
                return false;
            }
        });
    }

}