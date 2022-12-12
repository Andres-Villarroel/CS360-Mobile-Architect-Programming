package com.zybooks.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {
    EditText uName;
    EditText uPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createAccountButtonClick(View view){
        //adds username and password to database
        EditText uName = (EditText) findViewById(R.id.editTextUsername);
        EditText uPass = (EditText) findViewById(R.id.newPasswordEditText);
        String createAccountUserName = uName.getText().toString();
        String createAccountUserPassword = uPass.getText().toString();

        DatabaseHandler db = new DatabaseHandler(CreateAccountActivity.this);

        db.addNewUserCredentials(createAccountUserName, createAccountUserPassword);

        //switching screens to the main inventory screen
        Intent intent = new Intent(CreateAccountActivity.this, SecondDisplayActivityScreen.class);
        startActivity(intent);

    }
}