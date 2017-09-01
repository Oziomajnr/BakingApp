package com.example.ogbeoziomajnr.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ogbeoziomajnr.bakingapp.Model.Ingredient;
import com.example.ogbeoziomajnr.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SQ-OGBE PC on 03/07/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    List<Ingredient> ingredients = new ArrayList<>();

    Context context;

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutForItem = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForItem, parent, false);
        IngredientViewHolder viewHolder = new IngredientViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) {
            return 0;
        }
        return ingredients.size();
    }

    public void loadRecipes(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIngredientName;
        TextView textViewIngredientQuantity;
        TextView textViewIngredientmeasure;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            textViewIngredientName = (TextView) itemView.findViewById(R.id.tv_ingredient_name);
            textViewIngredientQuantity = (TextView) itemView.findViewById(R.id.tv_ingredient_quantity);
            textViewIngredientmeasure = (TextView) itemView.findViewById(R.id.tv_ingredient_measure);
        }

        void bind(int position) {
            textViewIngredientmeasure.setText("Measure: " + ingredients.get(position).getMeasure());
            textViewIngredientName.setText(ingredients.get(position).getIngredient());
            textViewIngredientQuantity.setText("Quantity: " + ingredients.get(position).getQuantity());
        }
    }
}
