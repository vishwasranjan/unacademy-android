package com.exademy.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.exademy.R;

import java.util.ArrayList;

public class BooksRecyclerViewAdapter extends RecyclerView.Adapter<BooksRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mImageurls = new ArrayList<String>();
    private ArrayList<String> mNames = new ArrayList<String>();
    private ArrayList<String> mAuthor = new ArrayList<String>();

    private Context mContext;

    public BooksRecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> author ){
//        mImageurls =imageurls;
        mNames = names;
        mAuthor = author;
        mContext = context;
    }

    @Override
    public BooksRecyclerViewAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_books, parent, false);
        return new BooksRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BooksRecyclerViewAdapter.ViewHolder holder, final int position) {

//        Glide.with(mContext)
//                .asBitmap()
//                .load(mImageurls.get(position))
//                .into(holder.bookimg);

        holder.bookname.setText(mNames.get(position));
        holder.bookauthor.setText(mAuthor.get(position));

//        holder.bookimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
//                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_LONG).show();
//            }
//        });
        holder.readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Read button is clicked",Toast.LENGTH_SHORT).show();
                Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.bounce);
                ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                scale.setDuration(300);
                scale.setInterpolator(new OvershootInterpolator());
                holder.readbutton.startAnimation(scale);
            }
        });

        holder.bookcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.zoomout);
//                holder.bookcardview.startAnimation(animation);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView bookimg;
        TextView bookname , bookauthor;
        Button readbutton;
        CardView bookcardview;

        public ViewHolder(View itemView) {
            super(itemView);
          bookimg = itemView.findViewById(R.id.bookimg);
          bookname = itemView.findViewById(R.id.bookname);
          bookauthor = itemView.findViewById(R.id.bookauthor);
          readbutton = itemView.findViewById(R.id.Read);
          bookcardview = itemView.findViewById(R.id.bookcardview);

        }
    }
}
