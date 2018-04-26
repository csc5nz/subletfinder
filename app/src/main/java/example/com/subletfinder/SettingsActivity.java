package example.com.subletfinder;
import example.com.subletfinder.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    // Firebase auth
    private FirebaseAuth mAuth;

    // Shared prefs
    public static final String PREFS_NAME = "CoreSkillsPrefsFile";

    CheckBox emailCheckBox;
    EditText emailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Firebase authorization
        mAuth = FirebaseAuth.getInstance();

        emailCheckBox = (CheckBox) findViewById(R.id.emailCheckBox);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        emailCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                saveToSharedPreferences();
            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                saveToSharedPreferences();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Shared prefs
        loadFromSharedPreferences();
    }


        // Save share preferences
    public void saveToSharedPreferences() {
        // Create editor object to make preference changes.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        // Set preferences
        editor.putString("email", emailEditText.getText().toString());
        if (emailCheckBox.isChecked())
            editor.putString("checkbox", "true");
        else
            editor.putString("checkbox", "false");

        // Commit the edits!
        editor.commit();
    }

    // Load shared preferences
    public void loadFromSharedPreferences() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        // Get preferences
        String email = settings.getString("email", "");
        String checkbox = settings.getString("checkbox", "false");

        // Update layout
        emailEditText.setText(email);
        if (checkbox.equals("true"))
            emailCheckBox.setChecked(true);
        else
            emailCheckBox.setChecked(false);

    }

    public void signOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(SettingsActivity.this, LogInActivity.class);     // Open main activity
        startActivity(intent);

    }

}
