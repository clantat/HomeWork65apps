package com.example.homework;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ShortContactDiffCallback extends DiffUtil.Callback {
    private final List<ShortContact> oldShortContacts;
    private final List<ShortContact> newShortContacts;

    public ShortContactDiffCallback(List<ShortContact> oldShortContacts, List<ShortContact> newShortContacts) {
        this.oldShortContacts = oldShortContacts;
        this.newShortContacts = newShortContacts;
    }


    @Override
    public int getOldListSize() {
        return oldShortContacts.size();
    }

    @Override
    public int getNewListSize() {
        return newShortContacts.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldShortContacts.get(oldItemPosition).getId().equals(newShortContacts.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ShortContact oldContact = oldShortContacts.get(oldItemPosition);
        final ShortContact newContact = newShortContacts.get(newItemPosition);
        return oldContact.getName().equals(newContact.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
