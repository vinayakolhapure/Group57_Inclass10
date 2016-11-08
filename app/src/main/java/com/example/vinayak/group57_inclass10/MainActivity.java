package com.example.vinayak.group57_inclass10;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements SignupFragment.OnFragmentInteractionListener,
        LoginActivity.OnLoginFragmentInteractionListener, ExpensesList.OnFragmentInteractionListener {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    public static String TAG = "Main Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser != null) {
            getFragmentManager().beginTransaction().replace(R.id.container,new ExpensesList(),"expense_add").addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginActivity(), "login_fragment").commit();
        }
        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    getFragmentManager().beginTransaction().replace(R.id.mainContainer,new AddExpense(),"expense_add").addToBackStack(null).commit();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginActivity(), "login_fragment").commit();
                }
            }
        };*/
    }

    @Override
    public void onSignupInteraction() {

    }

    @Override
    public void onLoginFragmentInteraction(String name, String uname) {
        if(name.equals("sign_up"))
        {
            Log.d("Main Activity","In signup");
            getFragmentManager().beginTransaction().replace(R.id.container, new SignupFragment(), "signup_fragment").addToBackStack(null).commit();
        }

        else if(name.equals("expesne_list")) {
           getFragmentManager().beginTransaction().replace(R.id.container, new ExpensesList(), "expesne_list").addToBackStack(null).commit();
        }
    }
}
