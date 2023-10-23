package com.cs407.lab5_milestone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditorActivity extends AppCompatActivity {

    private TextView textView;
    public static ArrayList<Notes> note = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        textView = findViewById(R.id.textView);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (!username.isEmpty()) {
            textView.setText("Welcome, " + username + " to Notes app!");
        }

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        note = dbHelper.readNotes(username);

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Notes note : note) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NoteWritingActivity.class);
                intent.putExtra("noteid", String.valueOf(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editing_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addNoteItem) {
            Intent intent = new Intent(this, NoteWritingActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.logoutItem) {
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove("username").apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
