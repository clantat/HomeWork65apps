package com.example.homework.presentation.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.R;
import com.example.homework.core.Screens;
import com.example.homework.domain.model.ShortContact;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Router router;

    private final AsyncListDiffer<ShortContact> asyncListDiffer =
            new AsyncListDiffer<>(this, DIFF_CALLBACK_SHORTCONTACT);

    @Inject
    public RecyclerViewAdapter(Router router) {
        this.router = router;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        final ViewHolder vHolder = new ViewHolder(view);
        vHolder.getItem_contact().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                router.navigateTo(new Screens.ContactInfoScreen(2,
                        asyncListDiffer.getCurrentList().get(vHolder.getAdapterPosition()).getId()));
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
