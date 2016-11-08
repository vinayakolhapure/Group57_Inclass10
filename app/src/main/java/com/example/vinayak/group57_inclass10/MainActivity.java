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
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,new ExpensesList(),"expense_list")
                    .addToBackStack(null)
                    .commit();
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new LoginActivity(), "login_fragment")
                    .commit();
        }
    }

    @Override
    public void onClickSignUp() {
        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SignupFragment(), "signup_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToExpenseList() {
        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ExpensesList(), "expense_list")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSignupInteraction() {
        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ExpensesList(), "expense_list")
                .addToBackStack(null)
                .commit();
    }
}
