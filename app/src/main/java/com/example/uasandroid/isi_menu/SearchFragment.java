package com.example.uasandroid.isi_menu;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.uasandroid.R;
import com.google.firebase.database.FirebaseDatabase;

public class SearchFragment extends Fragment {

    EditText edtNewUser, edtNewUserName, edtNewPassword; //for sing up

    public static SearchFragment newInstances()
    {
        SearchFragment searchFragment = new SearchFragment();
        return searchFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

}
