package com.example.uasandroid.update_delete_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.uasandroid.Common.Common;
import com.example.uasandroid.R;
import com.example.uasandroid.isi_menu.AccountFragment;
import com.example.uasandroid.isi_menu.RecipesFragment;
import com.example.uasandroid.proses_user.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText edtNewUser, edtNewUserName, edtNewPassword;

    FirebaseDatabase database;
    DatabaseReference update;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        boolean useDarkMode = preferences.getBoolean("DARK_MODE", false);

        if(useDarkMode) {
            setTheme(R.style.ActivityThemeDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        edtNewUser = (EditText) findViewById(R.id.edtNewName);
        edtNewUserName = (EditText) findViewById(R.id.edtNewUserName);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        btnSubmit = (Button) findViewById(R.id.btn_update);

        database = FirebaseDatabase.getInstance();
        update = database.getReference("Users");

        if (Common.currentUser != null) {
            edtNewUser.setText(Common.currentUser.getUserName());
            edtNewUserName.setText(Common.currentUser.getUserUserName());
            edtNewPassword.setText(Common.currentUser.getUserPassword());

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUser();
                }
            });
        }

//        ImageButton imageButton = findViewById(R.id.back_menu_update);
//
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    // Create new fragment and transaction
//                    Fragment newFragment = new AccountFragment();
//                    // consider using Java coding conventions (upper first char class names!!!)
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//                    // Replace whatever is in the fragment_container view with this fragment,
//                    // and add the transaction to the back stack
//                    transaction.replace(R.id.account, newFragment);
//                    transaction.addToBackStack(null);
//
//                    // Commit the transaction
//            }
//        });
    }

    private void updateUser() {
        update.child(Common.currentUser.getUserUserName())

                .setValue(new User(edtNewUser.getText().toString(), edtNewUserName.getText().toString(), edtNewPassword.getText().toString())) //set value barang yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.btn_update), "Data berhasil diupdate", Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
                });
    }

}
