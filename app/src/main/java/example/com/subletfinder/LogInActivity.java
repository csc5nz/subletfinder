package example.com.subletfinder;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    // Firebase Authentication
    private FirebaseAuth mAuth;

    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);


        // Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Virtual keyboard "Done" feature
        final Button signinButton = (Button) findViewById((R.id.signinButton));
        passwordEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signinButton.performClick();
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            // Sign in success, open main activity for this account
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);     // Open main activity
            startActivity(intent);
        }
        else {
//            Toast.makeText(LogInActivity.this, "Log in please",
//                    Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(View view) {
        // Check if email/password not empty
        String email = emailEditText.getText().toString();
        if(email == null || email.trim().equals("")){
            Toast.makeText(this, "No email entered", Toast.LENGTH_SHORT).show();
            return;
        }
        String password = passwordEditText.getText().toString();
        if(password == null || password.trim().equals("")){
            Toast.makeText(this, "No password entered", Toast.LENGTH_SHORT).show();
            return;
        }

       // Progress dialog - please wait
        final ProgressDialog progressDialog;
        progressDialog = ProgressDialog.show(this, "","Please Wait...", true);


        // Firebase sign in
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            // Deactivate progress dialog
                            progressDialog.dismiss();

                            // Open main activity
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);     // Open main activity
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());

                            // Deactivate progress dialog
                            progressDialog.dismiss();

                            // Let user know that authentication failed
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);

                        }

                    }
                });
    }


    public void signUp(View view) {
        // Open signUpActivity
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
