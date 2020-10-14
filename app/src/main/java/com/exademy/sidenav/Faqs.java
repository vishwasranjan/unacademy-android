package com.exademy.sidenav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exademy.RecyclerViewAdapters.FaqRecyclerViewAdapter;
import com.exademy.R;

import android.os.Bundle;

import java.util.ArrayList;

public class Faqs extends AppCompatActivity {

    private ArrayList<String> mQuery = new ArrayList<String>();
    private ArrayList<String> mReply = new ArrayList<String>();
    RecyclerView recyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        recyclerView = findViewById(R.id.faqrecyclerview);

        getFaq();

    }

    private void getFaq() {

        mQuery.add("How to join state whatsapp group ?");
        mReply.add("You have to message on Exademy Official Number (+91-7381987177) through Whatsapp with the Text that \"sir,I want to join State Whatsapp Group.Please add me\" with the following Basic details :\n" +
                "\n" +
                "Name :\n" +
                "\n" +
                "City:\n" +
                "\n" +
                "State:\n" +
                "\n" +
                "UPSC Appearing Year :");

        mQuery.add("How to take weekly topic-wise test?");
        mReply.add("First of all you need to join State Whatsapp group .After joining state whatsapp group.\n" +
                "\n" +
                "Ask in your state group about Topic-test process and Schedule.Your fellow mates or State officers will guide you.");

        mQuery.add("How to join Full Syllabus Test Series ?");
        mReply.add("Read our blog : Exademy Full syllabus Test Series");

        mQuery.add("Is there everything free on Exademy ?");
        mReply.add("Yes,UPSC,BPSC Lectures & Topic-wise weekly test is completely free .");

        mQuery.add("I dont't have whatsapp how can i give topic-wise weekly test ?");
        mReply.add("If you don't have whatsapp don't worry.Kindly login on Exademy website.And,navigate your cursor to the Menu of the website.There you will find a seperate block named as \"Community Group\".Click on that and join your respective group.Questions will be uploaded there at the scheduled Date and Time.");

        mQuery.add("When UPSC Mathematics Optional syllabus will get completed ?");
        mReply.add("UPSC Mathematics Optional Syllabus will get completed by October 2020.");

        mQuery.add("Where should I have to upload answer sheet of Weekly Tests ?");
        mReply.add("Go to \"My services\" .There you will find a section named as \"Pariksha\" .In that section you will find a button to \"Uplaod Answer Sheet\".Click on that & Fill out Basic Details and upload your Pdf file (Answer Sheet).");


        initRecyclerView();
    }

    private void initRecyclerView() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FaqRecyclerViewAdapter adapter = new FaqRecyclerViewAdapter(getApplicationContext(), mQuery, mReply);
        recyclerView.setAdapter(adapter);

    }
    
}