package example.com.subletfinder;

/**
 * Created by danielbrown on 4/26/18.
 */

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

        @GET("{categories}")
        Call<List<Building>> buildingList(@Path("categories") String dept);




    }
