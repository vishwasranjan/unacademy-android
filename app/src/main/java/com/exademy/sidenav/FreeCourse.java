package com.exademy.sidenav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.exademy.R;
import com.exademy.RecyclerViewAdapters.FaqRecyclerViewAdapter;
import com.exademy.RecyclerViewAdapters.FreecourseRecyclerViewAdapter;

import java.util.ArrayList;

public class FreeCourse extends AppCompatActivity {

    private ArrayList<String> mCourses = new ArrayList<String>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_course);
        recyclerView = findViewById(R.id.freecourseRecyclerView);
        get_Course();
    }

    public void get_Course(){
        mCourses.add("Thermodynamics");
        mCourses.add("Radioactivity");
        mCourses.add("Algebra Basics");
        mCourses.add("Advance Mathematics");
        mCourses.add("OScillations");
        mCourses.add("Momentum");
        initRecyclerView();
    }
    private void initRecyclerView() {


        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        FreecourseRecyclerViewAdapter adapter = new FreecourseRecyclerViewAdapter(mCourses,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
}