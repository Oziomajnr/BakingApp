package com.example.ogbeoziomajnr.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ogbeoziomajnr.bakingapp.Activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.support.test.espresso.contrib.RecyclerViewActions;

import static org.hamcrest.Matchers.anything;

/**
 * Created by SQ-OGBE PC on 08/07/2017.
 * This class is to test that the basic functionality of the main activity
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    /**
     * You should run this method when there is internet connection because
     * internet connection is needed for the recycler view to load its items completely
     * @throws InterruptedException
     */
    @Test
    public void clickRecyclerViewItemOpensActivity() throws InterruptedException {
        // delay for 3 seconds so that it would load data from the internet
            Thread.sleep(3000);


        //perform click on the recycler view
        onView(withId(R.id.recyclerview_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the Ingredient Detail Activity opens by checking if it
        // a view pager with the id R.id.viewpager
        onView(withId(R.id.viewpager)).check(matches(withId(R.id.viewpager)));

    }
}
