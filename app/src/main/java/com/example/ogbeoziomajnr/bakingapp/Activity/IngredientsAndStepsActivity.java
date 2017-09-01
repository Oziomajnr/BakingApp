package com.example.ogbeoziomajnr.bakingapp.Activity;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.ogbeoziomajnr.bakingapp.Fragment.StepDetailFragment;
import com.example.ogbeoziomajnr.bakingapp.Interface.OnFragmentInteractionListener;
import com.example.ogbeoziomajnr.bakingapp.Widget.BakingAppWidget;
import com.example.ogbeoziomajnr.bakingapp.Fragment.IngredientFragment;
import com.example.ogbeoziomajnr.bakingapp.Fragment.StepsFragment;
import com.example.ogbeoziomajnr.bakingapp.Model.Ingredient;
import com.example.ogbeoziomajnr.bakingapp.Model.Step;
import com.example.ogbeoziomajnr.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import timber.log.Timber;

public class IngredientsAndStepsActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    List<Ingredient> ingredients = new ArrayList<>();
    List<Step> steps = new ArrayList<>();

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String ingredientString = "";
    SharedPreferences sharedPref;
    String recipeName;

   public AppWidgetManager appWidgetManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ingredients_and_steps);
            ButterKnife.bind(this);
            Bundle extras = this.getIntent().getExtras();
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if (extras != null) {
                try {
                    Intent i = getIntent();
                    recipeName = i.getExtras().getString("RECIPE_NAME");
                    ingredients = (ArrayList<Ingredient>) i.getExtras().get("INGREDIENTS");
                    steps = (ArrayList<Step>) i.getExtras().get("STEPS");
                } catch (Exception ex) {
                    Timber.e(ex);
                    Toast.makeText(this,"An Error Has Occured", Toast.LENGTH_LONG).show();
                }
            }

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(recipeName);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);


            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            setIngredients();
            saveToSharedPrefrence();

        } catch (Exception ex) {
           Timber.e(ex);
        }
    }

    /**
     * Set up the view pager used in displaying the tabs
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new IngredientFragment(), "Ingredient");
        adapter.addFragment(new StepsFragment(), "Step");
        viewPager.setAdapter(adapter);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    /**
     * Save the name, number of ingredient and step of the recipe to shared preference
     * so that the data can be used to populate the widget later
     *
     * @return true if the operation was successful and false if the operation failed
     */
    private boolean saveToSharedPrefrence() {
        try {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.recipe_name), recipeName);
            editor.putString(getString(R.string.recipe_ingredient_string), ingredientString);
            editor.apply();

            RemoteViews remoteV = new RemoteViews(this.getPackageName(), R.layout.baking_app_widget);

            ComponentName thisWidget = new ComponentName( this, BakingAppWidget.class );
            AppWidgetManager.getInstance(this).updateAppWidget( thisWidget, remoteV );

            return true;
        } catch (Exception ex) {
            Timber.e(ex);
            return false;
        }
    }

    private void setIngredients () {
        if (ingredients != null && !ingredients.isEmpty()) {
            for (Ingredient ing : ingredients) {
                ingredientString += ing.getQuantity()
                        + " "+ing.getMeasure()
                        +" of "+ing.getIngredient() + "\n";
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
