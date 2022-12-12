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

public class MainActivity extends AppCompatActivity {

    EditText userEmail;
    EditText userPass;
    Button submitCredentialsBtn;
    Button newAccountBtn;
    TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText userPass = (EditText) findViewById(R.id.editTextTextPassword);
        Button submitCredentialsBtn = (Button) findViewById(R.id.submitCredentialsButton);
        Button newAccountBtn = (Button) findViewById(R.id.newAccountButton);


    }

    /*
    callback function for the 'Login' button
    will read the text in the username and password EditText fields and verify if they
    are valid.
    */
    public void onLoginButtonClick(View view){
        //check login credentials
        //switch screen if successful
            //display error message if false
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        //testing block, remove this section when done
        //db.addNewUserCredentials("testUser", "testPass");
        //================== end of to-remove section===========================================

        EditText userEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText userPass = (EditText) findViewById(R.id.editTextTextPassword);
        TextView loginTextView = (TextView) findViewById(R.id.loginTextView);

        String usernameField = userEmail.getText().toString();
        String userPasswordField = userPass.getText().toString();

        if(usernameField != null && userPasswordField != null){
            if(db.checkCredential(usernameField, userPasswordField)){
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SecondDisplayActivityScreen.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this, "Unsuccessful Login Attempt", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void onNewAccountButtonClick(View view){
        Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }
}