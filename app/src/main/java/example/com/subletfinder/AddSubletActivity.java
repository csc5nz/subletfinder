package example.com.subletfinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;


/**
 * Created by danielbrown on 4/14/18.
 */

public class AddSubletActivity extends AppCompatActivity {
    private Intent intent;

    //Pick picture
    static final int PICK_IMAGE_REQUEST = 3;

    // Firebase authorization
    private FirebaseAuth mAuth;
    String uid;

    // firebase storage
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri uri_for_upload;
    Uri downloadUrl;
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

        // Firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        filename = "";

        // Virtual keyboard "Done" feature
        final Button saveButton = (Button) findViewById((R.id.button_save));
        EditText editTxt = (EditText) findViewById(R.id.field_description);
        editTxt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    saveButton.performClick();
                    return true;
                }
                return false;
            }
        });
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
        uploadImage();
        intent.putExtra("filename", filename);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

//    public void addImage(android.view.View view) {
//        Intent intent = new Intent(this, AddImageActivity.class);     // Only sends the intent and switched activity
//        startActivityForResult(intent, 1);
//    }

    // Start the process of getting a picture from the library
    public void addImage(View view) {
        // Send intent to request image chooser
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }


    // Handles results from both taking a picture or pulling from image gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle picture from gallery
        if (requestCode == PICK_IMAGE_REQUEST) {
            // Display it on layout
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                uri_for_upload = uri;

//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    Log.d("YO", String.valueOf(bitmap));
//                    bmp = bitmap;
//                    imageView.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }


    private void uploadImage(){
        Uri file = uri_for_upload;
        filename = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child(filename);

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("imageupload", "uploadsuccess!");
                        // Get a URL to the uploaded content
                        downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("imageupload", "uploadfailed!");
                        // Handle unsuccessful uploads
                        // ...
                        filename="";
                    }
                });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {       //everything went to plan
//            if (resultCode == Activity.RESULT_OK) {
////                byte[] byteArray = data.getByteArrayExtra("image");
////                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
////                ImageView result = (ImageView)findViewById(R.id.imageView3);
////                result.setImageBitmap(bmp);
//
////              // Image filename for Firebase storage
//                filename = data.getStringExtra("filename");
////
//            }
//
//        }
//    }

}
