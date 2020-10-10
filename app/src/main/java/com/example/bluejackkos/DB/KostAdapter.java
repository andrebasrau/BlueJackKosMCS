package com.example.bluejackkos.DB;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bluejackkos.Model.Kost;
import com.example.bluejackkos.R;

import java.util.ArrayList;

public class KostAdapter extends RecyclerView.Adapter<KostAdapter.KostViewHolder> {

    private ArrayList<Kost> kosts = new ArrayList<>();

    Context context;  public KostAdapter(Context context, ArrayList<Kost> kosts) {
        //ini untuk daptein context yang dipanggil dari kostlist
        this.context = context;
        this.kosts = kosts;
    }

    public ArrayList<Kost> getKosts() {
        return kosts;
    }

    public void setKosts(ArrayList<Kost> kosts) {
        this.kosts = kosts;
        notifyDataSetChanged();
    }
    //ngeset listener sama interface agar bisa jadi function yagn dipanggil nantinya di viewholder trus di panggil di koslist
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick (int position);
    }
    public void setOnClickItemClickListener (OnItemClickListener listener ){
        mListener = listener;
    }

    @NonNull
    @Override
    public KostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kost_item,parent,false);
        return new KostViewHolder(view, mListener);
        //nambahin parameter mListener untuk maggil ViewHoldernya karena parameter diabwah juga harus ditambah untuk bikin onclick nya
    }

    @Override
    public void onBindViewHolder(@NonNull KostViewHolder holder , int position) {
        Kost kost = kosts.get(position);
        holder.kostName.setText(kost.getKostname());
        holder.kostPrice.setText(String.format("Rp. %s", kost.getKostPrice()));
        holder.kostFacility.setText(kost.getKostFacilities());
        //ini untuk ngeload image
        Glide.with(context).load(kost.getKostImageSrc()).into(holder.kostImg);
        //load image
//       Glide.with(position).load(kost.getKostImageSrc()).into(holder.kostImg);

    }

    @Override
    public int getItemCount() {
        return kosts.size();
    }

    public class KostViewHolder extends RecyclerView.ViewHolder {
        ImageView kostImg;
        TextView kostName, kostPrice, kostFacility;

        public KostViewHolder(@NonNull View kostView, final OnItemClickListener listener) {
            super(kostView);

            kostName= kostView.findViewById(R.id.kost_Name_tv);
            kostPrice =kostView.findViewById(R.id.kost_Price_tv);
            kostFacility=kostView.findViewById(R.id.kost_Facility_tv);
            kostImg = kostView.findViewById(R.id.kost_Image_tv);
            //untuk bikin onclicknya di class koslist jadi nya bisa ngepass ke class kos detail
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
