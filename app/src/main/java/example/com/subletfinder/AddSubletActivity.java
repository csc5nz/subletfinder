package example.com.subletfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by danielbrown on 4/14/18.
 */

public class AddSubletActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("YOOOOO", "HER");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sublet);
        intent =  new Intent(this, MainActivity.class);

        //Set title and back button for action bar
        getSupportActionBar().setTitle("Add Sublet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void saveNewItem(android.view.View view) {

    }
}
