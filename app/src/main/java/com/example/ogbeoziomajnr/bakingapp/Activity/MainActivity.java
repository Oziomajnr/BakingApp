package com.example.ogbeoziomajnr.bakingapp.Activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ogbeoziomajnr.bakingapp.Adapter.RecipeAdapter;
import com.example.ogbeoziomajnr.bakingapp.Interface.RecipeAdapterOnClickHandler;
import com.example.ogbeoziomajnr.bakingapp.Model.Ingredient;
import com.example.ogbeoziomajnr.bakingapp.Model.Recipe;
import com.example.ogbeoziomajnr.bakingapp.Model.Step;
import com.example.ogbeoziomajnr.bakingapp.R;
import com.example.ogbeoziomajnr.bakingapp.Util.ApiClient;
import com.example.ogbeoziomajnr.bakingapp.Interface.ApiInterface;
import com.example.ogbeoziomajnr.bakingapp.Widget.BakingAppWidget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeAdapterOnClickHandler {

    List<Recipe> recipeList = new ArrayList<>();
    GridLayoutManager layoutManager;
    RecipeAdapter recipeAdapter;
    int number_of_grid;
    SharedPreferences sharedPref;


    // Declare and initialise views
    @BindView(R.id.recyclerview_recipe)
    RecyclerView mRecipeList;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar pbLoading;
    @BindView(R.id.button_retry)
    Button retryButton;
    @BindView(R.id.textView_connectionError)
    TextView textViewConnectionError;
    @BindView(R.id.textView_welcome)
    TextView textViewWelcome;


    String ingredientString = "";
    private Call<List<Recipe>> call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Timber.plant(new Timber.DebugTree());

        number_of_grid = this.getResources().getInteger(R.integer.num_grid);

        layoutManager = new GridLayoutManager(this, number_of_grid);
        mRecipeList.setLayoutManager(layoutManager);
        recipeAdapter = new RecipeAdapter(this);
        mRecipeList.setAdapter(recipeAdapter);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // load the data from saved instance instead of going to the server
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("RECIPES") != null) {
            recipeList = savedInstanceState.getParcelableArrayList("RECIPES");
            recipeAdapter.loadRecipes(recipeList);
            stopLoading();
        }

        // if it did not get any data from bundle then load the data from the server
        if (recipeList == null || recipeList.isEmpty()) {
            getRecipeList();
        }
    }


    @Override
    public void onClick(Recipe recipeToView) {
        List<Ingredient> ingredients = recipeToView.getIngredients();
        List<Step> steps = recipeToView.getSteps();
        Intent intent = new Intent(this, IngredientsAndStepsActivity.class);
        intent.putExtra("RECIPE_NAME", recipeToView.getName());
        intent.putParcelableArrayListExtra("INGREDIENTS", (ArrayList<Ingredient>) ingredients);
        intent.putParcelableArrayListExtra("STEPS", (ArrayList<Step>) steps);
        startActivity(intent);
    }



    private void getRecipeList() {
        //Initialise the api interface
        showLoading();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        call = apiService.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = response.body();
                recipeAdapter.loadRecipes(recipeList);
                stopLoading();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Timber.e(t);
                showErrorMessage();
            }

        });
    }

    private void showLoading() {
        textViewWelcome.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);
        textViewConnectionError.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {
        textViewWelcome.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        textViewWelcome.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.VISIBLE);
        textViewConnectionError.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.button_retry)
    void retry() {
        getRecipeList();
    }

    /**
     * Save the list so that it would not be reloaded when you navigate in and out of the activity
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("RECIPES", (ArrayList<Recipe>) recipeList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // if it did not get any data from bundle then load the data from the server
        if (recipeList == null || recipeList.isEmpty()) {
            getRecipeList();
        }
    }
}
