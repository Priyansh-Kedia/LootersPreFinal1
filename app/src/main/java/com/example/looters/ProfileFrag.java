package com.example.looters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFrag extends Fragment {
TextView profilename,profileid,profilenameimg;
Button logout;
GoogleSignInClient mGoogleSignInClient;
    public ProfileFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        profilename = (TextView)view.findViewById(R.id.profilename1);
        profileid=(TextView)view.findViewById(R.id.profileid);
        logout = (Button)view.findViewById(R.id.logoutbtn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        int a = account.getEmail().toString().indexOf("@");
//                Random random = new Random();
//                String id = String.format("%04d", random.nextInt(10000));
        String b = account.getEmail().toString().substring(0,a);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(b);
        profilenameimg = (TextView)view.findViewById(R.id.profileimgname);
        profilenameimg.setText(account.getDisplayName().toString().substring(0,1));
        profilename.setText(account.getDisplayName().toString());
        profileid.setText(account.getEmail().toString().substring(0,9));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                if (databaseReference != null)
                {
                    databaseReference.removeValue();
                }
            }
        });
        return view;
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"Successfully Logged Out",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),LoginActivity.class);
                        intent.putExtra("Done","Done");
                        startActivity(intent);
                    }
                });
    }
}
