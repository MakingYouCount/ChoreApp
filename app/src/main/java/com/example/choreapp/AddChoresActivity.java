package com.example.choreapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddChoresActivity extends AppCompatActivity {

    //links the app to the database stored on firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = database.getReference();

    public static final String HOUSE_ID = "com.example.choreapp.HOUSE_ID";
    private String houseID;
    private List<String> choresToAllocate = new ArrayList<>();
    private RecyclerView recView;
    private AddChoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chores);

        Intent intent = getIntent();
        houseID = intent.getStringExtra(AddHouseMemberActivity.HOUSE_ID);

        // RecView stuff
        recView = (RecyclerView) findViewById(R.id.recView2);
        LinearLayoutManager recLayout = new LinearLayoutManager(this);
        recView.setLayoutManager(recLayout);
        recView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AddChoreAdapter(choresToAllocate);
        recView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recView.setAdapter(adapter);
    }

    public void addChore (View view){
        //Need code that will add selected chore to the list of chores house will use
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        String chore = spinner.getSelectedItem().toString();

        // Checks that chore hasn't already been added
        Boolean add = Boolean.TRUE;
        for(int i = 0; i < choresToAllocate.size(); i++) {
            if (choresToAllocate.get(i) == chore) {
                add = Boolean.FALSE;
            }
        }
        if (add == Boolean.TRUE) {
            choresToAllocate.add(chore);
            adapter.notifyDataSetChanged();   //This updates the recyclerView
        }
    }

    public void confirmChores (View view) {
        //mRef.child("groups").child(houseID).child("chores").setValue(choresToAllocate);
        Intent intent = new Intent(this, ChoreListActivity.class);
        intent.putExtra(HOUSE_ID,houseID);
        startActivity(intent);
    }
}
