package com.example.uasandroid.isi_menu.recipes_isi_category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_isi);

        database = FirebaseDatabase.getInstance();
        categoriesIsi = database.getReference("CategoryRecipes");

        ListCategoryIsi = (RecyclerView)findViewById(R.id.IsiCategory);

        ListCategoryIsi.hasFixedSize();
        ListCategoryIsi.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));

        loadCategoryIsi(categoryId);

        TextView category_judul_name = (TextView)findViewById(R.id.category_judul_name);

        if (position == 0){
            category_judul_name.setText("BREAKFAST");
        } else if(position == 1){
            category_judul_name.setText("LUNCH");
        } else if(position == 2){
            category_judul_name.setText("DINNER");
        }
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

    public void backButtonDetail(View view) {
        Intent intent =  new Intent(this, RecipesFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
