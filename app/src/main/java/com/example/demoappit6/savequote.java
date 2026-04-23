package com.example.demoappit6;

import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class savequote extends AppCompatActivity {

    private EditText inputQoute, inputAuthor;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_savequote);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputQoute = (EditText) findViewById(R.id.quote);
        inputAuthor = (EditText) findViewById(R.id.author);
        saveBtn = (Button) findViewById(R.id.save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String theQuote = inputQoute.getText().toString();
                String theAuthor = inputAuthor.getText().toString();

                addqoute(theQuote, theAuthor);
            }
        });
    }

    private void addqoute(String qoute, String author){
        HashMap<String, Object> hashQuote = new HashMap<>();
        hashQuote.put("quote", qoute);
        hashQuote.put("author", author);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://samplefbase-29641-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference qouteRef = database.getReference("qoutes");

        String key = qouteRef.push().getKey();
        hashQuote.put("key",key);

        qouteRef.child(key).setValue(hashQuote).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(savequote.this, "Quote Saved Successfully", Toast.LENGTH_LONG).show();
                inputQoute.getText().clear();
                inputAuthor.getText().clear();
            }
        });


    }
}