package com.example.looters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.wallet.PaymentData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PayActivity extends AppCompatActivity {
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 0;
    Button gpaybtn;
    String amount,otp;
    Button cardpay;
    String note,name,upivirtualid;
    final int UPI_PAYMENT = 0;
    Button paytmbtn;
    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    Button button;
    Random random = new Random();
    String id = String.format("%04d", random.nextInt(10000));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        gpaybtn = (Button)findViewById(R.id.gpaybtn);
        paytmbtn = (Button)findViewById(R.id.paytmbtn);
        upivirtualid = "karnatheveer@oksbi";
        name="Karnaveer Shaktawat";
        note="Looters";
        amount = "40";

        if (getIntent().hasExtra("Price"))
        {
            amount = getIntent().getStringExtra("Price");
            otp = getIntent().getStringExtra("otp");
        }
      //  Toast.makeText(this,amount,Toast.LENGTH_SHORT).show();
        gpaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi(name, upivirtualid,
                        note, amount);
         //       startActivity(new Intent(PayActivity.this,SubmitActivity.class));
            }
        });
        paytmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paytm();
            }
        });


        cardpay = (Button)findViewById(R.id.cardpay);
        cardpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                int a = account.getEmail().toString().indexOf("@");
//                Random random = new Random();
//                String id = String.format("%04d", random.nextInt(10000));
                String b = account.getEmail().toString().substring(0,a);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(b);

                // databaseReference.child("OTP").setValue(OTP);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            if (!(dataSnapshot1.child("q").equals("0"))) {
                                Log.d("ADD!", dataSnapshot1.getValue().toString());
                                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Looters").child("Gaurav").child(otp).child(b);
                                databaseReference1.setValue(dataSnapshot.getValue());
                                databaseReference1.push();
                                databaseReference.removeValue();
                            }
                        }
               //        String key = databaseReference1.getKey();
//                        if (databaseReference1.child(b) != null)
//                        {
//
//                        }





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent = new Intent(PayActivity.this,MenuActivity.class);
              //  intent.putExtra("Card","Paid With Card");
                startActivity(intent);
                Toast.makeText(PayActivity.this,"Order Placed Successfully",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void Gpay()
    {
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", "manas14agarwal@oksbi")    //gauravgaur7069133082@okaxis
                        .appendQueryParameter("pn", "manas agaarwal")          //gaurav gaur
                        .appendQueryParameter("mc", "5812")
                        .appendQueryParameter("tr", "123456789")
                        .appendQueryParameter("tn", "Looters")
                        .appendQueryParameter("am", amount)
                        .appendQueryParameter("cu", "INR")
                        .appendQueryParameter("url", "")
                        .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
//                if (intent == null)
//                {
//                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                    intent1.setData(Uri.parse(
//                            "https://play.google.com/store/apps/details?id=com.example.gpay"));
//                    intent1.setPackage("com.android.vending");
//                    startActivity(intent1);
//                }
//                else
        try {
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);

        }
        catch (Exception e)
        {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse(
                    "https://play.google.com/store/apps/details?id=com.google.android.apps.nbu.paisa.user"));
            intent1.setPackage("com.android.vending");
            startActivity(intent1);
        }






    }

    void payUsingUpi(  String name,String upiId, String note, String amount) {
        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(PayActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PayActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(PayActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
                Intent intent = new Intent(PayActivity.this,FinalActivity.class);
                Random random = new Random();
                intent.putExtra("OTP",String.format("%04d", random.nextInt(10000)));
                startActivity(intent);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PayActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);

            }
            else {
                Toast.makeText(PayActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            Log.e("UPI", "Internet issue: ");

            Toast.makeText(PayActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public void Paytm()
    {
        if (ContextCompat.checkSelfPermission(PayActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PayActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        final String mid = "iXxJSW67783594128895";       //"yQlodq70335332297942"
        final String custid = "custid";
        final String orderid = UUID.randomUUID().toString().substring(0,28);
        String url = "https://priyansh-kedia.000webhostapp.com/paytmc/generateChecksum.php";
        final String callbackurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(PayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("CHECKSUMHASH"))
                    {
                        String CHECKSUMHASH = jsonObject.getString("CHECKSUMHASH");
                        PaytmPGService paytmPGService = PaytmPGService.getProductionService();

                        HashMap<String, String> paramMap = new HashMap<String,String>();
                        paramMap.put( "MID" , mid);
                        paramMap.put( "ORDER_ID" , orderid);
                        paramMap.put( "CUST_ID" , custid);
                        paramMap.put( "CHANNEL_ID" , "WAP");
                        paramMap.put( "TXN_AMOUNT" , amount);
                        paramMap.put( "WEBSITE" , "WEBSTAGING");
                        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
                        paramMap.put( "CALLBACK_URL", callbackurl);
                        paramMap.put("CHECKSUMHASH",CHECKSUMHASH);

                        PaytmOrder paytmOrder = new PaytmOrder(paramMap);
                        paytmPGService.initialize(paytmOrder,null);
                        paytmPGService.startPaymentTransaction(PayActivity.this, true, true, new PaytmPaymentTransactionCallback() {
                            @Override
                            public void onTransactionResponse(Bundle inResponse) {
                                Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void networkNotAvailable() {
                                Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void clientAuthenticationFailed(String inErrorMessage) {
                                Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void someUIErrorOccurred(String inErrorMessage) {
                                Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();
                            }


                            @Override
                            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                                Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                            }


                            @Override
                            public void onBackPressedCancelTransaction() {

                            }

                            @Override
                            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<String,String>();
                paramMap.put( "MID" , mid);
                paramMap.put( "ORDER_ID" , orderid);
                paramMap.put( "CUST_ID" , custid);
                paramMap.put( "CHANNEL_ID" , "WAP");
                paramMap.put( "TXN_AMOUNT" , amount);
                paramMap.put( "WEBSITE" , "WEBSTAGING");
                paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
                paramMap.put( "CALLBACK_URL", callbackurl);

                return paramMap;
            }
        };
        requestQueue.add(stringRequest);


    }
    private void handlePaymentSuccess(PaymentData paymentData) {
        Toast.makeText(PayActivity.this, "Payment Success", Toast.LENGTH_LONG).show();
    }
    private void handleError(int statusCode) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }
    }


