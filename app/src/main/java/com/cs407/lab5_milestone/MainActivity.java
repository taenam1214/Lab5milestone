package com.cs407.lab5_milestone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        if (!username.isEmpty()) {
            Intent intent = new Intent(this, EditorActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            buttonLogin = findViewById(R.id.buttonLogin);
            editTextUsername = findViewById(R.id.userName);

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enteredUsername = editTextUsername.getText().toString();
                    if (!enteredUsername.isEmpty()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", enteredUsername);
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }
}
