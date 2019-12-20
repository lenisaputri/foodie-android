package com.example.uasandroid.isi_menu.recipes_isi_category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uasandroid.Common.Common;
import com.example.uasandroid.Interface.ItemClickListener;
import com.example.uasandroid.R;
import com.example.uasandroid.isi_menu.RecipesFragment;
import com.example.uasandroid.isi_menu.proses_menu.IsiRecipesCategory;
import com.example.uasandroid.isi_menu.proses_menu.RecipesCategory;
import com.example.uasandroid.isi_menu.view_holder.IsiRecipesCategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.uasandroid.Common.Common.categoryId;

public class RecipesIsiActivity extends AppCompatActivity {

    RecyclerView ListCategoryIsi;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<IsiRecipesCategory, IsiRecipesCategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categoriesIsi;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        boolean useDarkMode = preferences.getBoolean("DARK_MODE", false);

        if (useDarkMode) {
            setTheme(R.style.ActivityThemeDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_isi);

        database = FirebaseDatabase.getInstance();
        categoriesIsi = database.getReference("CategoryRecipes");

        ListCategoryIsi = (RecyclerView)findViewById(R.id.IsiCategory);

        ListCategoryIsi.hasFixedSize();
        ListCategoryIsi.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));

        loadCategoryIsi(categoryId);
    }

    private void loadCategoryIsi(String categoryId) {

        FirebaseRecyclerOptions<IsiRecipesCategory> options =
                new FirebaseRecyclerOptions.Builder<IsiRecipesCategory>()
                        .setQuery(categoriesIsi.orderByChild("CategoryId").equalTo(categoryId), IsiRecipesCategory.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<IsiRecipesCategory, IsiRecipesCategoryViewHolder>(options)
        {
            @NonNull
            @Override
            public IsiRecipesCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new IsiRecipesCategoryViewHolder(inflater.inflate(R.layout.isi_category, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull IsiRecipesCategoryViewHolder viewHolder, int position, @NonNull final IsiRecipesCategory model) {
                viewHolder.category_name.setText(model.getName());
                Glide.with(getApplicationContext())
                        .load(model.getImage())
                        .into(viewHolder.category_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent startGame = new Intent(view.getContext() ,IsiRecipesCategoryDetailActivity.class);
                        Common.categoryName = model.getName();
                        startActivity(startGame);
                    }
                });
            }

        };
        adapter.notifyDataSetChanged();
        ListCategoryIsi.setAdapter(adapter);
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
//
//    public void buttonBack(View view) {
//        // Create new fragment and transaction
//        Fragment newFragment = new RecipesFragment();
//        // consider using Java coding conventions (upper first char class names!!!)
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack
//        transaction.replace(R.id.action_recipes, newFragment);
//        transaction.addToBackStack(null);
//
//        // Commit the transaction
//        transaction.commit();
//    }

}
