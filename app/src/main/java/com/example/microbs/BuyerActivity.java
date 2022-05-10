package com.example.microbs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyerActivity extends AppCompatActivity implements BuyerAdapter.ItemClickListener {

    private Button addB;
    private EditText bnameRef,bpassRef,bpibRef;
    private String bname,bpass,bpib;
    private FirebaseFirestore db;
    private List<Buyer> buyerList;
    private BuyerAdapter adapter;
    private RecyclerView rv_b;
    private Bundle bundle;
    private FloatingActionButton fab_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        bnameRef = findViewById(R.id.et_bName);
        bpassRef = findViewById(R.id.et_bPass);
        bpibRef = findViewById(R.id.et_bPib);
        addB = findViewById(R.id.btn_addB);
        db = FirebaseFirestore.getInstance();
        buyerList = new ArrayList<Buyer>();
        rv_b = findViewById(R.id.rv_buyer);
        rv_b.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new BuyerAdapter(this,buyerList);
        rv_b.setAdapter(adapter);
        adapter.setClickListener(this::onItemClick);
        adapter.notifyDataSetChanged();

        fab_back = findViewById(R.id.fab_backH);
        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EmployeActivity.class));
            }
        });

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBuyer();
            }
        });

        EventChangeListener();
    }



    public void addBuyer(){
        Map<String,Object> buy = new HashMap<>();
        bname = bnameRef.getText().toString();
        bpass = bpassRef.getText().toString();
        bpib = bpibRef.getText().toString();
        if (bname != null && bpass != null && bpib != null) {
            buy.put("name", bname);
            buy.put("password", bpass);
            buy.put("pib", bpib);
            db.collection("kupac").document(bname).set(buy).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Succsess", Toast.LENGTH_LONG).show();
                    bnameRef.setText("");
                    bpassRef.setText("");
                    bpibRef.setText("");
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Fill out all the fields", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    private void EventChangeListener() {
        db.collection("kupac").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        buyerList.add(dc.getDocument().toObject(Buyer.class));
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getApplicationContext(),"You clicked "+ position,Toast.LENGTH_SHORT).show();
        bundle = new Bundle();
        bundle.putString("name", String.valueOf(buyerList.get(position).name));
        bundle.putString("password", String.valueOf(buyerList.get(position).password));
        bundle.putString("pib", String.valueOf(buyerList.get(position).pib));
        BuyerFragment bFrag = new BuyerFragment();
        bFrag.setArguments(bundle);
        replaceFragment(bFrag);
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cLayoutMain,fragment);
        fragmentTransaction.commit();
        addB.setVisibility(View.INVISIBLE);
    }
}