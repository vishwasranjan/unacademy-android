package com.exademy.activity;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.exademy.*;
import com.exademy.adapter.SearchAutocompleteItemAdapter;
import com.exademy.connection.HttpsRequest;
import com.exademy.model.SearchAutocompleteItem;
import com.exademy.utility.APIS;
import com.exademy.utility.Constant;
import com.exademy.utility.KeyboardUtility;
import com.exademy.utility.NetworkUtility;
import com.exademy.utility.TypefaceUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchAutocompleteActivity extends AppCompatActivity {

    String currentQuery="";
    boolean explicitQuerySet = false;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TypefaceUtility tfUtil;
    RequestQueue requestQueue;
    LinearLayoutManager llm_items;
    List<SearchAutocompleteItem> searchAutocompleteItemList;
    SearchAutocompleteItemAdapter searchAutocompleteItemAdapter;

    SearchView sv_query;
    RecyclerView rv_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_autocomplete);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        sp=getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        editor=sp.edit();
        tfUtil=new TypefaceUtility(this);
        requestQueue= Volley.newRequestQueue(this);
        searchAutocompleteItemList=new ArrayList<>();
        searchAutocompleteItemAdapter=new SearchAutocompleteItemAdapter(this,searchAutocompleteItemList);

        rv_items=findViewById(R.id.rv_items);

        sv_query=findViewById(R.id.sv_query);
        TextView tv_search = sv_query.findViewById(R.id.search_src_text);
        tv_search.setTypeface(tfUtil.getTypefaceRegular());

        llm_items=new LinearLayoutManager(this);
        rv_items.setLayoutManager(llm_items);
        rv_items.setAdapter(searchAutocompleteItemAdapter);

        rv_items.setNestedScrollingEnabled(false);
        fetchPopulars();

        sv_query.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query=query.trim();
                sv_query.clearFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals(currentQuery)){
                    return false;
                }

                if(explicitQuerySet){
                    explicitQuerySet=false;
                    return false;
                }

                if(NetworkUtility.isAvailable(SearchAutocompleteActivity.this)){
                    if(newText.trim().length()==0){
                        fetchPopulars();
                    }
                    else{
                        fetchAutoCompleteContent(newText.trim());
                    }
                }
                currentQuery=newText;
                return false;
            }
        });
    }


    public void fetchPopulars(){
        Response.Listener<String> resListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                searchAutocompleteItemList.clear();
                try{
                    JSONArray jaResults = new JSONArray(response);
                    for(int k=0; k<jaResults.length(); k++){
                        JSONObject joResult = jaResults.getJSONObject(k);
                        searchAutocompleteItemList.add(new SearchAutocompleteItem(
                                "popular", joResult.getString("query"), null, null, 0, 0));
                    }

                }
                catch (Exception e){
                    Toast.makeText(SearchAutocompleteActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                }
                searchAutocompleteItemAdapter.notifyDataSetChanged();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchAutocompleteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };


        HashMap<String,String> params=new HashMap<String,String>();
        HashMap<String,String> headers=new HashMap<String,String>();

        HttpsRequest popularsRequest=new HttpsRequest(Request.Method.GET, APIS.POPULARS,resListener,errorListener,params,headers);
        requestQueue.add(popularsRequest);
    }

    public void fetchAutoCompleteContent(String query){
        Response.Listener<String> resListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                searchAutocompleteItemList.clear();
                try{
                    JSONArray jaResults = new JSONArray(response);
                    for(int i=0; i<jaResults.length(); i++){
                        JSONObject joResult = jaResults.getJSONObject(i);
                        switch (joResult.getString("type")){
                            case "popular":{
                                searchAutocompleteItemList.add(new SearchAutocompleteItem(
                                        "popular", joResult.getString("query"), null, null, 0, 0));
                                break;
                            }
                            case "search":{
                                searchAutocompleteItemList.add(new SearchAutocompleteItem(
                                        "search", joResult.getString("label"), null, null, 0, 0));
                                break;
                            }
                            case "user":{
                                JSONObject joDetails = joResult.getJSONObject("details");
                                String label = joResult.getString("label");
                                String user_name = joDetails.getString("username");
                                String avatar = joDetails.getString("avatar");
                                int followers = joDetails.getInt("followers");
                                int courses = joDetails.getInt("courses");
                                searchAutocompleteItemList.add(new SearchAutocompleteItem("user", label, user_name, avatar, followers, courses));
                                break;
                            }
                            case "keyword":{
                                searchAutocompleteItemList.add(new SearchAutocompleteItem(
                                        "keyword", joResult.getString("label"), null, null, 0, 0));
                                break;
                            }
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(SearchAutocompleteActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                }
                searchAutocompleteItemAdapter.notifyDataSetChanged();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchAutocompleteActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };


        HashMap<String,String> params=new HashMap<String,String>();
        HashMap<String,String> headers=new HashMap<String,String>();

        HttpsRequest autocompleteSearchRequest=new HttpsRequest(Request.Method.GET, APIS.SEARCH_AUTOCOMPLETE+"?q="+query,resListener,errorListener,params,headers);
        requestQueue.add(autocompleteSearchRequest);
    }

    public void updateSearchQuery(String newQuery){
        explicitQuerySet=true;
        sv_query.setQuery(newQuery, false);
    }

    @Override
    public void onResume(){
        super.onResume();
        KeyboardUtility.hideKeyboard(this, rv_items);
    }

    public void back_onClick(View v){
        super.onBackPressed();
    }
}
