package com.exademy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.exademy.*;
import com.exademy.adapter.TopicGroupItemAdapter;
import com.exademy.connection.HttpsRequest;
import com.exademy.model.TopicGroupItem;
import com.exademy.utility.APIS;
import com.exademy.utility.Constant;
import com.exademy.utility.DigitsUtility;
import com.exademy.utility.TypefaceUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EducatorProfileActivity extends AppCompatActivity {

    String user_name="", goal_uid="", first_name="", last_name="", avatar="";
    int live_minutes=0, live_classes=0;
    double upvotes=0.0;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TypefaceUtility tfUtil;
    RequestQueue requestQueue;
    LinearLayoutManager llm_ongoing, llm_upcoming;
    List<TopicGroupItem> onGoingList, upcomingList;
    TopicGroupItemAdapter onGoingAdapter, upcomingAdapter;


    NestedScrollView nsv_items;
    ImageView iv_avatar;
    TextView tv_name, tv_complete_profile, tv_live_min_value, tv_live_min_title, tv_classes_taught_value;
    TextView tv_classes_taught_title, tv_rating_value, tv_rating_title, tv_ongoing_courses, tv_upcoming;
    RecyclerView rv_ongoing_courses, rv_upcoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educator_profile);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        user_name=getIntent().getExtras().getString("user_name");
        goal_uid=getIntent().getExtras().getString("goal_uid");

        sp=getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        editor=sp.edit();
        tfUtil=new TypefaceUtility(this);
        requestQueue= Volley.newRequestQueue(this);
        onGoingList=new ArrayList<>();
        upcomingList=new ArrayList<>();
        onGoingAdapter=new TopicGroupItemAdapter(this,onGoingList, true);
        upcomingAdapter=new TopicGroupItemAdapter(this,upcomingList, false);

        nsv_items = findViewById(R.id.nsv_items);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_complete_profile = findViewById(R.id.tv_complete_profile);
        tv_live_min_value = findViewById(R.id.tv_live_min_value);
        tv_live_min_title = findViewById(R.id.tv_live_min_title);
        tv_classes_taught_value = findViewById(R.id.tv_classes_taught_value);
        tv_classes_taught_title = findViewById(R.id.tv_classes_taught_title);
        tv_rating_value = findViewById(R.id.tv_rating_value);
        tv_rating_title = findViewById(R.id.tv_rating_title);
        tv_ongoing_courses = findViewById(R.id.tv_ongoing_courses);
        tv_upcoming = findViewById(R.id.tv_upcoming);
        rv_ongoing_courses = findViewById(R.id.rv_ongoing_courses);
        rv_upcoming = findViewById(R.id.rv_upcoming);
        tv_live_min_value = findViewById(R.id.tv_live_min_value);
        tv_live_min_value = findViewById(R.id.tv_live_min_value);
        tv_live_min_value = findViewById(R.id.tv_live_min_value);

        tv_name.setTypeface(tfUtil.getTypefaceSemiBold());
        tv_complete_profile.setTypeface(tfUtil.getTypefaceRegular());
        tv_live_min_value.setTypeface(tfUtil.getTypefaceRegular());
        tv_live_min_title.setTypeface(tfUtil.getTypefaceRegular());
        tv_classes_taught_value.setTypeface(tfUtil.getTypefaceRegular());
        tv_classes_taught_title.setTypeface(tfUtil.getTypefaceRegular());
        tv_rating_value.setTypeface(tfUtil.getTypefaceRegular());
        tv_rating_title.setTypeface(tfUtil.getTypefaceRegular());
        tv_ongoing_courses.setTypeface(tfUtil.getTypefaceSemiBold());
        tv_upcoming.setTypeface(tfUtil.getTypefaceSemiBold());

        tv_complete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EducatorProfileActivity.this, UserProfileActivity.class);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });

        llm_ongoing=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        llm_upcoming=new LinearLayoutManager(this);
        rv_ongoing_courses.setLayoutManager(llm_ongoing);
        rv_upcoming.setLayoutManager(llm_upcoming);
        rv_ongoing_courses.setAdapter(onGoingAdapter);
        rv_upcoming.setAdapter(upcomingAdapter);
        rv_ongoing_courses.setNestedScrollingEnabled(false);
        rv_upcoming.setNestedScrollingEnabled(false);
        updateLiveStats();
        fetchOngoingCourses();
        fetchUpcomingCourses();

        fetchLiveStats();
    }

    public void fetchLiveStats(){
        Response.Listener<String> resListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                try{
                   JSONObject joResponse = new JSONObject(response);
                   avatar = joResponse.getString("intro_photo");
                   first_name = joResponse.getString("first_name");
                   last_name = joResponse.getString("last_name");
                   live_classes = joResponse.getInt("live_classes");
                   live_minutes = joResponse.getInt("live_minutes");
                   upvotes = joResponse.getDouble("upvotes");
                    updateLiveStats();
                }
                catch (Exception e){
                    Toast.makeText(EducatorProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EducatorProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };


        HashMap<String,String> params=new HashMap<String,String>();
        HashMap<String,String> headers=new HashMap<String,String>();

        HttpsRequest liveStatsRequest=new HttpsRequest(Request.Method.GET, APIS.LIVE_STATS+user_name+"/live_classes_stats/",resListener,errorListener,params,headers);
        requestQueue.add(liveStatsRequest);
    }

    public void fetchOngoingCourses(){
        Response.Listener<String> resListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject joResponse = new JSONObject(response);
                    JSONArray jaData = joResponse.getJSONArray("results");
                    if(jaData.length()>0){
                        tv_ongoing_courses.setVisibility(View.VISIBLE);
                        rv_ongoing_courses.setVisibility(View.VISIBLE);
                    }

                    for(int d=0; d<jaData.length(); d++){
                        JSONObject joDataItem = jaData.getJSONObject(d);
                        JSONObject joAuthor = joDataItem.getJSONObject("author");

                        String topic_group_name="";
                        JSONArray joTopicGroups = joDataItem.getJSONArray("topic_groups");
                        for(int t=0; t<joTopicGroups.length(); t++){
                            JSONObject joTopicGroup = joTopicGroups.getJSONObject(t);
                            topic_group_name+=joTopicGroup.getString("name")+" ";
                        }

                        TopicGroupItem topicGroupItem = new TopicGroupItem(
                                joDataItem.getString("uid"),
                                joDataItem.getString("name"),
                                joAuthor.getString("first_name")+" "+joAuthor.getString("last_name"),
                                joAuthor.getString("username"),
                                goal_uid,
                                joDataItem.getString("cover_photo"),
                                joDataItem.getString("starts_at"),
                                topic_group_name,
                                joDataItem.getInt("item_count")
                        );
                        onGoingList.add(topicGroupItem);
                    }
                }
                catch (Exception e){
                    Toast.makeText(EducatorProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                onGoingAdapter.notifyDataSetChanged();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EducatorProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };


        HashMap<String,String> params=new HashMap<String,String>();
        HashMap<String,String> headers=new HashMap<String,String>();

        HttpsRequest liveStatsRequest=new HttpsRequest(Request.Method.GET, APIS.PLUS_ONGOING+"?educator_username="+user_name+"&goal_uid="+goal_uid+"",resListener,errorListener,params,headers);
        requestQueue.add(liveStatsRequest);
    }

    public void fetchUpcomingCourses(){
        Response.Listener<String> resListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject joResponse = new JSONObject(response);
                    JSONArray jaData = joResponse.getJSONArray("results");
                    if(jaData.length()>0){
                        tv_upcoming.setVisibility(View.VISIBLE);
                        rv_upcoming.setVisibility(View.VISIBLE);
                    }

                    for(int d=0; d<jaData.length(); d++){
                        JSONObject joDataItem = jaData.getJSONObject(d);
                        JSONObject joAuthor = joDataItem.getJSONObject("author");

                        String topic_group_name="";
                        JSONArray joTopicGroups = joDataItem.getJSONArray("topic_groups");
                        for(int t=0; t<joTopicGroups.length(); t++){
                            JSONObject joTopicGroup = joTopicGroups.getJSONObject(t);
                            topic_group_name+=joTopicGroup.getString("name")+" ";
                        }

                        TopicGroupItem topicGroupItem = new TopicGroupItem(
                                joDataItem.getString("uid"),
                                joDataItem.getString("name"),
                                joAuthor.getString("first_name")+" "+joAuthor.getString("last_name"),
                                joAuthor.getString("username"),
                                goal_uid,
                                joDataItem.getString("cover_photo"),
                                joDataItem.getString("starts_at"),
                                topic_group_name,
                                joDataItem.getInt("item_count")
                        );
                        upcomingList.add(topicGroupItem);
                    }
                }
                catch (Exception e){
                    Toast.makeText(EducatorProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                upcomingAdapter.notifyDataSetChanged();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EducatorProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };


        HashMap<String,String> params=new HashMap<String,String>();
        HashMap<String,String> headers=new HashMap<String,String>();

        HttpsRequest liveStatsRequest=new HttpsRequest(Request.Method.GET, APIS.PLUS_UPCOMING+"?educator_username="+user_name+"&goal_uid="+goal_uid+"",resListener,errorListener,params,headers);
        requestQueue.add(liveStatsRequest);
    }

    public void updateLiveStats(){
        if(!avatar.equals("")){
            Picasso.with(this).load(avatar).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(iv_avatar);
        }
        tv_name.setText(first_name+" "+last_name);
        tv_live_min_value.setText(DigitsUtility.getCondensedNumber(live_minutes));
        tv_classes_taught_value.setText(DigitsUtility.getCondensedNumber(live_classes));
        tv_rating_value.setText(((int)upvotes)+"%");
    }


    public void back_onClick(View v){
        super.onBackPressed();
    }
}
