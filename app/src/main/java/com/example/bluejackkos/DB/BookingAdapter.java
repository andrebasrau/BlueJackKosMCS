package com.example.bluejackkos.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bluejackkos.Model.Booking;
import com.example.bluejackkos.Model.Global;
import com.example.bluejackkos.Model.Kost;
import com.example.bluejackkos.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    Context ctx;
    ArrayList <Booking> BookList;
    public BookingAdapter (Context context, ArrayList BookList){
        this.ctx = context;
        this.BookList = BookList;
    }
    private KostAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick (int position);
    }
    public void setOnClickItemClickListener (KostAdapter.OnItemClickListener listener ){
        mListener = listener;
    }
    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
        return new BookingAdapter.ViewHolder(view, mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.ViewHolder holder, int position) {
        Booking book = BookList.get(position);
        holder.kostName.setText(book.getName());
        holder.Bookingid.setText(book.getId());
        holder.BookingDate.setText(book.getBookdate());
        holder.BookingPrice.setText(String.format("Rp. %s", book.getPrice()));
        holder.userId.setText(Global.useridNow);
        //ini untuk ngeload image
        Glide.with(ctx).load(book.getImage()).into(holder.kostImg);
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView kostImg;
        TextView kostName, Bookingid, BookingDate, BookingPrice, userId;
        public ViewHolder(@NonNull View itemView , final KostAdapter.OnItemClickListener listener) {
            super(itemView);
            kostName = itemView.findViewById(R.id.book_Name_tv);
            kostImg = itemView.findViewById (R.id.kost_Image_tv);
            userId = itemView.findViewById (R.id.book_user_id);
            BookingDate = itemView.findViewById(R.id.booking_date);
            Bookingid = itemView.findViewById(R.id.booking_id);
            BookingPrice = itemView.findViewById(R.id.book_Price_tv);
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
