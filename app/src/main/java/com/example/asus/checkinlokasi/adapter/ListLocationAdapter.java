package com.example.asus.checkinlokasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.checkinlokasi.R;
import com.example.asus.checkinlokasi.data.network.model.Location;

import java.util.List;

public class ListLocationAdapter extends RecyclerView.Adapter<ListLocationAdapter.ListLocationViewHolder> {
    private Context context;
    private List<Location> locationList;
    private OnDetailListener onDetailListener;

    public ListLocationAdapter(Context context) {
        this.context = context;
    }

    public ListLocationAdapter() {

    }
    public void setData(List<Location> locationList){
        this.locationList = locationList;
        notifyDataSetChanged();
    }

    @Override
    public ListLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new ListLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListLocationViewHolder holder, int position) {
        holder.tvLocationName.setText(locationList.get(position).getNama());
        holder.tvLocationNote.setText(locationList.get(position).getKeterangan());
        holder.tvLocationContributor.setText(locationList.get(position).getKontributor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetailListener.onItemDetailClicked(Integer.parseInt(locationList.get(position).getId()));
            }
        });
    }


    public void setOnClickDetailListener(OnDetailListener onDetailListener){
        this.onDetailListener = onDetailListener;
    }


    @Override
    public boolean onFailedToRecycleView(ListLocationViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        if(locationList == null) return 0;
        return locationList.size();
    }

    public class ListLocationViewHolder extends RecyclerView.ViewHolder{
        TextView tvLocationName, tvLocationNote, tvLocationContributor;

        public ListLocationViewHolder(final View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tv_location_name);
            tvLocationNote = itemView.findViewById(R.id.tv_location_note);
            tvLocationContributor = itemView.findViewById(R.id.tv_location_contributor);

        }
    }

    public interface OnDetailListener{
        void onItemDetailClicked(int tipsId);

    }
}
