package com.example.demoappit6;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button loginbutton, createqoute, btnmap;
    private EditText textEmail;
    private EditText textPass;
    private Button signupbtn;
    FirebaseAuth fbaseAuth;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                if (isGranted) {
                    notificationManagerCompat.notify(1, notification);
                } else {
                    Toast.makeText(MainActivity.this, "Notification permission denied",
                            Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbaseAuth = FirebaseAuth.getInstance();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://samplefbase-29641-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World! Welcome to Firebase DB");


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("mychannel", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "mychannel")
                .setSmallIcon(android.R.drawable.btn_star_big_off)
                .setContentTitle("Login Notification")
                .setContentText("The password or username that is inputed is incorrect")
                .setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT);

        notification = builder.build();
        notificationManagerCompat=NotificationManagerCompat.from(this);


        textEmail = findViewById(R.id.email);
        textPass = findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.loginButton);
        createqoute = (Button) findViewById(R.id.getqoute);
        btnmap = (Button) findViewById(R.id.gomap);

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, googleMaps.class);
                startActivity(intent);
            }
        });

        createqoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, savequote.class);
                startActivity(intent);
            }
        });



        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextPage();
            }
        });

        signupbtn = (Button) findViewById(R.id.signup);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signuppage.class);
                startActivity(intent);
            }
        });
    }


    public void openNextPage(){
//        String myEmail = "myemail.gmail.com";
//        String myPassword = "GwapoKo123";
        String inputEmail = textEmail.getText().toString();
        String inputPass = textPass.getText().toString();
//        if(inputEmail.equals(myEmail) && inputPass.equals(myPassword)){
//            Intent intent = new Intent(this, withSideNavigation.class);
//            startActivity(intent);
//        }else{
////            Toast.makeText(MainActivity.this, "Incorrect Password or Username", Toast.LENGTH_LONG).show();
//            showLoginFailedNotification();
//        }

        fbaseAuth.signInWithEmailAndPassword(inputEmail, inputPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this, withSideNavigation.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this,
                                    "Incorrect Email and Password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void showLoginFailedNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                notificationManagerCompat.notify(1, notification);
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            notificationManagerCompat.notify(1, notification);
        }
    }


//    public void notif(View v){
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED ){
//            notificationManagerCompat.notify(1,notification);
//        }else{
//            Toast.makeText(MainActivity.this, "No Persmission", Toast.LENGTH_LONG).show();
//        }
//    }
}