package example.com.subletfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildingsActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://devhub.virginia.edu/api/5adfcbb667dd561ad0ac5d18/categories/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildings_activity);

        //Set title and back button for action bar
        getSupportActionBar().setTitle("On Grounds Housing Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        callAPI();
    }

    public void callAPI() {

        APIInterface apiService =
                APIClient.getClient().create(APIInterface.class);

      //  EditText mnemonic = (EditText)findViewById(R.id.editText);
    //    String mnemonicSearch = mnemonic.getText().toString();

        Call<List<Building>> call = apiService.buildingList("housing");
        call.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                Log.d("HELLO!", ""+response.body().toString());
                List<Building> sections = response.body();

                String courseDisplay = "";
                for(Building s : sections) {

                    Log.d("LousList", "Received: " + s);
                    courseDisplay += s + "\n";
                }
                TextView display = (TextView)findViewById(R.id.textView);
                display.setText(courseDisplay);
            }

            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {
                // Log error here since request failed
                Log.e("LousList", t.toString());
            }
        });

    }
}