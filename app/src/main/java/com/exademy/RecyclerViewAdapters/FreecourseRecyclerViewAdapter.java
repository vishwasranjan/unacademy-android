package com.exademy.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exademy.R;

import java.util.ArrayList;

public class FreecourseRecyclerViewAdapter extends RecyclerView.Adapter<FreecourseRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mCourses = new ArrayList<String>();
    private Context mContext;

    public FreecourseRecyclerViewAdapter(ArrayList<String> course, Context context){
        mCourses = course;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freecourses,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.courses.setText(mCourses.get(position));

        holder.courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "free courses will be available soon", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courses;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courses = itemView.findViewById(R.id.courseTextview);
        }
    }
}
