package com.example.itube;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the user is already signed in
        boolean isUserSignedIn = checkUserSignedIn();

        if (isUserSignedIn) {
            // User is signed in, navigate to the home page
            navigateToHomePage();
        } else {
            // User is not signed in, navigate to the sign-in page
            navigateToSignInPage();
        }
    }

    private boolean checkUserSignedIn() {
        // Implement your logic to check if the user is signed in
        // Return true if the user is signed in, false otherwise
        return false; // Placeholder logic, change as per your requirements
    }

    private void navigateToHomePage() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity if you don't want to go back to it on pressing the back button
    }

    private void navigateToSignInPage() {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity if you don't want to go back to it on pressing the back button
    }
}
