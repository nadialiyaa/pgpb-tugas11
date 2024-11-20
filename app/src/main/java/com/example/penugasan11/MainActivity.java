package com.example.penugasan11;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_NOTE_REQUEST = 1;
    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<>();

    private static final int EDIT_NOTE_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Button buttonAddNote = findViewById(R.id.button_add_note);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Initialize adapter and set it to RecyclerView
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        // Set initial empty list to adapter
        adapter.setNotes(noteList);

        // Handle the "Add Note" button
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra("ID", noteList.indexOf(note)); // Pass note index
                intent.putExtra("TITLE", note.getTitle());
                intent.putExtra("DESCRIPTION", note.getDescription());
                intent.putExtra("DATE", note.getDate());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("TITLE");
            String description = data.getStringExtra("DESCRIPTION");
            String date = data.getStringExtra("DATE");

            // Create a new Note object and add it to the list
            Note newNote = new Note(title, description, date);
            noteList.add(newNote);

            // Notify the adapter about the data change
            adapter.setNotes(noteList);
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK && data != null) {
            int id = data.getIntExtra("ID", -1);
            if (id != -1) {
                String title = data.getStringExtra("TITLE");
                String description = data.getStringExtra("DESCRIPTION");
                String date = data.getStringExtra("DATE");

                Note updatedNote = new Note(title, description, date);
                noteList.set(id, updatedNote);
                adapter.setNotes(noteList);
        }
    }
}}
