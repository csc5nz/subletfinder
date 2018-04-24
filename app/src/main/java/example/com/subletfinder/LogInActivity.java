package example.com.subletfinder;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

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

        //firebase initialize firebaseauth
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            // Open main activity for this account
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);     // Open main activity
            startActivity(intent);
        }
        else {
            Toast.makeText(LogInActivity.this, "You are not logged in",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(View view) {
        // Check if valid email/password combo
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            // Open main activity for this account
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);     // Open main activity
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

        // Save email/password into share preference
        // Restore preferences
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        // Get preferences
//        String email = settings.getString("email", "none");
//        String password = settings.getString("password", "none");
//        String logedin = settings.getString("loggedin", "none");
//
//
//        if(email.equals(emailEditText.getText().toString()) && password.equals(passwordEditText.getText().toString())) {
//            SharedPreferences.Editor editor = settings.edit();
//            editor.putString("loggedin", "True");
//            editor.commit();
//            // Open main activity for this account
//            Intent intent = new Intent(this, MainActivity.class);     // Open main activity
//            startActivity(intent);
//        }
//        else {
//            Toast.makeText(LogInActivity.this, "Authentication failed.",
//                    Toast.LENGTH_SHORT).show();
//        }
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
