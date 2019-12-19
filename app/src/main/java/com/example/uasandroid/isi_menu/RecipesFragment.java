package com.example.uasandroid.isi_menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uasandroid.Common.Common;
import com.example.uasandroid.Interface.ItemClickListener;
import com.example.uasandroid.R;
import com.example.uasandroid.isi_menu.proses_menu.RecipesCategory;
import com.example.uasandroid.isi_menu.recipes_isi_category.RecipesIsiActivity;
import com.example.uasandroid.isi_menu.view_holder.RecipesCategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;

public class RecipesFragment extends Fragment {

    View myFragment;
    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<RecipesCategory, RecipesCategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;

    public static RecipesFragment newInstances()
    {
        RecipesFragment recipesFragment = new RecipesFragment();
        return recipesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context contextThemeWrapper;

        SharedPreferences preferences = getActivity().getSharedPreferences("SETTINGS", MODE_PRIVATE);
        boolean useDarkMode = preferences.getBoolean("DARK_MODE", false);

        if (useDarkMode) {
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.ActivityThemeDark);
        } else {
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme);
        }

        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        myFragment = localInflater.inflate(R.layout.fragment_recipes,container,false);

        listCategory = myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listCategory.setLayoutManager(layoutManager);

        backBtn();
        loadCategories();

        return myFragment;
    }

    private void backBtn() {
        ImageButton btnBack = (ImageButton) myFragment.findViewById(R.id.back_menu);

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

    private void loadCategories() {
        FirebaseRecyclerOptions<RecipesCategory> options =
                new FirebaseRecyclerOptions.Builder<RecipesCategory>()
                        .setQuery(categories, RecipesCategory.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<RecipesCategory, RecipesCategoryViewHolder>(options)
        {
            @NonNull
            @Override
            public RecipesCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new RecipesCategoryViewHolder(inflater.inflate(R.layout.category_layout, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull RecipesCategoryViewHolder viewHolder, int position, @NonNull final RecipesCategory model) {
                viewHolder.category_name.setText(model.getName());
                Glide.with(getActivity())
                        .load(model.getImage())
                        .into(viewHolder.category_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent startGame = new Intent(view.getContext() , RecipesIsiActivity.class);
                        Common.categoryId = adapter.getRef(position).getKey();
                        Common.categoryName = model.getName();
                        startActivity(startGame);
                    }
                });
            }

        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
    
}
