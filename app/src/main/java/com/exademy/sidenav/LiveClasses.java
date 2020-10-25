package com.exademy.sidenav;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exademy.R;

public class LiveClasses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_classes);
        setmessage();
    }

    private void setmessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Confirm");
        builder.setCancelable(false);

        final LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText name=new EditText(getApplicationContext());
        name.setHint("Enter Name");

        final EditText DOB=new EditText(getApplicationContext());
        DOB.setHint("Enter D.O.B");

        final EditText UIN=new EditText(getApplicationContext());
        UIN.setHint("Enter UIN");

        layout.addView(name);
        layout.addView(DOB);
        layout.addView(UIN);
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }
}
