package com.example.mentalitree.ui.motivation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalitree.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyViewHolder> {


    ArrayList<QuoteModel> quotes;

    public QuoteAdapter(ArrayList<QuoteModel> quoteList){
        this.quotes = quoteList;
    }

    @NonNull
    @Override
    public QuoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.old_motivational_quote, parent, false);

        return new QuoteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteAdapter.MyViewHolder holder, int position) {
        holder.quoteTv.setText(quotes.get(position).getQuote());
        holder.dateTv.setText(quotes.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView quoteTv;
        TextView dateTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteTv = itemView.findViewById(R.id.oldQuoteTv);
            dateTv = itemView.findViewById(R.id.oldQuoteDateTv);
        }
    }
}
