package com.example.uasandroid.isi_menu.recipes_isi_category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.uasandroid.Interface.ItemClickListener;
import com.example.uasandroid.R;
import com.example.uasandroid.isi_menu.proses_menu.IsiRecipesCategoryDetail;
import com.example.uasandroid.isi_menu.view_holder.IsiRecipesCategoryDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.uasandroid.Common.Common.categoryId;

public class IsiRecipesCategoryDetailActivity extends AppCompatActivity {

    RecyclerView ListCategoryIsiDetail;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<IsiRecipesCategoryDetail, IsiRecipesCategoryDetailViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categoriesIsiDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_recipes_category_detail);

        database = FirebaseDatabase.getInstance();
        categoriesIsiDetail = database.getReference("CategoryRecipes");

        ListCategoryIsiDetail = (RecyclerView)findViewById(R.id.IsiCategoryDetail);

        ListCategoryIsiDetail.hasFixedSize();
        ListCategoryIsiDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        loadCategoryIsiDetail(categoryId);
    }

    private void loadCategoryIsiDetail(String categoryId) {

        FirebaseRecyclerOptions<IsiRecipesCategoryDetail> options =
                new FirebaseRecyclerOptions.Builder<IsiRecipesCategoryDetail>()
                        .setQuery(categoriesIsiDetail.orderByChild("CategoryId").equalTo(categoryId), IsiRecipesCategoryDetail.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<IsiRecipesCategoryDetail, IsiRecipesCategoryDetailViewHolder>(options)
        {
            @NonNull
            @Override
            public IsiRecipesCategoryDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new IsiRecipesCategoryDetailViewHolder(inflater.inflate(R.layout.isi_recipes_category_detail, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull IsiRecipesCategoryDetailViewHolder viewHolder, int position, @NonNull final IsiRecipesCategoryDetail model) {
                viewHolder.category_name.setText(model.getName());
                Glide.with(getApplicationContext())
                        .load(model.getImage())
                        .into(viewHolder.category_image);
                viewHolder.category_ingridient.setText(model.getIngridient());
                viewHolder.category_method.setText(model.getMethod());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Intent startGame = new Intent(view.getContext() ,IsiRecipesCategoryDetailActivity.class);
//                        Common.categoryId = adapter.getRef(position).getKey();
////                        TextView nama = view.findViewById(R.id.category_name);
////                        Common.categoryName = model.getName();
//                        startActivity(startGame);
                    }
                });
            }

        };
        adapter.notifyDataSetChanged();
        ListCategoryIsiDetail.setAdapter(adapter);
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
