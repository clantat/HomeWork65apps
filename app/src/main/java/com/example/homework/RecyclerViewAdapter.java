package com.example.homework;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

    private List<ShortContact> data;
    private List<ShortContact> dataFull;

    public RecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        final ViewHolder vHolder = new ViewHolder(view);
        vHolder.getItem_contact().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) parent.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, FragmentContactInfo.newInstance(
                                data.get(vHolder.getAdapterPosition()).getId()))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ShortContact> data) {
        this.data = data;
        dataFull = new ArrayList<>(data);
        updateShortContactListItems(data);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<ShortContact> filteredList = new ArrayList<>();
                if (TextUtils.isEmpty(charSequence)) {
                    filteredList = dataFull;
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (ShortContact contact : dataFull) {
                        if (contact.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(contact);
                        }
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                updateShortContactListItems((List<ShortContact>) filterResults.values);
            }
        };
    }

    private void updateShortContactListItems(List<ShortContact> shortContacts) {
        final ShortContactDiffCallback diffCallback = new ShortContactDiffCallback(this.data, shortContacts);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.data = shortContacts;
        diffResult.dispatchUpdatesTo(this);
    }
}
