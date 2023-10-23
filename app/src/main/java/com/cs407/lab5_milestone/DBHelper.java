package com.cs407.lab5_milestone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DBHelper {

    static SQLiteDatabase sqLiteDatabase;

    public DBHelper (SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes " +
                "(id INTEGER PRIMARY KEY, username TEXT, date TEXT, title TEXT, content TEXT, src TEXT)");
    }

    public ArrayList<Notes> readNotes(String username){

        createTable();
        Cursor c = sqLiteDatabase.rawQuery("SELECT * from notes where username like ?", new String[]{"%" + username + "%"});

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int contentIndex = c.getColumnIndex("content");

        c.moveToFirst();
        ArrayList<Notes> notesList = new ArrayList<>();

        while(!c.isAfterLast()){
            String title = c.getString(titleIndex);
            String date = c.getString(dateIndex);
            String content = c.getString(contentIndex);
            Notes note = new Notes(date, username, title, content);
            notesList.add(note);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return notesList;
    }

    public void saveNotes(String username, String title, String content, String date){
        createTable();
        sqLiteDatabase.execSQL("INSERT INTO notes (username, date, title, content) VALUES (?, ?, ?, ?)",
                new String[]{username, date, title, content});
    }

    public void updateNote(String title, String date, String content, String username) {
        createTable();
        sqLiteDatabase.execSQL("UPDATE notes set content = ?, date = ? where title = ? and username = ?",
                new String[]{content, date, title, username});
    }

    public void deleteNotes(String content, String title) {
        createTable();
        String date = "";
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT date FROM notes WHERE content = ?",
                new String[]{content});
        if (cursor.moveToNext()) {
            date = cursor.getString(0);
        }

        sqLiteDatabase.execSQL("DELETE FROM notes WHERE content = ? AND date = ?",
                new String[]{content, date});
        cursor.close();
    }
}
