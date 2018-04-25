package example.com.subletfinder;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.OutputStream;
import java.util.List;


/**
 Assignment Notes: This activity should pull data from a REST JSON API based upon
 the course requested in the EditText.  The courses are those from a previous semester.
 We used the Retrofit library to do this, since making network calls in Android
 otherwise require actions on a separate thread.  If you wish to use this library,
 some helper code is provided in the LousListAPI classes.
 Retrofit References:
 http://square.github.io/retrofit/
 https://github.com/marksherriff/LousListRESTAPI
 Native JSON Parsing:
 http://www.ssaurel.com/blog/learn-to-consume-a-rest-web-service-and-parse-json-result-in-android/
 */

public class BuildingsActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://devhub.virginia.edu/api/5adfcbb667dd561ad0ac5d18/categories/";


    TextView nameTV;
    TextView latTV;
    TextView lonTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_web_service);
/*
        HttpURLConnection httpcon;
        try {
            httpcon = (HttpURLConnection) ((new URL(BASE_URL + "Housing").openConnection()));
            httpcon.setRequestProperty("Content-Type", "application/json");

            httpcon.setRequestProperty("Accept", "application/json");

            httpcon.setRequestMethod("POST");

            httpcon.connect();
            byte[] outputBytes = "{'Name': 'sysadmin', 'Latitude':'apple', 'Longitude' : 'orange'}".getBytes("UTF-8");

            OutputStream os = httpcon.getOutputStream();
            os.write(outputBytes);

            os.close();
            for (int i = 0; i < 3; i++) {
                Log.d("HEY",outputBytes[i] + "");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        nameTV = (TextView) findViewById(R.id.name);
        latTV = (TextView) findViewById(R.id.lat);
        lonTV = (TextView) findViewById(R.id.lon);
        */
    }



}