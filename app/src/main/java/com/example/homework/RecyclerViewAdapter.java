package com.example.homework;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Contact> mData;

    public RecyclerViewAdapter() {
        Log.i(TAG, "getContacts: adapter constructor");
    }

    RecyclerViewAdapter(List<Contact> mData) {
        this.mData = mData;
        Log.i(TAG, "getContacts: RecyclerViewAdapter: ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "getContacts: onCreateViewHolder ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        final ViewHolder vHolder = new ViewHolder(view);
        vHolder.getItem_contact().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = vHolder.getAdapterPosition();
                ((MainActivity) parent.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, FragmentContactInfo.newInstance(
                                mData.get(position).getName(),
                                mData.get(position).getPhone(),
                                mData.get(position).getEmail(),
                                mData.get(position).getmBitmap()))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "getContacts: onBindViewHolder ");
        holder.onBind(mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Contact> data) {
        mData = data;
        notifyDataSetChanged();
        for (int i = 0; i < mData.size(); i++) {
            Log.i(TAG, "getContacts: adapter setData: name" + mData.get(i).getName());
        }
    }
}
