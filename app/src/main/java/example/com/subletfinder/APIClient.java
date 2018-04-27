package example.com.subletfinder;

/**
 * Created by danielbrown on 4/26/18.
 */
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {


        public static final String BASE_URL = "https://devhub.virginia.edu/api/5adfcbb667dd561ad0ac5d18/categories/";
        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }



}
