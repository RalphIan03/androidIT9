package com.example.demoappit6;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signuppage extends AppCompatActivity {

    private Button createBtn;
    private EditText uname, pass;
    private FirebaseAuth fbaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbaseAuth = FirebaseAuth.getInstance();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signuppage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createBtn = (Button) findViewById(R.id.createAcc);
        uname = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = uname.getText().toString();
                password = pass.getText().toString();

                Toast.makeText(signuppage.this, email, Toast.LENGTH_LONG).show();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(signuppage.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    return;
                }

                fbaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(signuppage.this, "Signup Successful", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(signuppage.this, "Auth Field", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });


    }
}