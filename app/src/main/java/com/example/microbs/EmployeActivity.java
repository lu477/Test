package com.example.microbs;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeActivity extends AppCompatActivity implements EmployeAdapter.ItemClickListener {

    private EmployeAdapter adapter;
    private EditText nameRef;
    private EditText surenameRef;
    private EditText cityRef;
    private EditText passwordRef;
    private ToggleButton btnML, btnMC,btnMR;
    private Button addEmp;
    private String name,surename,city,password;
    private RecyclerView rvMain;
    private List<Employe> employes;
    private FirebaseFirestore db;
    private FloatingActionButton fabBuyer,fabWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe);

        fabBuyer = findViewById(R.id.fbtn_buyer);
        fabWeather = findViewById(R.id.fbtn_second);
        nameRef = findViewById(R.id.et_empName);
        surenameRef = findViewById(R.id.et_empSure);
        passwordRef = findViewById(R.id.et_empPass);
        cityRef = findViewById(R.id.et_empCity);
        btnML = findViewById(R.id.tbtn_M2);
        btnMC = findViewById(R.id.tbtn_M1);
        btnMR = findViewById(R.id.tbtn_M3);
        addEmp = findViewById(R.id.btnAddEmp);
        db = FirebaseFirestore.getInstance();
        employes = new ArrayList<Employe>();
        rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeAdapter(this, employes);
        adapter.notifyDataSetChanged();
        rvMain.setAdapter(adapter);

        addEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmp();
            }
        });

        fabWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),WeatherActivity.class));
            }
        });

        EventChangeListener();

        fabBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BuyerActivity.class));
            }
        });
    }

    private void EventChangeListener() {
        db.collection("korisnik").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        employes.add(dc.getDocument().toObject(Employe.class));
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public void addEmp(){
        Map<String,Object> emp = new HashMap<>();
        name = nameRef.getText().toString();
        surename = surenameRef.getText().toString();
        city = cityRef.getText().toString();
        password = passwordRef.getText().toString();
        if (name != null && surename != null && city != null&& password != null) {
            emp.put("name", name);
            emp.put("surename", surename);
            emp.put("city", city);
            emp.put("password", password);
            emp.put("magacin2", btnML.getText().toString());
            emp.put("magacin3", btnMR.getText().toString());
            emp.put("magacin1", btnMC.getText().toString());
            db.collection("korisnik").add(emp).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplicationContext(), "Succsess", Toast.LENGTH_LONG);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Fill out all the fields", Toast.LENGTH_SHORT);
        }
    }
}
