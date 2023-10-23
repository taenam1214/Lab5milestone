package com.cs407.lab5_milestone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteWritingActivity extends AppCompatActivity {

    public int noteid = -1;
    private EditText writeNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        writeNote = findViewById(R.id.writeNote);
        Button saveButton = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        String str = intent.getStringExtra("noteid");

        if (str != null) {
            noteid = Integer.parseInt(str);
        }

        Log.i("lab5", "NoteID: " + str);

        if (noteid != -1) {
            Notes note = EditorActivity.note.get(noteid);
            String noteContent = note.getContent();
            writeNote.setText(noteContent);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMethod();
            }
        });

        Button deleteButton = findViewById(R.id.buttonDelete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMethod();
            }
        });
    }

    public void saveMethod() {
        String content = writeNote.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTES_" + (EditorActivity.note.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTES_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    public void deleteMethod() {
        if (noteid != -1) {
            Context context = getApplicationContext();
            SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
            DBHelper dbHelper = new DBHelper(sqLiteDatabase);

            String title = "NOTES_" + (noteid + 1);
            String content = writeNote.getText().toString();

            dbHelper.deleteNotes(content, title);

            Intent intent = new Intent(this, EditorActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No note to delete", Toast.LENGTH_SHORT).show();
        }
    }

}
