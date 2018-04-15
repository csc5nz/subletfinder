package example.com.subletfinder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import android.util.Log;






public class SubletListAdapter extends RecyclerView.Adapter<SubletListAdapter.ViewHolder> {
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public TextView nameTextView;
            public TextView dateTextView;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                nameTextView = (TextView) itemView.findViewById(R.id.contact_name);     // Sets the name of the task
                dateTextView = (TextView) itemView.findViewById(R.id.address);             // Sets the date of the task

            }
        }

    // Store a member variable for the contacts
    private List<SubletItem> mSublets;
    // Store the context for easy access
    private Context mContext;


    public SubletListAdapter(Context context, List<SubletItem> sublets) {
        mSublets = sublets;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }







    @Override
    public SubletListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {     // Adapter onCreateViewHolder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout inflater works with relative layout
        View contactView = inflater.inflate(R.layout.sublet_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(SubletListAdapter.ViewHolder holder, int position) {

        // Get the data model based on position
        SubletItem item = mSublets.get(position);
        Log.d("HERE", "oi: " + item.getmName());

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(item.getmName());
        TextView dateView = holder.dateTextView;
        dateView.setText(item.getmAddress());



    }

    @Override
    public int getItemCount() {
        return mSublets.size();
    }



    }
