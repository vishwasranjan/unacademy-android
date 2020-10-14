package com.exademy.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exademy.R;
import com.exademy.activity.VideoPlayer;

import java.util.ArrayList;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> {

   private  ArrayList<String> mthumbnail = new ArrayList<>();
   private  ArrayList<String> mvideourls = new ArrayList<>();
   private Context mcontext;

   public VideoRecyclerViewAdapter(Context context , ArrayList<String> thumbnail ,ArrayList<String> videourls){
       mthumbnail = thumbnail;
       mvideourls = videourls;
       mcontext = context;
   }

    @NonNull
    @Override
    public VideoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_videos,parent,false);
       return new VideoRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerViewAdapter.ViewHolder holder, int position) {

       holder.videoimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(mcontext,"wait while video is loading",Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(mcontext, VideoPlayer.class);
                mcontext.startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return mthumbnail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       ImageView videoimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoimage = itemView.findViewById(R.id.videoimage);
        }
    }
}
