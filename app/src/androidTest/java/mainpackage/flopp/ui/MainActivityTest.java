package mainpackage.flopp.ui;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import mainpackage.flopp.R;

import mainpackage.flopp.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void checkForRecyclerView() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void checkForRecyclerViewClick(){
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }
}