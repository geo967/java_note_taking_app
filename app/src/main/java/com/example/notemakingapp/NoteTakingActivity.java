package com.example.notemakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class NoteTakingActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_taking_activity);
        EditText noteText = findViewById(R.id.note_content_id);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            noteText.setText((CharSequence) HomeActivity.list.get(noteId));
        } else {
            HomeActivity.list.add("");
            noteId = HomeActivity.list.size() - 1;

        }
        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                HomeActivity.list.set(noteId, String.valueOf(s));
               /* SharedPreferences sp = getSharedPreferences("file", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                HashSet<String> set = new HashSet<>(HomeActivity.list);
                ed.putStringSet("Notes", set);
                ed.apply();*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void saveNote(View view) {
        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        SharedPreferences sp = getSharedPreferences("file", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        HashSet<String> set = new HashSet<>(HomeActivity.list);
        ed.putStringSet("Notes", set);
        ed.apply();
        Intent intent = new Intent(NoteTakingActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
