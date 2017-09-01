package com.example.ogbeoziomajnr.bakingapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ogbeoziomajnr.bakingapp.Adapter.IngredientAdapter;
import com.example.ogbeoziomajnr.bakingapp.Activity.IngredientsAndStepsActivity;
import com.example.ogbeoziomajnr.bakingapp.Model.Ingredient;
import com.example.ogbeoziomajnr.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link IngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    LinearLayoutManager layoutManager;
    IngredientAdapter ingredientAdapter;
    @BindView(R.id.recyclerview_ingredient)
    RecyclerView mRecyclerView;

    List<Ingredient> ingredients = new ArrayList<>();

    public IngredientFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static IngredientFragment newInstance() {
        IngredientFragment fragment = new IngredientFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_ingredient);

        IngredientsAndStepsActivity parentActivity = (IngredientsAndStepsActivity) getActivity();
        ingredients = parentActivity.getIngredients();

        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        ingredientAdapter = new IngredientAdapter();
        mRecyclerView.setAdapter(ingredientAdapter);
        ingredientAdapter.loadRecipes(ingredients);
        // Inflate the layout for this fragment
        return rootView;
    }

}
