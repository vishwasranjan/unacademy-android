package com.exademy.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exademy.RecyclerViewAdapters.BooksRecyclerViewAdapter;
import com.exademy.R;
import com.exademy.activity.MainActivity;
import com.exademy.utility.TypefaceUtility;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyLibraryFragment extends Fragment implements PaymentResultListener {

    Context context;
    TypefaceUtility tfUtil;

    LinearLayout ll_login_signup;
    TextView tv_search_hint, tv_lists, tv_courses, tv_lessons, tv_downloads, tv_history;
    TextView tv_title, tv_sub_title, tv_login_signup;
    Button bookpayment;
    RecyclerView recyclerView;
    RelativeLayout bookpaymentlayout;


    private static final String TAG = "MyLibraryFragment";

    private ArrayList<String> mImageurls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAuthor = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_library, container, false);
        tfUtil = new TypefaceUtility(context);


        recyclerView = rootView.findViewById(R.id.booksrecyclerview);
        bookpayment = rootView.findViewById(R.id.bookpayment);
        bookpaymentlayout = rootView.findViewById(R.id.bookpaymentlayout);
        Checkout.preload(context);


        bookpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
                bookpayment.startAnimation(animation);
                startPayment();
                if (MainActivity.sharedPreferences.getBoolean("bookpayment", false) != true) {
                    recyclerView.setVisibility(View.VISIBLE);
                    bookpaymentlayout.setVisibility(View.GONE);
                    bookpayment.setVisibility(View.GONE);
                }
            }
        });


        getImages();

//        tv_search_hint.setTypeface(tfUtil.getTypefaceRegular());
//        tv_lists.setTypeface(tfUtil.getTypefaceRegular());
//        tv_courses.setTypeface(tfUtil.getTypefaceRegular());
//        tv_lessons.setTypeface(tfUtil.getTypefaceRegular());
//        tv_downloads.setTypeface(tfUtil.getTypefaceRegular());
//        tv_history.setTypeface(tfUtil.getTypefaceRegular());
//        tv_title.setTypeface(tfUtil.getTypefaceBold());
//        tv_sub_title.setTypeface(tfUtil.getTypefaceRegular());
//        tv_login_signup.setTypeface(tfUtil.getTypefaceRegular());
        return rootView;
    }


    private void getImages() {


//        mImageurls.add("https://lh3.googleusercontent.com/proxy/u64boaj5eZiRsCv5Fv6XXP9K7wfcqRvTCkhkc2egX4VID0_uraDuUv_t9qKYZhOGejRBUaRaswUR8VlEJEE9wil50X2xSzpuI8U3heel8XguCbnZh1dauITkLA5z3ocY88ZcsA2876YCPhM_Zg");
        mNames.add("H.C. verma");
        mAuthor.add("Physics");

        mNames.add("R.d. sharma");
        mAuthor.add("Maths");

        mNames.add("Irovdo");
        mAuthor.add("Physics");

        mNames.add("Ncert");
        mAuthor.add("Chemistry");


        initRecyclerView();
    }

    private void initRecyclerView() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        BooksRecyclerViewAdapter adapter = new BooksRecyclerViewAdapter(context, mNames, mAuthor);
        recyclerView.setAdapter(adapter);

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
        final Activity activity = getActivity();

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
            options.put("prefill.contact", "9506934412");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(context, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        showBooks();
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        Log.i("Adhiraj", s);
    }

    public void showBooks() {
        MainActivity.sharedPreferences.edit().putBoolean("bookpayment", true).apply();
    }
}
