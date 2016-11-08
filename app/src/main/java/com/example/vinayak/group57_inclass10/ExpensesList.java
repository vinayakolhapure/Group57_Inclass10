package com.example.vinayak.group57_inclass10;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesList extends Fragment {

    private OnFragmentInteractionListener mListener;
    ListView lv;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userId = null;

    private ArrayList<Expense> expensesList;
    Expense expense;
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (ListView) getActivity().findViewById(R.id.listView);
        mAuth = FirebaseAuth.getInstance();
        expensesList = new ArrayList<Expense>();

        expense = new Expense();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        final DatabaseReference expenses = mDatabase.child("expenses");

        final ExpenseListDataAdapter adapter = new ExpenseListDataAdapter(getActivity(), R.layout.item_row_layout,expensesList);
        lv.setAdapter(adapter);

        expenses.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                switch (dataSnapshot.getKey()) {
                    case "eAmount":
                        expense.setAmount(value);
                        break;
                    case "eCategory":
                        expense.setCategory(value);
                        break;
                    case "eDate":
                        expense.setDate(value);
                        break;
                    case "eID":
                        expense.setId(value);
                        break;
                    case "eName":
                        expense.setName(value);
                        break;
                }
                if((expense.getName()!=null && !expense.getName().isEmpty()) && (expense.getAmount()!=null && !expense.getAmount().isEmpty())
                        && (expense.getDate()!=null && !expense.getDate().isEmpty()) &&
                        (expense.getCategory()!=null && !expense.getCategory().isEmpty()) &&
                        (expense.getId()!=null && !expense.getId().isEmpty())) {
                    expensesList.add(expense);
                    Log.d("demo",expense.toString());
                    adapter.notifyDataSetChanged();
                }
                //expensesList.add(expense);
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

    }
}
