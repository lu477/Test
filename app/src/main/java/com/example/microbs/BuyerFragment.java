package com.example.microbs;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class BuyerFragment extends Fragment {

    private Button saveBtn, delBtn;
    private EditText nameRef,passwordRef,pibRef;
    private String name,password,pib;
    private FirebaseFirestore db;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_buyer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        nameRef = view.findViewById(R.id.et_editBname);
        passwordRef = view.findViewById(R.id.et_editBpass);
        pibRef = view.findViewById(R.id.et_editBpib);
        db = FirebaseFirestore.getInstance();

        Bundle bundle = this.getArguments();

        saveBtn = view.findViewById(R.id.btn_editB);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameRef.getText().toString();
                password = passwordRef.getText().toString();
                pib = pibRef.getText().toString();
                Map<String, Object> buyer = new HashMap<>();
                buyer.put("name", name);
                buyer.put("password", password);
                buyer.put("pib", pib);
                db.collection("kupac").document(bundle.getString("name")).update(buyer).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(getContext(),BuyerActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                });

            }
        });
        delBtn = view.findViewById(R.id.btn_deleteB);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("kupac").document(bundle.getString("name")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(getContext(),BuyerActivity.class));
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to delete "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        if(bundle != null){
            nameRef.setText(bundle.getString("name"));
            passwordRef.setText(bundle.getString("password"));
            pibRef.setText(bundle.getString("pib"));
        }

        super.onViewCreated(view, savedInstanceState);
    }
}
