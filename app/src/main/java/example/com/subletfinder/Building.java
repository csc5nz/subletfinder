package example.com.subletfinder;

/**
 * Created by danielbrown on 4/26/18.
 */

public class Building {
    public static final String BASE_URL = "https://devhub.virginia.edu/api/5adfcbb667dd561ad0ac5d18/categories/";

    private String Name;
    private double Latitude;
    private double Longitude;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

    public String toString() {
        return Name + " " + Longitude + " : " + Latitude;
    }
}
