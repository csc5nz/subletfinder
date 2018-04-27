package example.com.subletfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ViewSubletActivity extends AppCompatActivity {
    private Intent intent;
    String email;
    String name;
    String location;
    String description;

    // Shared prefs
    public static final String PREFS_NAME = "SubletPref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sublet);

        intent =  new Intent(this, MainActivity.class);
        get_item();

        //Set title and back button for action bar
        getSupportActionBar().setTitle("View Sublet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void get_item(){
        Log.d("hello", "you got here!");
        //Get string values from intent extras and save them into variables
        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("desc");
        location = getIntent().getStringExtra("loc");
        email = getIntent().getStringExtra("email");
        String filename = getIntent().getStringExtra("filename");


        //Update EditText fields with string variables
        TextView textView= (TextView) findViewById(R.id.field_title);
        textView.setText(name);
        textView = (TextView)findViewById(R.id.field_description);
        textView.setText(description);
        textView = (TextView)findViewById(R.id.field_location);
        textView.setText(location);

        // Display image only if it was uploaded
        if (!filename.equals("")){
        // Firebase storage - download image to imageview
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference fileRef = storageReference.child(filename);

        // ImageView in Activity
        ImageView imageView = (ImageView)findViewById(R.id.imageView3);

        // Load the image using Glide
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(fileRef)
                .into(imageView);
        }
    }

    protected void sendEmail(View view) {
        Log.i("Send email", "");
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        // Get preferences
        String pref_email = settings.getString("email", "");
        String checkbox = settings.getString("checkbox", "false");

        // Update layout
        if (checkbox.equals("true"))
            email = pref_email;

        String[] TO = {email};
        //String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Re: " + name);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "I'm interested in your sublet \n" + name + "\n" + location + "\n" + description);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ViewSubletActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}


