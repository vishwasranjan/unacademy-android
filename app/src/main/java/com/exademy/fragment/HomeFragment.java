package com.exademy.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.exademy.R;
import com.exademy.RecyclerViewAdapters.VideoRecyclerViewAdapter;
import com.exademy.activity.SearchAutocompleteActivity;
import com.exademy.utility.Constant;
import com.exademy.utility.TypefaceUtility;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    final int ITEMS_LIMIT = 7;
    int currentPage = 0;

    boolean isScrolling = false, isResponsePending = false;
    int currentItems, totalItems, scrollOutItems;

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TypefaceUtility tfUtil;
    RequestQueue requestQueue;
    LinearLayoutManager llm;
    RecyclerView rv_videos;
    LinearLayout ll_search;
    TextView tv_search_hint;

    private static final String TAG = "HomeFragment";

    private ArrayList<String> mVideourls = new ArrayList<>();
    private ArrayList<String> mThumbnails = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        tfUtil = new TypefaceUtility(context);
        requestQueue = Volley.newRequestQueue(context);


        ll_search = rootView.findViewById(R.id.ll_search);
        rv_videos = rootView.findViewById(R.id.rv_videos);
        tv_search_hint = rootView.findViewById(R.id.tv_search_hint);


        tv_search_hint.setTypeface(tfUtil.getTypefaceRegular());

        llm = new LinearLayoutManager(context);

        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SearchAutocompleteActivity.class));
            }
        });

        getVideos();

        return rootView;
    }

    private void getVideos() {
        mThumbnails.add("sfd");
        mVideourls.add("dsf");

        mThumbnails.add("sfd");
        mVideourls.add("dsf");

        mThumbnails.add("sfd");
        mVideourls.add("dsf");
        initRecyclerView();
    }
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_videos.setLayoutManager(layoutManager);
        VideoRecyclerViewAdapter adapter = new VideoRecyclerViewAdapter(context, mThumbnails, mVideourls);
        rv_videos.setAdapter(adapter);

    }

}
