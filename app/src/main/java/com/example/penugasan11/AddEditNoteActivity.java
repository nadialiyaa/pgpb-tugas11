package com.example.penugasan11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextDate = findViewById(R.id.edit_text_date);

        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonUpdate = findViewById(R.id.button_update);

        Intent intent = getIntent();
        if (intent.hasExtra("ID")) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra("TITLE"));
            editTextDescription.setText(intent.getStringExtra("DESCRIPTION"));
            editTextDate.setText(intent.getStringExtra("DATE"));

            buttonAdd.setVisibility(View.GONE); // Hide Add Button
        } else {
            setTitle("Add Note");
            buttonUpdate.setVisibility(View.GONE); // Hide Update Button
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
                }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = editTextDate.getText().toString();

        if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("TITLE", title);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("DATE", date);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = editTextDate.getText().toString();

        if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = getIntent().getIntExtra("ID", -1);
        Intent intent = new Intent();
        intent.putExtra("ID", id);
        intent.putExtra("TITLE", title);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("DATE", date);
        setResult(RESULT_OK, intent);
        finish();
    }
}


