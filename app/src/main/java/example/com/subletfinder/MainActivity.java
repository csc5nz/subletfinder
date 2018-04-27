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
import java.util.HashMap;
import java.util.Map;

import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;



public class MainActivity extends AppCompatActivity {
    RecyclerView rvSublet;
    ArrayList<SubletItem> items;
    EditText nameField;

    // Firebase authorization
    private FirebaseAuth mAuth;
    String uid;

    // Firebase database
    DatabaseReference databaseReference;



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

        // Firebase Authorization
        mAuth = FirebaseAuth.getInstance();

        // Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Firebase database - listen for change
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllSublets(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllSublets(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //taskDeletion(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // Firebase authorization
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
                String email = data.getStringExtra("email");
                String filename = data.getStringExtra("filename");
                SubletItem s = new SubletItem(title, loc, desc, email, filename);
                //items.add(s);
                //rvSublet.getAdapter().notifyDataSetChanged();

                // Firebase database
                // Create new sublet node and get id
                String subletId = databaseReference.child("sublets").push().getKey();
                // Push s to database inside the "sublets" group
                databaseReference.child("sublets").child(subletId).setValue(s);

            }

        }
    }

    private void getAllSublets(DataSnapshot dataSnapshot){
        items.clear();
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
            SubletItem s = singleSnapshot.getValue(SubletItem.class);


            items.add(s);
        }
        rvSublet.getAdapter().notifyDataSetChanged();
    }
}
