package example.com.subletfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by danielbrown on 4/14/18.
 */

public class AddSubletActivity extends AppCompatActivity {
    private Intent intent;

    // Firebase authorization
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sublet);
        intent =  new Intent(this, MainActivity.class);

        //Set title and back button for action bar
        getSupportActionBar().setTitle("Add Sublet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Firebase Authorization
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and and get user id.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            // Get user id
            String uid = currentUser.getUid();
        }
        else {
            // User not logged in send to LogInActivity
            Toast.makeText(AddSubletActivity.this, "You are not logged in",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddSubletActivity.this, LogInActivity.class);     // Open main activity
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){                    //When back button pressed send back intent with CANCELED message
        setResult(Activity.RESULT_CANCELED, intent);
        //finish();
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp(){           //When back button pressed send back intent with CANCELED message
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
        return true;
    }
    public void saveNewItem(android.view.View view) {
        EditText editTxt = (EditText) findViewById(R.id.field_title);
        String title = editTxt.getText().toString();
        editTxt = (EditText) findViewById(R.id.field_description);
        String desc = editTxt.getText().toString();
        editTxt = (EditText) findViewById(R.id.field_location);
        String location = editTxt.getText().toString();
        intent.putExtra("title", title);
        intent.putExtra("desc", desc);
        intent.putExtra("loc", location);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void addImage(android.view.View view) {
        Intent intent = new Intent(this, AddImageActivity.class);     // Only sends the intent and switched activity
        startActivityForResult(intent, 1);
    }
}
