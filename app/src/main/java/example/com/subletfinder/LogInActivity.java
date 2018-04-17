package example.com.subletfinder;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;


public class LogInActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "LogInPrefsFile";

    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);


        // Check if already logged in
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        // Get preferences
        String email = settings.getString("email", "none");
        String password = settings.getString("password", "none");
        String logedin = settings.getString("loggedin", "none");
        // If loged in skip to mainactivity
//        if (logedin.equals("True")) {
//           // Open main activity for this account
//            Intent intent = new Intent(this, MainActivity.class);     // Open main activity
//            startActivity(intent);
//        }

        //Set title and back button for action bar
        //getSupportActionBar().setTitle("Sign Up");
    }

    public void signIn(View view) {
        // Check if valid email/password combo

        // Save email/password into share preference
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        // Get preferences
        String email = settings.getString("email", "none");
        String password = settings.getString("password", "none");
        String logedin = settings.getString("loggedin", "none");


        if(email.equals(emailEditText.getText().toString()) && password.equals(passwordEditText.getText().toString())) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("loggedin", "True");
            editor.commit();
            // Open main activity for this account
            Intent intent = new Intent(this, MainActivity.class);     // Open main activity
            startActivity(intent);
        }
        else {
            Toast.makeText(LogInActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Save info into share preferences
    public void saveToSharePreferences(View view) {
        // Create editor object to make preference changes
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        // Set preferences
        editor.putString("email", emailEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        editor.putString("loggedin", "True");

        // Commit the edits
        editor.commit();

    }

    // Load shared preferences
    public void loadFromSharedPreferences(View view) {
        // Restore preferences
        SharedPreferences settngs = getSharedPreferences(PREFS_NAME, 0);
        // Get preferences
        String email = settngs.getString("email", "none");
        String password = settngs.getString("password", "none");
        String logedin = settngs.getString("loggedin", "none");
    }

    public void signUp(View view) {
        // Open signUpActivity
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
