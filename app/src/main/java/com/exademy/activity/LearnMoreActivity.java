package com.exademy.activity;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.exademy.adapter.QuestionAnswerAdapter;
import com.exademy.connection.HttpsRequest;
import com.exademy.model.QuestionAnswer;
import com.exademy.utility.APIS;
import com.exademy.utility.Constant;
import com.exademy.utility.TypefaceUtility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LearnMoreActivity extends AppCompatActivity {


    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TypefaceUtility tfUtil;
    RequestQueue requestQueue;
    LinearLayoutManager llm;
    List<QuestionAnswer> questionAnswerList;
    QuestionAnswerAdapter questionAnswerAdapter;

    TextView tv_title;
    RecyclerView rv_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        sp=getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        editor=sp.edit();
        tfUtil=new TypefaceUtility(this);
        requestQueue= Volley.newRequestQueue(this);
        questionAnswerList=new ArrayList<>();
        questionAnswerAdapter=new QuestionAnswerAdapter(this,questionAnswerList);

        tv_title = findViewById(R.id.tv_title);
        tv_title.setTypeface(tfUtil.getTypefaceSemiBold());
        rv_items=findViewById(R.id.rv_items);

        llm=new LinearLayoutManager(this);
        rv_items.setLayoutManager(llm);
        rv_items.setAdapter(questionAnswerAdapter);
        rv_items.setNestedScrollingEnabled(false);
        fetchContent();
    }

    public void fetchContent(){
        Response.Listener<String> resListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                questionAnswerList.clear();
                try{
                    JSONArray jaResults = new JSONArray(response);
                    for(int k=0; k<jaResults.length(); k++){
                        JSONObject joResult = jaResults.getJSONObject(k);
                        String question = joResult.getString("question");
                        String answer = joResult.getString("answer");
                        questionAnswerList.add(new QuestionAnswer(question, answer));
                    }

                }
                catch (Exception e){
                    Toast.makeText(LearnMoreActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                }
                questionAnswerAdapter.notifyDataSetChanged();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LearnMoreActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        };


        HashMap<String,String> params=new HashMap<String,String>();
        HashMap<String,String> headers=new HashMap<String,String>();

        HttpsRequest learnMoreRequest=new HttpsRequest(Request.Method.GET, APIS.LEARN_MORE,resListener,errorListener,params,headers);
        requestQueue.add(learnMoreRequest);
    }

    public void back_onClick(View v){
        super.onBackPressed();
    }
}
