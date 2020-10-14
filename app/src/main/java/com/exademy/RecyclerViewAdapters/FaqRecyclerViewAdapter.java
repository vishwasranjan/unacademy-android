package com.exademy.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.exademy.R;

import java.util.ArrayList;

public class FaqRecyclerViewAdapter extends RecyclerView.Adapter<FaqRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mQuery = new ArrayList<String>();
    private ArrayList<String> mReply = new ArrayList<String>();
    private Context mContext;

    public FaqRecyclerViewAdapter(Context mContext, ArrayList<String> mQuery , ArrayList<String> mReply){

        this.mQuery =mQuery;
        this.mReply = mReply;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public FaqRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_recycler_layout, parent, false);
        return new FaqRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FaqRecyclerViewAdapter.ViewHolder holder, int position) {


        holder.faq_query.setText(mQuery.get(position));
        holder.faq_reply.setText(mReply.get(position));

        holder.faq_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.faq_reply_cardview.getVisibility() ==  View.VISIBLE){
                    holder.faq_reply_cardview.setVisibility(View.GONE);
                }else{
                    holder.faq_reply_cardview.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mQuery.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView faq_query,faq_reply;
        CardView faq_reply_cardview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            faq_reply_cardview = itemView.findViewById(R.id.faq_reply_cardview);
            faq_query = itemView.findViewById(R.id.faq_query);
            faq_reply = itemView.findViewById(R.id.faq_reply);
        }
    }
}
