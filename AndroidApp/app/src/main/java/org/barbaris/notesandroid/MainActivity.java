package org.barbaris.notesandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        EditText loginBox = findViewById(R.id.loginBox);
        EditText passBox = findViewById(R.id.passwordBox);
        String login = loginBox.getText().toString();
        String pass = passBox.getText().toString();



        LoginRequest request = new LoginRequest(login, pass, MainActivity.this);
        request.start();


    }
}







