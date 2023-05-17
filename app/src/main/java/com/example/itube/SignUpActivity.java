package com.example.itube;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText fullNameEditText, newUsernameEditText, newPasswordEditText, confirmPasswordEditText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        newUsernameEditText = findViewById(R.id.newUsernameEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString();
                String username = newUsernameEditText.getText().toString();
                String password = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Perform sign-up logic here
                // Check if passwords match
                // If yes, create a new user account
                // Otherwise, display an error message
                if (password.equals(confirmPassword)) {
                    createNewAccount(fullName, username, password);
                } else {
                    displayErrorMessage("Passwords do not match");
                }
            }
        });
    }

    private void createNewAccount(String fullName, String username, String password) {
        // Add your logic to create a new user account
        // For example, you can store the account details in the database

        // Create an instance of the DatabaseHelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        // Add the user to the database
        databaseHelper.addUser(username, password);

        // Display a success message
        displaySuccessMessage("Account created successfully");

        // Navigate back to the sign-in page
        navigateToSignInPage();
    }


    private void navigateToSignInPage() {
        // Create an intent to navigate back to the sign-in page
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent going back to the sign-up page
    }


    private void displaySuccessMessage(String message) {
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
        // Optionally, you can navigate back to the sign-in page here
    }

    private void displayErrorMessage(String message) {
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
