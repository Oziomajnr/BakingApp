package com.example.ogbeoziomajnr.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.ogbeoziomajnr.bakingapp.Activity.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by SQ-OGBE PC on 08/07/2017.
 */

public class MainActivityIntentTest {
    private static final String emailMessage = "I just ordered a delicious tea from TeaTime. Next time you are craving a tea, check them out!";

    /**
     *
     * This test demonstrates Espresso Intents using the IntentsTestRule, a class that extends
     * ActivityTestRule. IntentsTestRule initializes Espresso-Intents before each test that is annotated
     * with @Test and releases it once the test is complete. The designated Activity
     * is also terminated after each test.
     *
     */

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);


    @Before
    public void stubAllExternalIntents() {
       //Stub all internal intents
        intending(isInternal()).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickRecyclerViewSendsText() throws InterruptedException {
        Thread.sleep(3000);


        //perform click on the recycler view
        onView(withId(R.id.recyclerview_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // check if the intent contains the first element of the json
        // this text would have to be changed anytime the json changes
        intended(allOf(
                hasExtra("RECIPE_NAME", "Nutella Pie")));
    }
}
