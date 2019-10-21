package com.example.homework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final AsyncListDiffer<ShortContact> asyncListDiffer =
            new AsyncListDiffer<>(this, DIFF_CALLBACK_SHORTCONTACT);

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
                                asyncListDiffer.getCurrentList().get(vHolder.getAdapterPosition()).getId()))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShortContact shortContact = asyncListDiffer.getCurrentList().get(position);
        holder.onBind(shortContact);
    }


    @Override
    public int getItemCount() {
        return asyncListDiffer.getCurrentList().size();
    }

    public void setData(List<ShortContact> data) {
        asyncListDiffer.submitList(data);
    }

    public static final DiffUtil.ItemCallback<ShortContact> DIFF_CALLBACK_SHORTCONTACT
            = new DiffUtil.ItemCallback<ShortContact>() {
        @Override
        public boolean areItemsTheSame(
                @NonNull ShortContact oldShortContact, @NonNull ShortContact newShortContact) {
            return oldShortContact.getId().equals(newShortContact.getId());
        }

        @Override
        public boolean areContentsTheSame(
                @NonNull ShortContact oldShortContact, @NonNull ShortContact newShortContact) {
            return oldShortContact.getName().equals(newShortContact.getName());
        }
    };
}
