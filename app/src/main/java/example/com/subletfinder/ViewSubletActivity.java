package example.com.subletfinder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("desc");
        String location = getIntent().getStringExtra("loc");
        String email = getIntent().getStringExtra("email");
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
}


