package com.example.uasandroid.proses_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.uasandroid.Common.Common;
import com.example.uasandroid.activities.MenuActivity;
import com.example.uasandroid.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    EditText edtNewUser, edtNewUserName, edtNewPassword; //for sing up

    EditText edtUsername, edtPassword; //for sign in;

    Dialog myDialog;

    Button btnSingUp;

    ImageButton btnSignIn;

    FirebaseDatabase database;
    DatabaseReference users;


    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtUsername = (EditText)findViewById(R.id.edtUserName);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        btnSignIn = (ImageButton)findViewById(R.id.btn_sign_in);

        btnSingUp = (Button)findViewById(R.id.btn_sign_up);

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUsername.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void signIn(final String user, final String pwd){
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists())
                {
                    if(!user.isEmpty()) {
                        User login = dataSnapshot.child(user).getValue(User.class);
                        if (login.getUserPassword().equals(pwd)) {
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                                        "YOUR_CHANNEL_NAME",
                                        NotificationManager.IMPORTANCE_DEFAULT);
                                channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
                                mNotificationManager.createNotificationChannel(channel);
                            }
                            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                                    .setContentTitle("LOGIN") // title for notification
                                    .setContentText("Succes Login!")// message for notification
                                    .setAutoCancel(true); // clear notification after click

                            Intent homeActivity = new Intent(SignIn.this, MenuActivity.class);
                            Common.currentUser = login;
                            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, login, PendingIntent.FLAG_UPDATE_CURRENT);
                            mBuilder.setContentIntent(pi);
                            mNotificationManager.notify(0, mBuilder.build());
                            startActivity(homeActivity);
                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(0, mBuilder.build());
                            finish();
                        } else
                            Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(SignIn.this, "Please Enter your user name", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(SignIn.this, "User is not exists !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // POP UP

    public void showSignUpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignIn.this,R.style.MyDialogTheme);

        LayoutInflater inflater = this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(R.layout.sign_up,null);

        edtNewUser = sign_up_layout.findViewById(R.id.edtNewName);
        edtNewUserName = sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword = sign_up_layout.findViewById(R.id.edtNewPassword);

        alertDialog.setView(sign_up_layout);

        alertDialog.setNegativeButton("NO" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final User user = new User(edtNewUser.getText().toString(), edtNewUserName.getText().toString(), edtNewPassword.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUserUserName()).exists())
                        {
                            Toast.makeText(SignIn.this,"Username already exists!",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            users.child(user.getUserUserName()).setValue(user);
                            Toast.makeText(SignIn.this,"User registration success!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }
}
