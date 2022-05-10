package com.example.microbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeAdapter extends RecyclerView.Adapter<EmployeAdapter.ViewHolder> {
    private List<Employe> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    EmployeAdapter(Context context, List<Employe> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recylcerview_employe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeAdapter.ViewHolder holder, int position) {

        Employe name = mData.get(position);
        Employe surename = mData.get(position);
        Employe city = mData.get(position);
        Employe magacin1 = mData.get(position);
        Employe magacin2 = mData.get(position);
        Employe magacin3 = mData.get(position);
        mData.get(holder.getAdapterPosition());
        holder.myTextViewName.setText(name.getName());
        holder.myTextViewSurename.setText(surename.getSurename());
        holder.myTextViewCity.setText(city.getCity());
        holder.myTextViewMagacin1.setText(magacin1.getMagacin1());
        holder.myTextViewMagacin2.setText(magacin2.getMagacin2());
        holder.myTextViewMagacin3.setText(magacin3.getMagacin3());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView myTextViewName;
        private final TextView myTextViewSurename;
        private final TextView myTextViewCity;
        private final TextView myTextViewMagacin1;
        private final TextView myTextViewMagacin2;
        private final TextView myTextViewMagacin3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextViewName = itemView.findViewById(R.id.rv_tvname);
            myTextViewSurename = itemView.findViewById(R.id.rv_tvsurename);
            myTextViewCity = itemView.findViewById(R.id.rv_tvcity);
            myTextViewMagacin1 = itemView.findViewById(R.id.rv_tvmagacin1);
            myTextViewMagacin2 = itemView.findViewById(R.id.rv_tvmagacin2);
            myTextViewMagacin3 = itemView.findViewById(R.id.rv_tvmagacin3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, this.getAdapterPosition());
        }
    }

    Employe getItem(int id) {
        return mData.get(id);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
