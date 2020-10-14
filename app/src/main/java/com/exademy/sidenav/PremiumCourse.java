package com.exademy.sidenav;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.exademy.R;
import com.exademy.activity.MainActivity;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PremiumCourse extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button Pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_course);

        Checkout.preload(getApplicationContext());

        Pay = (Button)findViewById(R.id.paymentbutton);

        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {
//
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_live_ftvZYbz2djwEp2");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logoexademy);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Exademy");
            options.put("description", "Premium Features");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "100");//pass amount in currency subunits
            options.put("prefill.email", "adhiraj818@gmail.com");
            options.put("prefill.contact","9506934412");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(),"Payment Successful" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s , Toast.LENGTH_LONG).show();
        Log.i("Adhiraj",s);
    }
}