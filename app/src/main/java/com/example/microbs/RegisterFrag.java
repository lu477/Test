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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFrag extends Fragment {

    private EditText emailRef;
    private EditText userRef;
    private EditText passRef;
    private String user;
    private String pass;
    private String email;
    private FirebaseAuth mAuth;
    private Button btnReg;
    private Button btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        mAuth = FirebaseAuth.getInstance();
        emailRef = view.findViewById(R.id.et_regemail);
        passRef = view.findViewById(R.id.et_regpass);
        userRef = view.findViewById(R.id.et_username);

        btnBack = view.findViewById(R.id.btn_back);
        btnReg = view.findViewById(R.id.btn_regUser);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailRef.getText().toString();
                email.replaceAll(" ", "");
                email.toLowerCase();
                user = userRef.getText().toString().toLowerCase();
                pass = passRef.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "createUserWithEmail:success");
                            startActivity(new Intent(getContext(), MainActivity.class));
                            Toast.makeText(getContext(),"Welcome "+ user,Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Registration failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MainActivity.class));
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
