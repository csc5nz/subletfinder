package example.com.subletfinder;

import java.util.ArrayList;

/**
 * Created by danielbrown on 4/14/18.
 */

public class SubletItem {
    private String mName;
    //GPS?
    private String mAddress;

    public String getmName() {
        return mName;
    }
    public String getmAddress() {
        return mAddress;
    }

    private static int lastBucketId = 0;


    public SubletItem(String name, String address) {
        mName = name;
        mAddress = address;

    }

    public static ArrayList<SubletItem> createInitialBucketList(int numBuckets) {       // Pre-populates the list
        ArrayList<SubletItem> items = new ArrayList<SubletItem>();

        for (int i = 1; i <= numBuckets; i++) {
            items.add(new SubletItem("Name " + ++lastBucketId, "address " + lastBucketId));
        }
        return items;
    }
}
