package com.example.vinayak.group57_inclass10;


import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesList extends Fragment {

    private OnFragmentInteractionListener mListener;
    ListView lv;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userId = null;
    private ImageButton imageAdd;
    private Button logoutButton;

    private ArrayList<Expense> expensesList;
    public ExpensesList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("Demo","AFragment: OnAttach");
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "should implement IFragmentTextChanged");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSignupInteraction();
        void onAddButtonClicked();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("demo","On activityCreated");
        lv = (ListView) getActivity().findViewById(R.id.listView);
        imageAdd = (ImageButton) getActivity().findViewById(R.id.imageAdd);
        logoutButton = (Button) getActivity().findViewById(R.id.buttonLogout);
        mAuth = FirebaseAuth.getInstance();
        expensesList = new ArrayList<Expense>();

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddButtonClicked();
            }
        });

        //expense = new Expense();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser !=null) {
            userId = firebaseUser.getUid();
            final DatabaseReference expenses = mDatabase.child("expenses");

            final ExpenseListDataAdapter adapter = new ExpenseListDataAdapter(getActivity(), R.layout.item_row_layout, expensesList);
            lv.setAdapter(adapter);

            expenses.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Expense expense = dataSnapshot.getValue(Expense.class);
                    if(expense.getUser()!=null && expense.getUser().equals(mAuth.getCurrentUser().getEmail())){
                        expensesList.add(expense);
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    mListener.onSignupInteraction();
                }
            });
        }
    }
}
