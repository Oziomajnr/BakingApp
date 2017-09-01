package com.example.ogbeoziomajnr.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ogbeoziomajnr.bakingapp.Model.Recipe;
import com.example.ogbeoziomajnr.bakingapp.R;
import com.example.ogbeoziomajnr.bakingapp.Interface.RecipeAdapterOnClickHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SQ-OGBE PC on 27/06/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final RecipeAdapterOnClickHandler mClickHandler;
    Context context;
    private List<Recipe> recipes;

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         context = parent.getContext();
        int layoutForItem = R.layout.re_recipe_layout;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForItem, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }
        return recipes.size();
    }

    public void loadRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name)
        TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            itemView.setOnClickListener(this);
            recipeName = (TextView)itemView.findViewById(R.id.tv_recipe_name);
        }

        void bind(int position) {
            recipeName.setText(recipes.get(position).getName());
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe movieToView = recipes.get(adapterPosition);
            mClickHandler.onClick(movieToView);
        }

    }
}
