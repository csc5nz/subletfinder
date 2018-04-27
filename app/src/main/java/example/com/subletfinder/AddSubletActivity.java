package example.com.subletfinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * Created by danielbrown on 4/14/18.
 */

public class AddSubletActivity extends AppCompatActivity {
    private Intent intent;

    // Firebase authorization
    private FirebaseAuth mAuth;
    String uid;

    // firebase storage
    String filename;

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

        // firebase storage
        filename = "";
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
        String email = mAuth.getCurrentUser().getEmail();
        intent.putExtra("title", title);
        intent.putExtra("desc", desc);
        intent.putExtra("loc", location);
        intent.putExtra("email", email);
        intent.putExtra("filename", filename);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void addImage(android.view.View view) {
        Intent intent = new Intent(this, AddImageActivity.class);     // Only sends the intent and switched activity
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {       //everything went to plan
            if (resultCode == Activity.RESULT_OK) {
//                byte[] byteArray = data.getByteArrayExtra("image");
//                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                ImageView result = (ImageView)findViewById(R.id.imageView3);
//                result.setImageBitmap(bmp);

//                // Firebase storage - download image to imageview
                filename = data.getStringExtra("filename");
//                // Reference to an image file in Firebase Storage
//                // Create a storage reference from our app
//                // Firebase storage
//                FirebaseStorage  storage = FirebaseStorage.getInstance();
//                StorageReference storageReference = storage.getReference();
//                StorageReference fileRef = storageReference.child(filename);
//
//                // ImageView in your Activity
//                ImageView imageView = (ImageView)findViewById(R.id.imageView3);
//
//                // Load the image using Glide
//                Glide.with(this /* context */)
//                        .using(new FirebaseImageLoader())
//                        .load(fileRef)
//                        .into(imageView);
            }

        }
    }
}
