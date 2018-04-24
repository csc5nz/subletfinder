package example.com.subletfinder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    RecyclerView rvSublet;
    ArrayList<SubletItem> items;
    EditText nameField;

    // Firebase authorization
    private FirebaseAuth mAuth;
    String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvSublet = (RecyclerView) findViewById(R.id.rvToDo);
        if (savedInstanceState == null) {
            items = SubletItem.createInitialBucketList(25);         // Start with 20 so that it shows recycler view in action
        }

        SubletListAdapter adapter = new SubletListAdapter(this, items);
        // Attach the adapter to the recyclerview to populate items
        rvSublet.setAdapter(adapter);
        // Set layout manager to position the items
        rvSublet.setLayoutManager(new LinearLayoutManager(this));
        
        rvSublet.addItemDecoration(new SimpleDividerItemDecoration(this));

        //Set title for action bar
        getSupportActionBar().setTitle("Sublet Finder");

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
            uid = currentUser.getUid();
        }
        else {
            // User not logged in send to LogInActivity
            Toast.makeText(MainActivity.this, "You are not logged in",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);     // Open main activity
            startActivity(intent);
        }
    }


    public void startAddNewSublet(View view) {                                   // Will eventually need to be a startActivityForResponse
        Intent intent = new Intent(this, AddSubletActivity.class);     // Only sends the intent and switched activity
        startActivityForResult(intent, 1);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current state
        Log.d("RotationExample", "Rotating!");
        savedInstanceState.putParcelableArrayList("items", items);

        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        Log.d("RotationExample", "Rebuilding the View!");
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        items = savedInstanceState.getParcelableArrayList("items");
        SubletListAdapter adapter = new SubletListAdapter(this, items);
        // Attach the adapter to the recyclerview to populate items
        rvSublet.setAdapter(adapter);
        // Set layout manager to position the items
        rvSublet.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {       //everything went to plan
            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra("title");
                String desc = data.getStringExtra("desc");
                String loc = data.getStringExtra("loc");
                SubletItem s = new SubletItem(title, loc, desc);
                items.add(s);
                rvSublet.getAdapter().notifyDataSetChanged();

            }

        }
    }
}
