package example.com.subletfinder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by danielbrown on 4/14/18.
 */

public class SubletItem implements Parcelable {
    private String mName;
    //GPS?
    private String mAddress;
    private String mDescription;

    public String getmName() {
        return mName;
    }
    public String getmAddress() {
        return mAddress;
    }
    public String getmDescription() { return mDescription; }

    private static int lastBucketId = 0;

    public static final Parcelable.Creator<SubletItem> CREATOR
            = new Parcelable.Creator<SubletItem>() {
        public SubletItem createFromParcel(Parcel in) {
            return new SubletItem(in);
        }

        public SubletItem[] newArray(int size) {
            return new SubletItem[size];
        }
    };

    private SubletItem(Parcel in) {
        mName = in.readString();
        mAddress = in.readString();
        mDescription = in.readString();
    }

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mName);
        out.writeString(mAddress);
        out.writeString(mDescription);
    }


    public SubletItem(String name, String address, String desc) {
        mName = name;
        mAddress = address;
        mDescription = desc;
    }

    public static ArrayList<SubletItem> createInitialBucketList(int numBuckets) {       // Pre-populates the list
        ArrayList<SubletItem> items = new ArrayList<SubletItem>();

//        for (int i = 1; i <= numBuckets; i++) {
//            items.add(new SubletItem("Name " + ++lastBucketId, "address " + lastBucketId, ""));
//        }


        return items;
    }

    // For firebase database
    public SubletItem() {
    }
//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("mName", mName);
//        result.put("mAddress", mAddress);
//        result.put("MDescription", mDescription);
//
//        return result;
//    }
}
