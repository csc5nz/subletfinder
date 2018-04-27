package example.com.subletfinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**

 Assignment Notes: Add code here to make the call either the camera or
 the image library to update the imageView on the screen.  Note that there
 are NUMEROUS different ways to do this, some better than others, and many
 don't work well together at all.

 I used the following tutorials:
 https://androidkennel.org/android-camera-access-tutorial/
 http://codetheory.in/android-pick-select-image-from-gallery-with-intents/

 */

public class AddImageActivity extends AppCompatActivity {

    // Firebase authorization
    private FirebaseAuth mAuth;
    String uid;

    private Intent intent;

    static final int TAKE_PHOTO_PERMISSION = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    static final int PICK_IMAGE_REQUEST = 3;

    ImageView imageView;
    Button takePictureFloat;
    Bitmap bmp;

    Uri file;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri uri_for_upload;
    Uri downloadUrl;
    String filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        takePictureFloat = (Button) findViewById(R.id.takePictureButton);
        imageView = (ImageView) findViewById(R.id.imageView);

        intent =  new Intent(this, AddSubletActivity.class);

        // We are giving you the code that checks for permissions
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureFloat.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, TAKE_PHOTO_PERMISSION);
        }

        //Set title and back button for action bar
        getSupportActionBar().setTitle("Add Picture");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Firebase authorization
        mAuth = FirebaseAuth.getInstance();

        // Firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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
            Toast.makeText(AddImageActivity.this, "You are not logged in",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddImageActivity.this, LogInActivity.class);     // Open main activity
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // This is called when permissions are either granted or not for the app
        // You do not need to edit this code.

        if (requestCode == TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureFloat.setEnabled(true);
            }
        }
    }

    // Start the process of taking a picture
    public void takePicture(View view) {
        // Send an intent to the camera to take a picture...
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, REQUEST_TAKE_PHOTO);

    }

    // Start the process of getting a picture from the library
    public void getImageFromLibrary(View view) {
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
        // Handle picture from camera
        if (requestCode == REQUEST_TAKE_PHOTO) {
            // Display it on layout
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(file);
            }

        }
        // Handle picture from gallery
        else if (requestCode == PICK_IMAGE_REQUEST) {
            // Display it on layout
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                uri_for_upload = uri;

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                     Log.d("YO", String.valueOf(bitmap));
                    bmp = bitmap;
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Set file name and path for new image file
    // If directory doesn't exit it creates it
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }


    public void saveImage(android.view.View view) {
        uploadImage();
        //Convert to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("image",byteArray);
        intent.putExtra("filename", filename);
      //  intent.putExtra("location", )
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void signOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(AddImageActivity.this, LogInActivity.class);     // Open main activity
        startActivity(intent);

    }

    public void loadPreferences(View view) {
        Intent intent = new Intent(AddImageActivity.this, SettingsActivity.class);     // Open main activity
        startActivity(intent);

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


}
