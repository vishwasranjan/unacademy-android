package com.exademy.sidenav;

import androidx.appcompat.app.AppCompatActivity;
import com.exademy.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Contact extends AppCompatActivity {

    Button mailus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
       mailus = findViewById(R.id.mailus);

       mailus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               submitfeedback();
           }
       });
    }

    private void submitfeedback() {
        String emailaddress = "adhiraj818@gmail.com";
        String subject = "enter your subject here";

        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL,new String[]{emailaddress});
        i.putExtra(Intent.EXTRA_SUBJECT,subject);
        i.putExtra(Intent.EXTRA_TEXT,"please enter your query here");
        i.setType("message/rfc822");
        startActivity(Intent.createChooser(i,"choose an email client"));
    }
}