package com.example.microbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.ViewHolder>{
    private List<Buyer> mData;
    private LayoutInflater mInflater;
    private EmployeAdapter.ItemClickListener mClickListener;

    BuyerAdapter(Context context, List<Buyer> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public BuyerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyvlerview_buyer, parent, false);
        return new BuyerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buyer name = mData.get(position);
        Buyer password = mData.get(position);
        Buyer pib = mData.get(position);
        mData.get(holder.getAdapterPosition());
        holder.myTextViewName.setText(name.getName());
        holder.myTextViewPib.setText(password.getPassword());
        holder.myTextViewPass.setText(pib.getPib());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView myTextViewName;
        private final TextView myTextViewPib;
        private final TextView myTextViewPass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextViewName = itemView.findViewById(R.id.rv_tvnameB);
            myTextViewPass = itemView.findViewById(R.id.rv_tvpass);
            myTextViewPib = itemView.findViewById(R.id.rv_tvpibB);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, this.getAdapterPosition());
        }
    }

    Buyer getItem(int id) {
        return mData.get(id);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // allows clicks events to be caught
    void setClickListener(EmployeAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
