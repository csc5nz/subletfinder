package example.com.subletfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvSublet;
    ArrayList<SubletItem> items;
    EditText nameField;



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

        //Set title for action bar
        getSupportActionBar().setTitle("Sublet Finder");


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
}
