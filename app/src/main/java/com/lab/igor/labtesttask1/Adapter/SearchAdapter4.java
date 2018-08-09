package com.lab.igor.labtesttask1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lab.igor.labtesttask1.DescriptionActivity;
import com.lab.igor.labtesttask1.Model.DrugInteractionsN;
import com.lab.igor.labtesttask1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 20-May-18.
 */
class SearchViewHolder4 extends RecyclerView.ViewHolder {

    public TextView interactions, description;
    public ImageView imgButton;
    private static final String TAG = "SearchViewHolder4";
    public LinearLayout linearLayout;

    public SearchViewHolder4(View itemView) {
        super(itemView);
        // connect our fields in design with Model's fields
        interactions = (TextView) itemView.findViewById(R.id.interactions);
        description = (TextView) itemView.findViewById(R.id.description_of_interactions);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);
        imgButton = itemView.findViewById(R.id.arrow_down);


    }


}

public class SearchAdapter4 extends RecyclerView.Adapter<SearchViewHolder4> {

    private Context context;
    TextView desc;


    private static final String TAG = "SearchAdapter4";
    private List<DrugInteractionsN> drugInteractionsNS = new ArrayList<>();


    public SearchAdapter4(ArrayList<DrugInteractionsN> drugInteractionsNS) {
        this.drugInteractionsNS = drugInteractionsNS;
    }

    public SearchAdapter4(Context context, List<DrugInteractionsN> drugInteractionsNS) {
        this.context = context;
        this.drugInteractionsNS = drugInteractionsNS;
    }


    // list Of searched items
    @Override
    public SearchViewHolder4 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item7, parent, false);

        return new SearchViewHolder4(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder4 holder, final int position) {
        holder.interactions.setText(drugInteractionsNS.get(position).getDescription());
        holder.description.setText("");
        holder.imgButton.setVisibility(View.VISIBLE);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on:" + drugInteractionsNS.get(position).getNameOfDrugInteraction());

                holder.description.setText("Description: " + drugInteractionsNS.get(position).getNameOfDrugInteraction());
                holder.imgButton.setVisibility(View.INVISIBLE);
                // Intent intent = new Intent(context, DescriptionActivity.class);
                //intent.putExtra("description", drugInteractionsNS.get(position).getNameOfDrugInteraction());
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugInteractionsNS.size();
    }
}