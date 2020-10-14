package com.exademy.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.exademy.R;
import com.exademy.activity.GoalsActivity;
import com.exademy.activity.LearnMoreActivity;
import com.exademy.activity.LoginRegisterActivity;
import com.exademy.adapter.GoalFeedItemAdapter;
import com.exademy.connection.HttpsRequest;
import com.exademy.model.AllTopicsItem;
import com.exademy.model.Educator;
import com.exademy.model.GoalFeedItem;
import com.exademy.model.TopicGroupItem;
import com.exademy.utility.APIS;
import com.exademy.utility.NetworkUtility;
import com.exademy.utility.TypefaceUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlusFragment extends Fragment {

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plus, container, false);


        return rootView;
    }
}
